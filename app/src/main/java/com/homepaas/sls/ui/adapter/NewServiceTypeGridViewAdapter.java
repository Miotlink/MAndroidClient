package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JWC on 2016/12/16.
 */

public class NewServiceTypeGridViewAdapter extends BaseAdapter {
    List<ServiceTypeEx> serviceTypeExList = new ArrayList<>();
    private Context context;

    public NewServiceTypeGridViewAdapter(Context context) {
        this.context = context;

    }

    public void setServiceTypeExList(List<ServiceTypeEx> list) {
        this.serviceTypeExList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return serviceTypeExList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.new_service_content_gridview_item, parent, false);
        }
        ImageView imageView = BaseViewHolder.get(convertView, R.id.item_image);
        TextView textView = BaseViewHolder.get(convertView, R.id.item_text);
        View view = BaseViewHolder.get(convertView, R.id.vertical_line);
        textView.setText(serviceTypeExList.get(position).getTypeName());
        int defaultImage = R.mipmap.service_hot;
        Glide.with(context).load(serviceTypeExList.get(position).getIcon1()).placeholder(defaultImage)
                .into(new ImageTarget(imageView));
        if (serviceTypeExList != null) {
            if (serviceTypeExList.size() > 3) {
                if (position == 0 || position == 3) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            } else if (serviceTypeExList.size() > 1 && serviceTypeExList.size() <= 3) {
                if (position == 0) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            } else {
                view.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    public static class BaseViewHolder {
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }
}
