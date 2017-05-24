package com.example.l_5411.boread;

import android.view.View;

/**
 * Created by L_5411 on 2017/3/11.
 */

public interface BaseView<T> {

    /**
     * set the presenter of mvp
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * init the views of fragment
     * @param view
     */
    void initViews(View view);

}