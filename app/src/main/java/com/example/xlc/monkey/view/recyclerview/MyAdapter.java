package com.example.xlc.monkey.view.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xlc.monkey.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xlc
 * @date:2019/6/26
 * @descirbe:
 */
public class MyAdapter extends RecyclerView.Adapter {

    List<String> data = new ArrayList<>();

    public MyAdapter() {
        initData();
    }

    private void initData() {
        String[] str = {"AAAAA", "BBBBB", "CCCCC"};
        for (int i = 0; i < 9; i++) {
            data.add(i, str[i % 3]);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).btn.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn =itemView.findViewById(R.id.btn);
        }
    }
}
