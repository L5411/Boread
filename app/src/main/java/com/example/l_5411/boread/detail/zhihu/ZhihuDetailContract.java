package com.example.l_5411.boread.detail.zhihu;

import com.example.l_5411.boread.BasePresenter;
import com.example.l_5411.boread.BaseView;

/**
 * Created by L_5411 on 2017/3/16.
 */

public interface ZhihuDetailContract {

    interface View extends BaseView<Presenter> {

        void showSharingError();

        void showResult(String result);

        void showCover(String url);

        void setTitle(String title);

        void setImageMode(boolean showImage);

        void showBrowserNotFoundError();

        void showTextCopied();

        void showCopyTextError();


    }

    interface Presenter extends BasePresenter {

        void openInBrowser();

        void shareAsText();

        void requestData();

    }
}
