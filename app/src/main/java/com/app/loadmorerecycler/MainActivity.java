package com.app.loadmorerecycler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ViewStub mViewStub;
    private ArrayList<String> mList;
    private ListAdapter mListAdapter;
    private int size=20;
    private Handler mHandler=new Handler();
    ArrayList<String> listadd=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView= (RecyclerView) findViewById(R.id.recycler);
        mViewStub= (ViewStub) findViewById(R.id.load_more);

        mList=new ArrayList<>();
       for (int i=0;i<size;i++){
            mList.add("item textview"+i);
        }
        mListAdapter=new ListAdapter();
        mListAdapter.setData(mList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mListAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View lastView=recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount()-1);
                int position=recyclerView.getChildAdapterPosition(lastView);
                Log.e("text","positipn="+position+"  size="+(mListAdapter.getItemCount()-1));
                if (position==mListAdapter.getItemCount()-1){
                    mViewStub.setVisibility(View.VISIBLE);
                    loadMore();
                    //Toast.makeText(MainActivity.this, "最后一条数据", Toast.LENGTH_SHORT).show();
                }else {
                    mViewStub.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadMore() {
        listadd.clear();
        for (int i=mListAdapter.getItemCount();i<mListAdapter.getItemCount()+10;i++){
           listadd.add("item textview"+i);
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListAdapter.addAll(listadd);
                mViewStub.setVisibility(View.GONE);
            }
        },1000);

    }
}
