package com.example.xlc.monkey.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.entity.Trace;

import java.util.List;


/**
 * created by xlc at 2018/8/8
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.viewHolder> {


    private static final int TYPE_TOP = 0X12;
    private static final int TYPE_NORMAL = 0x14;
    private List<Trace> data;
    private Context mContext;

    public TimeLineAdapter(List<Trace> data, Context context) {

        this.data = data;
        mContext = context;
    }

    @NonNull
    @Override
    public TimeLineAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_time_line, parent, false);
        viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TimeLineAdapter.viewHolder holder, int position) {
        viewHolder itemHolder = holder;
        if (getItemViewType(position) == TYPE_TOP) {
            itemHolder.topline.setVisibility(View.INVISIBLE);
            itemHolder.acceptTime.setTextColor(0xff555555);
            itemHolder.acceptStation.setTextColor(0xff555555);
            itemHolder.dot.setBackgroundResource(R.drawable.timeline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            itemHolder.topline.setVisibility(View.VISIBLE);
            itemHolder.acceptTime.setTextColor(0xff999999);
            itemHolder.acceptStation.setTextColor(0xff999999);
            itemHolder.dot.setBackgroundResource(R.drawable.timeline_dot_normal);
        }

        itemHolder.bindView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        private TextView topline;
        private TextView dot;
        private TextView acceptTime;
        private TextView acceptStation;

        public viewHolder(View itemView) {
            super(itemView);
            topline = itemView.findViewById(R.id.topline);
            dot = itemView.findViewById(R.id.dot);
            acceptStation = itemView.findViewById(R.id.acceptStation);
            acceptTime = itemView.findViewById(R.id.acceptTime);
        }

        public void bindView(Trace trace) {
            acceptTime.setText(trace.getAcceptTime());
            acceptStation.setText(trace.getAcceptStation());
        }
    }
}
