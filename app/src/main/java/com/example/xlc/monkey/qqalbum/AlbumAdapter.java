package com.example.xlc.monkey.qqalbum;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.xlc.monkey.R;

import java.util.List;

/**
 * @author:xlc
 * @date:2018/10/29
 * @descirbe:
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private Context mContext;
    private List<AlbumBean> datas;
    private final LayoutInflater mInflater;

    public AlbumAdapter(Context context, List<AlbumBean> datas){
        this.mContext = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<AlbumBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void refreshData(List<AlbumBean> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(mInflater.inflate(R.layout.item_album,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlbumBean item = datas.get(position);
        Glide.with(mContext).load(item.getUrl()).into(holder.item_iv);
    }



    @Override
    public int getItemCount() {
        return datas!=null? datas.size():0;
    }

    public AlbumBean getItem(int position){
        return datas.get(position);
    }

    public List<AlbumBean> getDatas() {
        return datas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView item_iv;

        public ViewHolder(View itemView) {
            super(itemView);
            item_iv = itemView.findViewById(R.id.item_iv);
        }
    }
}
