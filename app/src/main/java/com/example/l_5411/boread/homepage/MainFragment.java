package com.example.l_5411.boread.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.l_5411.boread.R;
import com.example.l_5411.boread.adapter.MainPagerAdapter;

/**
 *
 * Created by L_5411 on 2017/3/11.
 */

public class MainFragment extends Fragment{

    private Context context;
    private MainPagerAdapter adapter;

    private TabLayout tabLayout;

    private ZhihuDailyFragment zhihuDailyFragment;
    private DoubanMovieFragment doubanMovieFragment;
    private PexelsFragment pexelsFragment;

    private ZhihuDailyPresenter zhihuDailyPresenter;
    private DoubanMoviePresenter doubanMoviePresenter;
    private PexelsPresenter pexelsPresenter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();

        if(savedInstanceState != null) {
            // Fragment嵌套Fragment要用getChildFragmentManager
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(savedInstanceState, "Zhihu");
            doubanMovieFragment = (DoubanMovieFragment) manager.getFragment(savedInstanceState, "Douban");
            pexelsFragment = (PexelsFragment) manager.getFragment(savedInstanceState, "Pexels");
        } else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            doubanMovieFragment = DoubanMovieFragment.newInstance();
            pexelsFragment = PexelsFragment.newInstance();
        }

        zhihuDailyPresenter = new ZhihuDailyPresenter(context, zhihuDailyFragment);
        doubanMoviePresenter = new DoubanMoviePresenter(context, doubanMovieFragment);
        pexelsPresenter = new PexelsPresenter(context, pexelsFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);


        return view;
    }

    private void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);

        adapter = new MainPagerAdapter(getChildFragmentManager(),
                context,
                zhihuDailyFragment,
                doubanMovieFragment,
                pexelsFragment);
        viewPager.setAdapter(adapter);
//        tabLayout.setTabTextColors(R.color.colorSecondaryText, R.color.colorPrimaryText);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "Zhihu", zhihuDailyFragment);
        manager.putFragment(outState, "Douban", doubanMovieFragment);
        manager.putFragment(outState, "Pexels", pexelsFragment);
    }

    public MainPagerAdapter getAdapter() {
        return adapter;
    }
}
