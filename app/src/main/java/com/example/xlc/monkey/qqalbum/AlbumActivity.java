package com.example.xlc.monkey.qqalbum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xlc.monkey.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<AlbumBean> albumList = new ArrayList<>();
    private AlbumAdapter mAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mRecyclerView = findViewById(R.id.recyclerview);
        initData();
        init();
    }

    private void init() {
        mAlbumAdapter = new AlbumAdapter(this, new ArrayList<AlbumBean>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return setSpanSize(position, mAlbumAdapter.getDatas());
            }
        });
        EaseItemDecoration item = new EaseItemDecoration(this, mAlbumAdapter.getDatas());
        mRecyclerView.addItemDecoration(item);
        mRecyclerView.setAdapter(mAlbumAdapter);
        mAlbumAdapter.setData(albumList);
        item.setDatas(mAlbumAdapter.getDatas());
    }

    private int setSpanSize(int position, List<AlbumBean> datas) {
        int count ;
        int d;
        if ((position+1<datas.size())&& position>0) {
            if (!datas.get(position).getSubId().equals(datas.get(position+1).getSubId())) {
                mAlbumAdapter.getItem(position + 1).value = 2 - (mAlbumAdapter.getItem(position).value + position) % 3 + mAlbumAdapter.getItem(position).value;
                d = 2 - (mAlbumAdapter.getItem(position).value + position) % 3;
                if (d == 2) {
                    count = 3;
                } else if (d == 1) {
                    count = 2;
                } else {
                    count = 1;
                }
            }else{
                mAlbumAdapter.getItem(position + 1).value = mAlbumAdapter.getItem(position).value;
                count = 1;
            }

        }else if (position == 0){
            if (mAlbumAdapter.getDatas().size() > 1) {
                if ((!datas.get(position).getSubId().equals(datas.get(position + 1).getSubId()))) {
                    mAlbumAdapter.getItem(1).value = 2;
                    count = 3;
                } else {
                    count = 1;
                    mAlbumAdapter.getItem(1).value = 0;
                }
            } else {
                count = 1;
            }
        }else{
            count = 1;
        }
        return count;
    }

    private void initData() {
        int position = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < position; j++) {
                AlbumBean albumBean = new AlbumBean();
                albumBean.setSubId(position+"");
                albumBean.setTitle("第"+position+"个标题");
                albumBean.setUrl(R.mipmap.ic_launcher);
                albumList.add(albumBean);
            }

            position++;
        }
    }
}
