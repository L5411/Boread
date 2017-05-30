package com.example.l_5411.boread.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.l_5411.boread.app.VolleySingleton;
import com.example.l_5411.boread.bean.StringModelImpl;
import com.example.l_5411.boread.bean.ZhihuDailyStory;
import com.example.l_5411.boread.data.BoreadContract;
import com.example.l_5411.boread.interfaces.OnStringListener;
import com.example.l_5411.boread.util.Api;
import com.google.gson.Gson;

/**
 * Created by L_5411 on 2017/3/13.
 */

public class CacheService extends Service {

    private static final String TAG = CacheService.class.getSimpleName();

    public static final int TYPE_ZHIHU = 100;
    public static final int TYPE_DOUBAN = 200;
    public static final int TYPE_PEXELS = 300;

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.l_5411.boread.LOCAL_BROADCAST");
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(new LocalReceiver(), filter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startZhihuCache(final int id) {
        Cursor cursor = getContentResolver().query(
                BoreadContract.ZhihuEntry.buildZhihuWithId(id), // 根据zhihu_id查询
                new String[]{BoreadContract.ZhihuEntry.COLUMN_CONTENT, BoreadContract.ZhihuEntry.COLUMN_ZHIHU_ID}, // 只需查询出content和id
                null,
                null,
                null,
                null
        );
        if(cursor.moveToFirst()) {
            // 没有加载过数据
            if(cursor.getString(cursor.getColumnIndex(BoreadContract.ZhihuEntry.COLUMN_CONTENT)).equals("")) {
                StringRequest request = new StringRequest(Api.ZHIHU_NEWS + id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        ZhihuDailyStory story = gson.fromJson(response, ZhihuDailyStory.class);
                        if(story.getType() == 1) {
                            StringRequest req = new StringRequest(story.getShare_url(), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    ContentValues value = new ContentValues();
                                    value.put(BoreadContract.ZhihuEntry.COLUMN_CONTENT, response);
                                    getContentResolver().update(
                                            BoreadContract.ZhihuEntry.buildZhihuWithId(id),
                                            value,null,null);
                                    value.clear();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            VolleySingleton.getVolleySingleton(CacheService.this).addToRequestQueue(req);
                        } else {
                            ContentValues value = new ContentValues();
                            value.put(BoreadContract.ZhihuEntry.COLUMN_CONTENT, response);
                            getContentResolver().update(
                                    BoreadContract.ZhihuEntry.buildZhihuWithId(id),
                                    value,null,null);
                            value.clear();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                VolleySingleton.getVolleySingleton(CacheService.this).addToRequestQueue(request);
            }
        } else {
            Log.i(TAG, "not has cursor");
        }
        cursor.close();
    }

    private void startDoubanCache(final int id) {
        StringModelImpl model = new StringModelImpl(this);
        Cursor cursor = getContentResolver().query(
                BoreadContract.DoubanEntry.buildDoubnaWithId(id),
                new String[]{BoreadContract.DoubanEntry.COLUMN_MOVIE_CONTENT}, // 只需查询出content
                null,
                null,
                null,
                null
        );
        if(cursor.moveToFirst()) {
            if(cursor.getString(cursor.getColumnIndex(BoreadContract.DoubanEntry.COLUMN_MOVIE_CONTENT)).equals("")) {
                model.load(Api.getDoubanMovieItemApi(id), new OnStringListener() {
                    @Override
                    public void onSuccess(String result) {
                        ContentValues value = new ContentValues();
                        value.put(BoreadContract.DoubanEntry.COLUMN_MOVIE_CONTENT, result);
                        getContentResolver().update(
                                BoreadContract.DoubanEntry.buildDoubnaWithId(id),
                                value,null,null);
                        value.clear();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
        } else {
            Log.i(TAG, "not has cursor");
        }
        cursor.close();
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", 0);
            int type = intent.getIntExtra("type", -1);
            switch (type) {
                case TYPE_ZHIHU:
                    startZhihuCache(id);
                    break;
                case TYPE_DOUBAN:

                    startDoubanCache(id);
                    break;
                case TYPE_PEXELS:
                    break;
                default:
            }
        }
    }



}
