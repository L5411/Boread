package com.example.l_5411.boread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.l_5411.boread.R;
import com.example.l_5411.boread.bean.DoubanMovieListBean;
import com.example.l_5411.boread.interfaces.OnRecyclerViewOnClickListener;
import com.example.l_5411.boread.util.ScreenUtils;

import java.util.List;

/**
 * Created by L_5411 on 2017/5/19.
 */

public class DoubanMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DoubanMovieListBean.SubjectsBean> list;
    private Context context;
    private LayoutInflater inflater;
    private OnRecyclerViewOnClickListener listener;
    public DoubanMovieAdapter(Context context, List<DoubanMovieListBean.SubjectsBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.list_item_douban, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Holder) {
            DoubanMovieListBean.SubjectsBean subjectsBean = list.get(position);

            FrameLayout.LayoutParams layoutParams =
                    (FrameLayout.LayoutParams) ((Holder) holder).imageView.getLayoutParams();

            float itemWidth =
                    (ScreenUtils.getScreenWidth(((Holder) holder).imageView.getContext()) -  2) / 2;
            layoutParams.width = (int) itemWidth;
            float scale = itemWidth / 288;
            layoutParams.height = (int) (465 * scale);
            Glide.with(context).load(subjectsBean.getImages().getLarge())
                    .asBitmap()
                    .override(layoutParams.width, layoutParams.height)
                    .into(((Holder) holder).imageView);
            ((Holder) holder).textView.setText(subjectsBean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener) {
        this.listener = listener;
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private OnRecyclerViewOnClickListener listener;
        private TextView textView;

        public Holder(View itemView, final OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.movie_pic);
            this.textView = (TextView) itemView.findViewById(R.id.movie_name);
            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, getLayoutPosition());
                }
            });
        }
    }
}
