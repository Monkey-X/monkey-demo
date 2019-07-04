package com.example.xlc.monkey.activity;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.base.BaseActivity;
import com.example.xlc.monkey.view.recyclerview.LooperLayoutManager;
import com.example.xlc.monkey.view.recyclerview.MyAdapter;

public class RecyclerActivity extends BaseActivity {


    private RecyclerView recyclerView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recycler;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData() {
        recyclerView.setAdapter(new MyAdapter());
        LooperLayoutManager layoutManager = new LooperLayoutManager();
        layoutManager.setLooperEnable(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    class myTask extends AsyncTask<String,Object,Long>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
