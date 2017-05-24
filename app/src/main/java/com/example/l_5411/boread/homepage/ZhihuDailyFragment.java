package com.example.l_5411.boread.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.l_5411.boread.R;
import com.example.l_5411.boread.adapter.ZhihuDailyNewsAdapter;
import com.example.l_5411.boread.bean.ZhihuDailyNews;
import com.example.l_5411.boread.interfaces.OnRecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by L_5411 on 2017/3/11.
 */

public class ZhihuDailyFragment extends Fragment
    implements ZhihuDailyContract.View{

    private ZhihuDailyContract.Presenter presenter;
    private ZhihuDailyNewsAdapter adapter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    public static ZhihuDailyFragment newInstance() {
        return new ZhihuDailyFragment();
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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastPosition = manager.findLastVisibleItemPosition();
                    int totleItemCount = manager.getItemCount();

                    if (lastPosition == totleItemCount - 1) {
                        Calendar c = Calendar.getInstance();
                        c.set(mYear, mMonth, --mDay);
                        presenter.loadMore(c.getTimeInMillis());
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        if(presenter != null){
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void showError() {
        Snackbar.make(getView(), R.string.loaded_failed, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                }).show();
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
    public void showResults(ArrayList<ZhihuDailyNews.StoriesBean> list) {
        if(adapter == null) {
            adapter = new ZhihuDailyNewsAdapter(getContext(), list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    presenter.read(position);
                }
            });
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

}
