package com.example.l_5411.boread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.l_5411.boread.R;
import com.example.l_5411.boread.bean.PexelsBean;
import com.example.l_5411.boread.interfaces.OnRecyclerViewOnClickListener;
import com.example.l_5411.boread.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L_5411 on 2017/3/20.
 */

public class PexelsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<PexelsBean.PhotosBean> list = new ArrayList<>();
    private OnRecyclerViewOnClickListener listener;

    public PexelsAdapter(Context context, List<PexelsBean.PhotosBean> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflater.inflate(R.layout.list_item_pexels, parent, false),listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalHolder) {
            PexelsBean.PhotosBean item = list.get(position);

            FrameLayout.LayoutParams layoutParams =
                    (FrameLayout.LayoutParams) ((NormalHolder) holder).imageView.getLayoutParams();

            float itemWidth =
                    (ScreenUtils.getScreenWidth(((NormalHolder) holder).imageView.getContext()) -  9) / 2;
            layoutParams.width = (int) itemWidth;
            float scale = itemWidth / item.getWidth();
            layoutParams.height = (int) (item.getHeight() * scale);
            ((NormalHolder) holder).imageView.setLayoutParams(layoutParams);
            Glide.with(context)
                    .load(item.getSrc().getMedium())
//                    .asBitmap()
//                    .centerCrop()
                    .override(layoutParams.width, layoutParams.height)
                    .into(((NormalHolder)holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.listener = listener;
    }

    class NormalHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        OnRecyclerViewOnClickListener listener;

        public NormalHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.imageView =(ImageView) itemView.findViewById(R.id.pexels_pic);
            this.listener = listener;
            itemView.setOnClickListener(this);

//            imageView.setOriginalSize(50, 50);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(imageView, getLayoutPosition());
        }
    }
}
