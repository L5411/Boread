package com.example.l_5411.boread.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.l_5411.boread.bean.DoubanMovieListBean;
import com.example.l_5411.boread.bean.StringModelImpl;
import com.example.l_5411.boread.data.BoreadContract;
import com.example.l_5411.boread.interfaces.OnStringListener;
import com.example.l_5411.boread.service.CacheService;
import com.example.l_5411.boread.util.Api;
import com.example.l_5411.boread.util.NetworkState;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * Created by L_5411 on 2017/3/12.
 */

public class DoubanMoviePresenter implements DoubanMovieContract.Presenter{

    private static final String TAG = DoubanMoviePresenter.class.getSimpleName();

    private DoubanMovieContract.View view;
    private Context context;

    private Gson gson;
    private int start;
    private int size;
    private StringModelImpl model;
    private List<DoubanMovieListBean.SubjectsBean> list = new ArrayList<>();

    private String url;

    public DoubanMoviePresenter(Context context, DoubanMovieContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        gson = new Gson();
        model = new StringModelImpl(context);
        this.start = 0;
        this.size = 20;
        url = Api.getDoubanMovieTopApi(start, size);
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void loadData(String url, final boolean clearing) {
        Log.i(TAG, "loadData:" + url);
        if(clearing) {
            view.showLoading();
            list.clear();
        }
        // 判断网络是否链接
        if(NetworkState.networkConnected(context)) {
            model.load(url, new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    try {
                        DoubanMovieListBean beans = gson.fromJson(result, DoubanMovieListBean.class);
                        Vector<ContentValues> cVVetor = new Vector<>(beans.getSubjects().size());
                        for (DoubanMovieListBean.SubjectsBean item :
                                beans.getSubjects()) {
                            list.add(item);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(BoreadContract.DoubanEntry.COLUMN_MOVIE_ID, item.getId());
                            contentValues.put(BoreadContract.DoubanEntry.COLUMN_MOVIE_SUM, gson.toJson(item));
                            contentValues.put(BoreadContract.DoubanEntry.COLUMN_MOVIE_CONTENT, "");
                            cVVetor.add(contentValues);
                        }
                        view.showResult(list);
                        if( cVVetor.size() > 0) {
                            ContentValues[] cVArray = new ContentValues[cVVetor.size()];
                            cVVetor.toArray(cVArray);
                        }

                        for(DoubanMovieListBean.SubjectsBean item : beans.getSubjects()) {
                            Intent intent = new Intent("com.example.l_5411.boread.LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_DOUBAN);
                            intent.putExtra("id", item.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    } catch (JsonSyntaxException e) {
                        view.showError();
                    }
                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                    setPageZero();
                }
            });
        } else {
            if(clearing) {
                list.clear();
                Cursor cursor = context.getContentResolver().query(
                        BoreadContract.DoubanEntry.CONTENT_URI, null, null, null, null, null);
                if(cursor.moveToFirst()) {
                    do {
                        DoubanMovieListBean.SubjectsBean subject = gson.fromJson(
                                cursor.getString(cursor.getColumnIndex(BoreadContract.DoubanEntry.COLUMN_MOVIE_SUM)),
                                DoubanMovieListBean.SubjectsBean.class);
                        list.add(subject);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                view.stopLoading();
                view.showResult(list);
            } else {
                view.showError();
                setPageZero();
            }
        }
    }

    @Override
    public void refresh() {
        setPageZero();
        loadData(url, true);
        Log.i(TAG, "refresh");
    }

    @Override
    public void loadMore() {
        setNextPage();
        loadData(url, false);
        Log.i(TAG, "loadMore");
    }

    @Override
    public void search(String tag) {
        url = Api.getDoubanMovieSearch(tag, start, size);
        Log.i(TAG, "search: " + url + ", Tag:" + tag);
        loadData(url, true);
    }

    @Override
    public void cancelSearch() {
        start = 0;
        url = Api.getDoubanMovieTopApi(start, size);
        Log.i(TAG, "cancelSearch");
        loadData(url, true);
    }

    private void setPageZero() {
        start = 0;
        url = Api.replaceUriParameter(Uri.parse(url), "start", Integer.toString(start)).toString();
    }
    private void setNextPage() {
        start += size;
        url = Api.replaceUriParameter(Uri.parse(url), "start", Integer.toString(start)).toString();
    }
}
