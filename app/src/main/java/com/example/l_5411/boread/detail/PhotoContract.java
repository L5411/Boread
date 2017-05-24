package com.example.l_5411.boread.detail;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.l_5411.boread.BasePresenter;
import com.example.l_5411.boread.BaseView;

/**
 * Created by L_5411 on 2017/4/2.
 */

public interface PhotoContract {

    interface View extends BaseView<Presenter> {

        void showPicture();

        void showShareAndDownload();

        void showDownloadDialog();

        void showSaveSuccess(String path);

        void showSaveFailed();

        Activity getViewActivity();

        void showPermissionsFailed();

    }

    interface Presenter extends BasePresenter {
        void downloadPic();
        void savePic(Bitmap bitmap);
        void share();

        boolean requestPermissions();
    }
}
