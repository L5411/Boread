package com.example.l_5411.boread.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.l_5411.boread.R;
import com.example.l_5411.boread.homepage.DoubanMovieFragment;
import com.example.l_5411.boread.homepage.PexelsFragment;
import com.example.l_5411.boread.homepage.ZhihuDailyFragment;

/**
 * Created by L_5411 on 2017/3/12.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private ZhihuDailyFragment zhihuDailyFragment;
    private DoubanMovieFragment doubanMovieFragment;
    private PexelsFragment pexelsFragment;
    private String[] titles;

    public ZhihuDailyFragment getZhihuDailyFragment() {
        return zhihuDailyFragment;
    }

    public DoubanMovieFragment getDoubanMovieFragment() {
        return doubanMovieFragment;
    }

    public PexelsFragment getPexelsFragment() {
        return pexelsFragment;
    }

    public MainPagerAdapter(FragmentManager fm,
                            Context context,
                            ZhihuDailyFragment zhihuDailyFragment,
                            DoubanMovieFragment doubanMovieFragment,
                            PexelsFragment pexelsFragment) {
        super(fm);
        this.context = context;

        titles = new String[] {
                context.getResources().getString(R.string.zhihu_daily),
                context.getResources().getString(R.string.douban_movie),
                context.getResources().getString(R.string.pexels)
        };

        this.zhihuDailyFragment = zhihuDailyFragment;
        this.doubanMovieFragment = doubanMovieFragment;
        this.pexelsFragment = pexelsFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0 ) {
            return zhihuDailyFragment;
        }else if(position == 1) {
            return doubanMovieFragment;
        } else {
            return pexelsFragment;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
