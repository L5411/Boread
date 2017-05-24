package com.example.l_5411.boread.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.l_5411.boread.R;

/**
 * Created by L_5411 on 2017/3/24.
 */

public class PhotoActivity extends AppCompatActivity {

    private static final String FRAGMENT_KEY = "detailFragment";
    private static final String EXTRA_URL = "url";
    private PhotoFragment fragment;
    private PhotoPresenter presenter;

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(EXTRA_URL, url);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.frame);

        if(savedInstanceState != null) {
            fragment = (PhotoFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_KEY);
        } else {
            fragment = PhotoFragment.newInstance(getIntent().getStringExtra(EXTRA_URL));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

        presenter = new PhotoPresenter(PhotoActivity.this, fragment, getIntent().getStringExtra(EXTRA_URL));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, fragment);
        }
    }
}
