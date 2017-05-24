package com.example.l_5411.boread.detail.douban;

import android.content.Context;
import android.database.Cursor;

import com.android.volley.VolleyError;
import com.example.l_5411.boread.bean.DoubanMovieBean;
import com.example.l_5411.boread.bean.StringModelImpl;
import com.example.l_5411.boread.data.BoreadContract;
import com.example.l_5411.boread.interfaces.OnStringListener;
import com.example.l_5411.boread.util.Api;
import com.google.gson.Gson;

/**
 * Created by L_5411 on 2017/5/21.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private static final String TAG = MovieDetailPresenter.class.getSimpleName();

    private Context context;
    private MovieDetailContract.View view;
    private int id;
    private DoubanMovieBean movie;
    private Gson gson;
    private StringModelImpl model;

    public void setId(int id) { this.id = id; }

    public MovieDetailPresenter(Context context, MovieDetailContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        gson = new Gson();
        model = new StringModelImpl(context);
    }

    @Override
    public void start() {
        requestData();
    }

    @Override
    public void requestData() {
        Cursor cursor = context.getContentResolver().query(
                BoreadContract.DoubanEntry.buildDoubnaWithId(id),
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()) {
            String content = cursor.getString(cursor.getColumnIndex(BoreadContract.DoubanEntry.COLUMN_MOVIE_CONTENT));
            movie = gson.fromJson(content, DoubanMovieBean.class);
            view.showMovie(movie);
            view.setTitle();
            view.showCover(movie.getImages().getLarge());
        } else {
            model.load(Api.getDoubanMovieItemApi(id), new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    movie = gson.fromJson(result, DoubanMovieBean.class);
                    view.showMovie(movie);
                    view.setTitle();
                    view.showCover(movie.getImages().getLarge());
                }

                @Override
                public void onError(VolleyError error) {
                    view.showLoadError();
                }
            });
        }
        cursor.close();
    }
}
