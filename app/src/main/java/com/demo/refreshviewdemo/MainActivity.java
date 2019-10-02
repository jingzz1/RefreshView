package com.demo.refreshviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.refreshviewdemo.refresh.RefreshView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RefreshView refreshView;
    private MyAdapter adapter;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshView = findViewById(R.id.refresh);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        refreshView.setHeaderRefreshListener(refreshView1 -> {
            adapter.setNewData(null);
            position = 0;
            addData();
        });

        refreshView.setFooterRefreshListener(refreshView1 -> {
            addData();
        });
        //关闭下拉
//        refreshView.setCanRefreshHeader(false);
        //关闭上滑
//        refreshView.setCanRefreshfooter(false);

        refreshView.openHeader();
    }

    private void addData() {
       new Handler().postDelayed(() -> {

           List<String> items = new ArrayList<>();
           for (int i = 0 ; i < 20 ;i++){
               items.add("条目-->"+(++position));
           }

           adapter.addData(items);
           refreshView.headerComplete();
           refreshView.footerComplete();

       },3000);

    }


    private static class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter() {
            super(R.layout.item_layout);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text,item);
        }
    }

}
