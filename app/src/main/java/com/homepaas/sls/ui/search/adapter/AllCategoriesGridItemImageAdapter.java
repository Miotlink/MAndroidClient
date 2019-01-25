package com.homepaas.sls.ui.search.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import java.util.List;

/**
 * Created by JWC on 2017/3/22.
 * 全部分类页面右边有图片（热门）gridvieww的Adapter
 */

public class AllCategoriesGridItemImageAdapter extends BaseAdapter {
    private Context context;
    private List<ServiceItem> serviceItemList;

    public AllCategoriesGridItemImageAdapter(Context context) {
        this.context = context;
    }

    public void setServiceItemHotList(List<ServiceItem> serviceItemList) {
        this.serviceItemList = serviceItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return serviceItemList == null ? 0 : serviceItemList.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.all_categories_gridview_item_image, viewGroup, false);
        }
        ImageView imageView = BaseViewHolder.get(view, R.id.item_image);
        TextView serviceName = BaseViewHolder.get(view, R.id.item_service_name);
        serviceName.setText(serviceItemList.get(position).getServiceName());
        int defaultImage = R.mipmap.service_hot;
        Glide.with(context).load(serviceItemList.get(position).getIconUrl()).placeholder(defaultImage)
                .into(new ImageTarget(imageView));
        return view;
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
