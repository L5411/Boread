package com.example.l_5411.boread.interfaces;

import com.android.volley.VolleyError;

/**
 * Created by L_5411 on 2017/3/11.
 */

public interface OnStringListener {

    void onSuccess(String result);

    void onError(VolleyError error);
}
