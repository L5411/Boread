package com.example.l_5411.boread.app;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by L_5411 on 2017/3/11.
 */

public class VolleySingleton {

    private static VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingleton getVolleySingleton(Context context) {
        if(volleySingleton == null) {
            volleySingleton = new VolleySingleton(context);
        }
        return volleySingleton;
    }

    public RequestQueue getRequestQueue() {
        return this.requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
