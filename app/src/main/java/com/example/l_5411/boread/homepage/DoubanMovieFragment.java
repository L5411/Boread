package com.example.l_5411.boread.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.l_5411.boread.R;
import com.example.l_5411.boread.adapter.DoubanMovieAdapter;
import com.example.l_5411.boread.bean.DoubanMovieListBean;
import com.example.l_5411.boread.detail.douban.MovieDetailActivity;
import com.example.l_5411.boread.interfaces.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 *
 * Created by L_5411 on 2017/3/12.
 */

public class DoubanMovieFragment extends Fragment
    implements DoubanMovieContract.View{

    private static final String TAG = DoubanMovieFragment.class.getSimpleName();

    private DoubanMovieContract.Presenter presenter;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private DoubanMovieAdapter adapter;

    public static DoubanMovieFragment newInstance() {
        return new DoubanMovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initViews(view);
        presenter.start();
        Log.i(TAG, "onCreateView");
        return view;
    }

    @Override
    public void setPresenter(DoubanMovieContract.Presenter presenter) {
        if(presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(final View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        // 上拉加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastPosition = -1;
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastPosition = manager.findLastVisibleItemPosition();
                }
                if(lastPosition == manager.getItemCount() - 1) {
                    presenter.loadMore();
                    showLoading();
                }
            }
        });
    }

    @Override
    public void showError() {
        Snackbar.make(getView(), R.string.loaded_failed, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showResult(final List<DoubanMovieListBean.SubjectsBean> list) {
        if(adapter == null) {
            adapter = new DoubanMovieAdapter(getContext(), list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(MovieDetailActivity.createDetailActivity(getContext(), list.get(position).getId()));
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
