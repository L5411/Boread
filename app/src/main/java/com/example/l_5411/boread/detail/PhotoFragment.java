package com.example.l_5411.boread.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.l_5411.boread.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by L_5411 on 2017/3/24.
 */

public class PhotoFragment extends Fragment implements PhotoContract.View{

    private static final int REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE = 111;
    private static final String TAG = PhotoFragment.class.getSimpleName();
    private static final String ARG_PIC_URL = "picture_url";
    private Context context;
    private PhotoView imageView;
    private LinearLayout linearLayout;
    private ImageButton shareButton;
    private ImageButton downloadButton;
    private PhotoContract.Presenter presenter;
    private boolean buttonShow;
    String imageUrl;


    public static PhotoFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_PIC_URL, url);

        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        initViews(view);
        presenter.start();
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.downloadPic();
                } else {
                    showPermissionsFailed();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void setPresenter(PhotoContract.Presenter presenter) {
        if(this.presenter == null ) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        buttonShow = true;
        imageUrl = getArguments().getString(ARG_PIC_URL);
        imageView = (PhotoView) view.findViewById(R.id.pexels_pic);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDownloadDialog();
                return false;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareAndDownload();
            }
        });
        linearLayout = (LinearLayout)view.findViewById(R.id.share_download);

        shareButton = (ImageButton) view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.share();
            }
        });
        downloadButton = (ImageButton) view.findViewById(R.id.download_button);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.downloadPic();
            }
        });
    }

    @Override
    public void showPicture() {
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);
    }

    @Override
    public void showShareAndDownload() {
        if(buttonShow) {
            linearLayout.setAlpha(1);
            linearLayout.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    });
        } else {
            linearLayout.setAlpha(0);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setListener(null);
        }
        buttonShow = !buttonShow;
    }

    @Override
    public void showDownloadDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.save_picture)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.downloadPic();
                    }
                })
                .show();
    }

    @Override
    public void showSaveSuccess(String path) {
        Snackbar.make(getView(),  path, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSaveFailed() {
        Snackbar.make(getView(), getString(R.string.save_failed), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public Activity getViewActivity() {
        return getActivity();
    }

    @Override
    public void showPermissionsFailed() {
        Snackbar.make(getView(), "没有存储空间权限", Snackbar.LENGTH_SHORT);
    }


}
