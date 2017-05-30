package com.example.l_5411.boread.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.l_5411.boread.R;
import com.example.l_5411.boread.adapter.PexelsAdapter;
import com.example.l_5411.boread.bean.PexelsBean;
import com.example.l_5411.boread.detail.PhotoActivity;
import com.example.l_5411.boread.interfaces.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by L_5411 on 2017/3/12.
 */

public class PexelsFragment extends Fragment
    implements PexelsContract.View{

    private static final String TAG = PexelsFragment.class.getSimpleName();
    private PexelsContract.Presenter presenter;
    private PexelsAdapter adapter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    public static PexelsFragment newInstance(){
        return new PexelsFragment();
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
        Log.i(TAG, "Start");


        return view;
    }

    @Override
    public void setPresenter(PexelsContract.Presenter presenter) {
        if(presenter != null){
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottom = manager.findLastVisibleItemPositions(new int[2])[1]
                        >= adapter.getItemCount() - 2;
                Log.i(TAG, "addOnScrollListener" + isBottom + refreshLayout.isRefreshing());
                Log.i(TAG, "" + Integer.toString(adapter.getItemCount() - 1) + manager.findLastVisibleItemPositions(new int[2])[1]);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(!refreshLayout.isRefreshing() && isBottom) {
                        presenter.loadMore();
                        Log.i(TAG, "loadMore");
                    }
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
    public void showResults(List<PexelsBean.PhotosBean> list) {
        if(adapter == null) {
            adapter = new PexelsAdapter(getContext(), list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    enterPicture(view, presenter.getUrl(position));
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void enterPicture(View v,String url){
        Intent intent = PhotoActivity.newIntent(getContext(), url);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            getActivity(), v, getString(R.string.transition_picture));
        try {
            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
    }
}
