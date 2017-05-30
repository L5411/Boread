package com.example.l_5411.boread.homepage;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.l_5411.boread.app.VolleySingleton;
import com.example.l_5411.boread.bean.PexelsBean;
import com.example.l_5411.boread.bean.StringModelImpl;
import com.example.l_5411.boread.util.Api;
import com.example.l_5411.boread.util.NetworkState;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by L_5411 on 2017/3/12.
 */

public class PexelsPresenter implements PexelsContract.Presenter{

    private static final String TAG = PexelsPresenter.class.getSimpleName();

    private PexelsContract.View view;
    private Context context;
    private StringModelImpl model;
    private int page;
    private Gson gson = new Gson();
    private String url;
    private ArrayList<PexelsBean.PhotosBean> list = new ArrayList<>();

    public PexelsPresenter(Context context, PexelsContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        page = 1;
        url = Api.getPexelsApiPopular(page);
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void loadPosts(String url, final boolean clearing) {
        if(clearing) {
            view.showLoading();
        }
        if(NetworkState.networkConnected(context)) {
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    PexelsBean bean = gson.fromJson(response, PexelsBean.class);
                    if(clearing) {
                        list.clear();
                    }
                    for (PexelsBean.PhotosBean item:bean.getPhotos() ) {
                        list.add(item);
                    }
                    view.showResults(list);
                    view.stopLoading();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Authorization", Api.PEXELS_API_KEY);
                    return params;
                }
            };
            VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
        } else {
            view.stopLoading();
            view.showError();
        }
    }

    @Override
    public void refresh() {
        setPageZero();
        loadPosts(url, true);
    }

    @Override
    public void loadMore() {
        setNextPage();
        loadPosts(url, false);
    }

    @Override
    public String getUrl(int position) {
        return list.get(position).getSrc().getLarge();
    }

    @Override
    public void search(String tag) {
        url = Api.getPexelsApiSearch(page, tag);
        loadPosts(url, true);
    }

    @Override
    public void cancelSearch() {
        page = 1;
        url = Api.getPexelsApiPopular(page);
        loadPosts(url, true);
    }

    private void setPageZero() {
        page = 1;
        url = Api.replaceUriParameter(Uri.parse(url), "page", Integer.toString(page)).toString();
    }
    private void setNextPage() {
        page++;
        url = Api.replaceUriParameter(Uri.parse(url), "page", Integer.toString(page)).toString();
    }
}
