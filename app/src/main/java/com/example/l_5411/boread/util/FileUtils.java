package com.example.l_5411.boread.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.l_5411.boread.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by L_5411 on 2017/3/24.
 */

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();
    public static String saveImageToGallery(Context context, Bitmap bmp, String name) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "/boread/");
        if (!appDir.exists()) {

            boolean i = appDir.mkdirs();
            Log.i(TAG, appDir.toString() + i);
        }
        String fileName = name;
        File file = new File(appDir, fileName);
        if(file.exists()) {
            return context.getString(R.string.save_failed )+ ": 文件已存在 ";
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        System.out.println(file.getAbsolutePath());
        return context.getString(R.string.save_at) + file.getAbsolutePath();
    }
}
