package com.example.l_5411.boread.detail.douban;

import com.example.l_5411.boread.BasePresenter;
import com.example.l_5411.boread.BaseView;
import com.example.l_5411.boread.bean.DoubanMovieBean;

/**
 * Created by L_5411 on 2017/5/21.
 */

public interface MovieDetailContract {
    interface View extends BaseView<Presenter> {
        void setTitle();
        void showCover(String url);
        void showMovie(DoubanMovieBean movie);
        void showLoadError();
    }

    interface Presenter extends BasePresenter {
        void requestData();
    }
}
