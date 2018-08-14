package com.example.xlc.monkey.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xlc.monkey.R;

/**
 * @author xlc
 * @date: 2018/8/10  16:35
 * @description:
 **/
public class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.ViewHolder> {

    private Context context;

    public SplashAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SplashAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_splash, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SplashAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_bg;
        public ViewHolder(View itemView) {
            super(itemView);
            item_bg = itemView.findViewById(R.id.item_bg);
        }
    }
}
