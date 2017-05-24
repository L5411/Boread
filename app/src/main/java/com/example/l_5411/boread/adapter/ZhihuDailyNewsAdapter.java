package com.example.l_5411.boread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.l_5411.boread.R;
import com.example.l_5411.boread.bean.ZhihuDailyNews;
import com.example.l_5411.boread.interfaces.OnRecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L_5411 on 2017/3/12.
 */

public class ZhihuDailyNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<ZhihuDailyNews.StoriesBean> list = new ArrayList<>();
    private OnRecyclerViewOnClickListener listener;

    // list_item
    private static final int TYPE_NORMAL = 0;
    // list_footer，加载更多
    private static final int TYPE_FOOTER = 1;

    public ZhihuDailyNewsAdapter(Context context, List<ZhihuDailyNews.StoriesBean> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new NormalHolder(inflater.inflate(R.layout.list_item_zhihu, parent, false), listener);
            case TYPE_FOOTER:
                return new FooterHolder(inflater.inflate(R.layout.list_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalHolder){
            ZhihuDailyNews.StoriesBean item = list.get(position);

            if(item.getImages().get(0) == null) {
                ((NormalHolder)holder).imageView.setImageResource(R.drawable.placeholder);
            } else {
                Glide.with(context)
                        .load(item.getImages().get(0))
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalHolder)holder).imageView);
            }
            ((NormalHolder)holder).titleView.setText(item.getTitle());
        }
    }

    // 含有footer size+1
    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == list.size()) {
            return TYPE_FOOTER;
        }
        return  TYPE_NORMAL;
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener) {
        this.listener = listener;
    }

    public class NormalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleView;
        ImageView imageView;
        OnRecyclerViewOnClickListener listener;

        public NormalHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.text_view_title);
            imageView =(ImageView) itemView.findViewById(R.id.zhihu_image_view);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getLayoutPosition());
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

}
