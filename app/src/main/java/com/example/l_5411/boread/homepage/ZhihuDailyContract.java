package com.example.l_5411.boread.homepage;

import com.example.l_5411.boread.BasePresenter;
import com.example.l_5411.boread.BaseView;
import com.example.l_5411.boread.bean.ZhihuDailyNews;

import java.util.ArrayList;

/**
 * Created by L_5411 on 2017/3/11.
 */

public interface ZhihuDailyContract {

    interface View extends BaseView<Presenter>{

        // 显示加载错误
        void showError();
        // 显示正在加载
        void showLoading();
        // 停止加载
        void stopLoading();
        // 获取数据后显示在界面中
        void showResults(ArrayList<ZhihuDailyNews.StoriesBean> list);

    }

    interface Presenter extends BasePresenter {

        // 请求数据
        void loadPosts(long date, boolean clearing);
        // 刷新数据
        void refresh();
        // 加载更多
        void loadMore(long date);
        // 显示详情
        void read(int position);
    }
}
