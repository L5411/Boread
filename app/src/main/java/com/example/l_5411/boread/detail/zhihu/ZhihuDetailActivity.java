package com.example.l_5411.boread.detail.zhihu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.l_5411.boread.R;

/**
 * Created by L_5411 on 2017/3/16.
 */

public class ZhihuDetailActivity extends AppCompatActivity {

    private static final String FRAGMENT_KEY = "detailFragment";
    private static final String EXTRA_ID = "id";

    private ZhihuDetailFragment fragment;

    public static Intent createDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, ZhihuDetailActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        if(savedInstanceState != null) {
            fragment = (ZhihuDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
        } else {
            fragment = ZhihuDetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

        Intent intent = getIntent();

        ZhihuDetailPresenter presenter = new ZhihuDetailPresenter(ZhihuDetailActivity.this, fragment);
        presenter.setId(intent.getIntExtra(EXTRA_ID, 0));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, fragment);
        }
    }
}
