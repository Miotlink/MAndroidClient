package com.homepaas.sls.util.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {

    private List<T> data;
    protected Context context;
    protected int layoutId;

    public CommonAdapter(Context context, int layoutId) {
        this(context, layoutId, new ArrayList<T>());
    }

    public CommonAdapter(Context context, int layoutId, List<T> data) {
        this.context = context;
        this.layoutId = layoutId;        this.data = data;
    }

    public Context getContext(){
        return this.context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, layoutId, position);
        convert(holder, data.get(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T model, int position);

    public void updateList(List<T> data) {
        if (data != null) {
            this.data = data;
            this.notifyDataSetChanged();
        }
    }

    public void append(List<T> data) {
        if (data != null) {
            this.data.addAll(data);
            this.notifyDataSetChanged();
        }
    }

    public void append(T data) {
        if (data != null) {
            this.data.add(data);
            this.notifyDataSetChanged();
        }
    }

    public void clear() {
        if (this.data != null) {
            this.data.clear();
            this.notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return data;
    }

}
