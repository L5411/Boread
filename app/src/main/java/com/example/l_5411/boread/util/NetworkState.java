package com.example.l_5411.boread.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by L_5411 on 2017/3/11.
 */

public class NetworkState {

    public static boolean networkConnected(Context context) {

        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }

        return false;
    }
}
