package com.example.l_5411.boread.homepage;

import com.example.l_5411.boread.BasePresenter;
import com.example.l_5411.boread.BaseView;
import com.example.l_5411.boread.bean.DoubanMovieListBean;

import java.util.List;

/**
 *
 * Created by L_5411 on 2017/3/12.
 */

public interface DoubanMovieContract {

    interface View extends BaseView<DoubanMovieContract.Presenter> {

        // 显示加载错误
        void showError();
        // 显示正在加载
        void showLoading();
        // 停止加载
        void stopLoading();
        // 获取数据后显示在界面中
        void showResult(List<DoubanMovieListBean.SubjectsBean> list);

    }

    interface Presenter extends BasePresenter {

        // 请求数据
        void loadData(String url, boolean clearing);
        // 刷新数据
        void refresh();
        // 加载更多
        void loadMore();

        void search(String tag);
        void cancelSearch();
    }
}
