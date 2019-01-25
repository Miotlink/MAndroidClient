package com.homepaas.sls.ui.merchantservice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.Holder> {

    List<Object> datas;
    LayoutInflater inflater;
    Context context;

    public CommentListAdapter(List<Object> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.item_merchant_comment_layout,parent,false));
    }


    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
