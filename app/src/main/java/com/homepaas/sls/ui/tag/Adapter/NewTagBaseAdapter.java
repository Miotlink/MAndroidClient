package com.homepaas.sls.ui.tag.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;


import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/16.
 */
public class NewTagBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private int selectPosition=-1;

    public NewTagBaseAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setPosition(int selectPosition){
        this.selectPosition=selectPosition;
        notifyDataSetChanged();
    }

    public  void setList( List<String> mList){
        this.mList=mList;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.newtagview, null);
            holder = new ViewHolder(convertView);
            holder.tagBtn = (Button) convertView.findViewById(R.id.tag_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String text = getItem(position).toString();
        if (position==selectPosition){
            holder.tagBtn.setTextColor(Color.parseColor("#27bbf3"));
        }else{
            holder.tagBtn.setTextColor(Color.parseColor("#cccccc"));
        }
        holder.tagBtn.setText(text);
        return convertView;
    }





    static class ViewHolder {
        @Bind(R.id.tag_btn)
        Button tagBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
