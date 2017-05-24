package com.example.l_5411.boread.detail;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.l_5411.boread.app.VolleySingleton;
import com.example.l_5411.boread.util.FileUtils;

import java.io.File;

/**
 * Created by L_5411 on 2017/4/2.
 */

public class PhotoPresenter implements PhotoContract.Presenter {

    private static final int REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE = 111;
    private PhotoContract.View view;
    private Activity context;
    private String imageUrl;

    public PhotoPresenter(Activity context, PhotoContract.View view, String imageUrl) {
        this.context = context;
        this.view = view;
        this.imageUrl = imageUrl;
        view.setPresenter(this);
    }

    @Override
    public void downloadPic() {
        if(requestPermissions()) {
            ImageRequest ir = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    savePic(response);
                }
            }, 0, 0, null, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.showSaveFailed();
                }
            });
            VolleySingleton.getVolleySingleton(context).addToRequestQueue(ir);
        }
    }

    @Override
    public void savePic(Bitmap bitmap) {
        if(bitmap != null) {
            Uri url = Uri.parse(imageUrl);
            String path = FileUtils.saveImageToGallery(context, bitmap, url.getPathSegments().get(2));
            view.showSaveSuccess(path);
        } else {
            view.showSaveFailed();
        }
    }

    @Override
    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND).setType("image/*");
        Uri uri = Uri.fromFile(new File("/storage/emulated/0/boread/" + Uri.parse(imageUrl).getPathSegments().get(2)));
        System.out.println(uri.toString());
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, "分享至"));
    }

    @Override
    public boolean requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(view.getViewActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }


    @Override
    public void start() {
        view.showPicture();
    }
}
