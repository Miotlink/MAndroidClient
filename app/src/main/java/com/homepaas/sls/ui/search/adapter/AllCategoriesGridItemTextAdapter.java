package com.homepaas.sls.ui.search.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;

import java.util.List;

/**
 * Created by JWC on 2017/3/22.
 * 全部分类页面右边没图片gridvieww的Adapter
 */

public class AllCategoriesGridItemTextAdapter extends BaseAdapter {
    private Context context;
    private List<ServiceItem> ServiceItemList;

    public AllCategoriesGridItemTextAdapter(Context context) {
        this.context = context;
    }

    public void setServiceTypeTextExList(List<ServiceItem> ServiceItemList) {
        this.ServiceItemList = ServiceItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ServiceItemList == null ? 0 : ServiceItemList.size();
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
                    .inflate(R.layout.all_categories_gridview_item_text, viewGroup, false);
        }
        TextView serviceName = BaseViewHolder.get(view, R.id.item_service_name_second);
        serviceName.setText(ServiceItemList.get(position).getServiceName());
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
