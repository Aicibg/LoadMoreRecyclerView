package com.app.loadmorerecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ViewStub mViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView= (RecyclerView) findViewById(R.id.recycler);
        mViewStub= (ViewStub) findViewById(R.id.load_more);


    }
}
