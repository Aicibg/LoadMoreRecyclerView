package com.app.loadmorerecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dh193 on 2016/8/27.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private ArrayList<String> lists;
    private final Object mLock=new Object();

    public void setData(ArrayList<String> data){
        lists=data;
    }

    public void addAll(Collection<String> list){
        synchronized (mLock) {
            if (null != lists) {
                lists.addAll(list);
            }
        }
        if (getItemCount() - list.size() != 0) {
            notifyItemRangeInserted(getItemCount() - list.size(), list.size());
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(),R.layout.item_list,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(lists.get(position));
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
          TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.textView);
        }

    }
}
