package com.homepaas.sls.ui.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.ui.order.chooseservice.SelectServiceItemFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Date: 2016/9/13.
 * Author: fmisser
 * Describe:
 */

public class ServiceTypeGridViewAdapter extends BaseAdapter {

    List<ServiceTypeEx> serviceTypeExList = new ArrayList<>();

    public ServiceTypeGridViewAdapter() {

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
                    .inflate(R.layout.service_content_gridview_item, parent, false);
        }
        TextView textView = BaseViewHolder.get(convertView, R.id.tv_item);
        textView.setText(serviceTypeExList.get(position).getTypeName());
        if(position==5){
            if(serviceTypeExList.get(position).getTypeName().equals("更多")){
                textView.setTextColor(Color.parseColor("#3FBEF9"));
            }else {
                textView.setTextColor(Color.parseColor("#333333"));
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
