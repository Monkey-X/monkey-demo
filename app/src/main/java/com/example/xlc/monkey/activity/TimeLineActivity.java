package com.example.xlc.monkey.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.adapter.TimeLineAdapter;
import com.example.xlc.monkey.base.BaseActivity;
import com.example.xlc.monkey.entity.Trace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 时间轴
 */
public class TimeLineActivity extends BaseActivity {


    @BindView(R.id.rcycleView)
    RecyclerView mRcycleView;
    private List<Trace> mList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_time_line;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mList.add(new Trace("2016-05-25 17:48:00", "[沈阳市] [沈阳和平五部]的派件已签收 感谢使用中通快递,期待再次为您服务!"));
        mList.add(new Trace("2016-05-25 14:13:00", "[沈阳市] [沈阳和平五部]的东北大学代理点正在派件 电话:18040xxxxxx 请保持电话畅通、耐心等待"));
        mList.add(new Trace("2016-05-25 13:01:04", "[沈阳市] 快件到达 [沈阳和平五部]"));
        mList.add(new Trace("2016-05-25 12:19:47", "[沈阳市] 快件离开 [沈阳中转]已发往[沈阳和平五部]"));
        mList.add(new Trace("2016-05-25 11:12:44", "[沈阳市] 快件到达 [沈阳中转]"));
        mList.add(new Trace("2016-05-24 03:12:12", "[嘉兴市] 快件离开 [杭州中转部]已发往[沈阳中转]"));
        mList.add(new Trace("2016-05-23 21:06:46", "[杭州市] 快件到达 [杭州汽运部]"));
        mList.add(new Trace("2016-05-23 18:59:41", "[杭州市] 快件离开 [杭州乔司区]已发往[沈阳]"));
        mList.add(new Trace("2016-05-23 18:35:32", "[杭州市] [杭州乔司区]的市场部已收件 电话:18358xxxxxx"));

    }

    @Override
    protected void initData() {
        TimeLineAdapter lineAdapter = new TimeLineAdapter(mList, this);
        mRcycleView.setLayoutManager(new LinearLayoutManager(this));
        mRcycleView.setAdapter(lineAdapter);
    }

}
