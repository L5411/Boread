package com.example.l_5411.boread.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.l_5411.boread.bean.StringModelImpl;
import com.example.l_5411.boread.bean.ZhihuDailyNews;
import com.example.l_5411.boread.data.BoreadContract;
import com.example.l_5411.boread.detail.zhihu.ZhihuDetailActivity;
import com.example.l_5411.boread.interfaces.OnStringListener;
import com.example.l_5411.boread.service.CacheService;
import com.example.l_5411.boread.util.Api;
import com.example.l_5411.boread.util.DateFormatter;
import com.example.l_5411.boread.util.NetworkState;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

/**
 * Created by L_5411 on 2017/3/11.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private static final String TAG = ZhihuDailyPresenter.class.getSimpleName();

    private ZhihuDailyContract.View view;
    private Context context;
    private StringModelImpl model;

    private DateFormatter dateFormatter = new DateFormatter();
    private Gson gson = new Gson();

    private ArrayList<ZhihuDailyNews.StoriesBean> list = new ArrayList<>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        if(clearing) {
            view.showLoading();
        }
        if(NetworkState.networkConnected(context)) {
            model.load(Api.ZHIHU_HISTORY + dateFormatter.dateFormat(date), new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    try {
                        ZhihuDailyNews news = gson.fromJson(result, ZhihuDailyNews.class);
                        Vector<ContentValues> cVVetor = new Vector<ContentValues>(news.getStories().size());
                        if(clearing) {
                            list.clear();
                        }
                        for (ZhihuDailyNews.StoriesBean item: news.getStories()){
                            list.add(item);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(BoreadContract.ZhihuEntry.COLUMN_ZHIHU_ID, item.getId());
                            contentValues.put(BoreadContract.ZhihuEntry.COLUMN_DATE, news.getDate());
                            contentValues.put(BoreadContract.ZhihuEntry.COLUMN_NEWS, gson.toJson(item));
                            contentValues.put(BoreadContract.ZhihuEntry.COLUMN_CONTENT, "");
                            cVVetor.add(contentValues);
                        }
                        view.showResults(list);
                        int inserted = 0;
                        if( cVVetor.size() > 0) {
                            ContentValues[] cVArray = new ContentValues[cVVetor.size()];
                            cVVetor.toArray(cVArray);
                            inserted = context.getContentResolver().bulkInsert(BoreadContract.ZhihuEntry.CONTENT_URI, cVArray);
                            Log.i(TAG, "Inserted:" + inserted);
                        }

                        for (ZhihuDailyNews.StoriesBean item: news.getStories()){
                            Intent intent = new Intent("com.example.l_5411.boread.LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_ZHIHU);
                            intent.putExtra("id", item.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }catch (JsonSyntaxException e){
                        view.showError();
                    }
                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                }
            });
        } else {
            if(clearing) {
                list.clear();
                Cursor cursor = context.getContentResolver().query(
                        BoreadContract.ZhihuEntry.CONTENT_URI, null, null, null, null, null);
                Log.i(TAG, BoreadContract.ZhihuEntry.CONTENT_URI.toString());
                if(cursor.moveToFirst()) {
                    do {
                        ZhihuDailyNews.StoriesBean stroy = gson.fromJson(
                                cursor.getString(cursor.getColumnIndex(BoreadContract.ZhihuEntry.COLUMN_NEWS)),
                                ZhihuDailyNews.StoriesBean.class);
                        list.add(stroy);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                view.stopLoading();
                view.showResults(list);
            } else {
                view.showError();
            }
        }

    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void read(int position) {
        context.startActivity(ZhihuDetailActivity.createDetailActivity(context, list.get(position).getId()));
    }


}
