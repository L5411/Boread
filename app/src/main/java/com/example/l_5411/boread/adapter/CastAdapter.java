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
import com.example.l_5411.boread.bean.DoubanMovieBean;
import com.example.l_5411.boread.interfaces.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by L_5411 on 2017/5/23.
 */

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DoubanMovieBean.CastsBean> list;
    private LayoutInflater inflater;
    private OnRecyclerViewOnClickListener listener;

    public CastAdapter(Context context, DoubanMovieBean movie) {
        this.context = context;
        this.list = movie.getCasts();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.list_item_cast, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Holder) {
            ((Holder) holder).textView.setText(list.get(position).getName());
            Glide.with(context)
                    .load(list.get(position).getAvatars().getLarge())
                    .asBitmap()
                    .into(((Holder) holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener) {
        this.listener = listener;
    }

    class Holder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private OnRecyclerViewOnClickListener listener;

        public Holder(View itemView, final OnRecyclerViewOnClickListener listener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.cast_image);
            textView = (TextView) itemView.findViewById(R.id.cast_name);
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
