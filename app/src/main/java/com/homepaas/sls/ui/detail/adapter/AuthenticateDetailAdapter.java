package com.homepaas.sls.ui.detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.mvp.model.IServiceInfo.SystemCertification;

import java.util.List;

/**
 * on 2015/12/25 0025
 *
 * @author zhudongjie .
 */
public class AuthenticateDetailAdapter extends BaseAdapter {

    private List<SystemCertification> mDataList;

    private LayoutInflater mInflater;

    public AuthenticateDetailAdapter(List<SystemCertification> dataList) {
        mDataList = dataList;
    }

    public void setDataList(List<SystemCertification> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.authenticate_detail_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SystemCertification certification = mDataList.get(position);
        Glide.with(parent.getContext())
                .load(certification.getImage())
                .placeholder(R.mipmap.authenticate_default_large)
                .into(viewHolder.icon);
        viewHolder.explanation.setText(certification.getDescription());
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView explanation;

        public ViewHolder(View itemView) {
            icon = (ImageView) itemView.findViewById(R.id.icon);
            explanation = (TextView) itemView.findViewById(R.id.explanation);
        }
    }
}
