package com.example.l_5411.boread.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by L_5411 on 2017/3/24.
 */

public class ScreenUtils {

    /**
     * 获取屏幕宽度
     * @param context
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager =  (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
