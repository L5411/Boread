package com.example.l_5411.boread.bean;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.l_5411.boread.app.VolleySingleton;
import com.example.l_5411.boread.interfaces.OnStringListener;

/**
 * Created by L_5411 on 2017/3/11.
 */

public class StringModelImpl {

    private Context context;

    public StringModelImpl(Context context) {
        this.context = context;
    }

    public void load(String url, final OnStringListener listener) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });

        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }
}
