package com.homepaas.sls.ui.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.widget.NestGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/3/22.
 */

public class AllCategoriesItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;
    private static Context context;
    private List<ServiceItem> ServiceItemList;
    private static final int ITEM_TITLE = 1;            //标题布局
    private static final int ITEM_GRIDVIEW_Image = 2;   //有图片的gridview
    private static final int ITEM_GRIDVIES = 3;         //没有图片的gridview

    public AllCategoriesItemAdapter(Context context) {
        this.context = context;
    }

    public void setItemList(List<ServiceItem> ServiceItemList) {
        this.ServiceItemList = ServiceItemList;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        if (viewType == ITEM_TITLE) {
            view = layoutInflater.inflate(R.layout.all_categories_item_title, parent, false);
            ItemTitleView itemTitleView = new ItemTitleView(view);
            itemTitleView.setIsRecyclable(false);
            return itemTitleView;
        } else if (viewType == ITEM_GRIDVIEW_Image) {
            view = layoutInflater.inflate(R.layout.all_categories_item_gridview_image, parent, false);
            ItemGridViewImage itemGridViewImage = new ItemGridViewImage(view);
            itemGridViewImage.setIsRecyclable(false);
            return itemGridViewImage;
        } else {
            view = layoutInflater.inflate(R.layout.all_categories_item_gridview_text, parent, false);
            ItemGridViewText itemGridView = new ItemGridViewText(view);
            itemGridView.setIsRecyclable(false);
            return itemGridView;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceItem ServiceItem = ServiceItemList.get(position / 2);
        if (getItemViewType(position) == ITEM_TITLE) {
            ((ItemTitleView) holder).setItemTitleServiceName(ServiceItem);
        } else if (getItemViewType(position) == ITEM_GRIDVIEW_Image) {
            ((ItemGridViewImage) holder).setModeHotList(ServiceItem);
        } else {
            ((ItemGridViewText) holder).setModelOtherList(ServiceItem);
        }

    }

    @Override
    public int getItemCount() {
        return ServiceItemList == null ? 0 : ServiceItemList.size() * 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return ITEM_TITLE;
        } else {
            String serviceName = ServiceItemList.get(position / 2).getServiceName();
            if (!TextUtils.isEmpty(serviceName) && TextUtils.equals(serviceName, "热门")) {
                return ITEM_GRIDVIEW_Image;
            } else {
                return ITEM_GRIDVIES;
            }
        }
    }


    public static class ItemTitleView extends RecyclerView.ViewHolder {
        @Bind(R.id.item_title_service_name)
        TextView itemTitleServiceName;

        public ItemTitleView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItemTitleServiceName(ServiceItem ServiceItem) {
            itemTitleServiceName.setText(ServiceItem.getServiceName());
        }
    }

    public static class ItemGridViewImage extends RecyclerView.ViewHolder {
        @Bind(R.id.item_gradview_image)
        NestGridView itemGradviewImage;

        List<ServiceItem> ServiceItemHotList = new ArrayList<>();
        private AllCategoriesGridItemImageAdapter allCategoriesGridItemImageAdapter;

        public ItemGridViewImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            allCategoriesGridItemImageAdapter = new AllCategoriesGridItemImageAdapter(context);
            itemGradviewImage.setAdapter(allCategoriesGridItemImageAdapter);
            itemGradviewImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    ServiceItem ServiceItemHot = ServiceItemHotList.get(position);
                    if (ServiceItemHot != null && onItemClickListener != null) {
                        onItemClickListener.itemClickListener(ServiceItemHot);
                    }
                }
            });
        }

        public void setModeHotList(ServiceItem ServiceItem) {
            ServiceItemHotList.clear();
            ServiceItemHotList = ServiceItem.getSubItems();
            allCategoriesGridItemImageAdapter.setServiceItemHotList(ServiceItemHotList);
        }
    }

    public static class ItemGridViewText extends RecyclerView.ViewHolder {
        @Bind(R.id.item_gradview_text)
        NestGridView itemGradviewText;

        List<ServiceItem> ServiceItemTextList = new ArrayList<>();
        private AllCategoriesGridItemTextAdapter allCategoriesGridItemTextAdapter;

        public ItemGridViewText(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            allCategoriesGridItemTextAdapter = new AllCategoriesGridItemTextAdapter(context);
            itemGradviewText.setAdapter(allCategoriesGridItemTextAdapter);
            itemGradviewText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    ServiceItem serviceItemHot = ServiceItemTextList.get(position);
                    if (serviceItemHot != null && onItemClickListener != null) {
                        onItemClickListener.itemClickListener(serviceItemHot);
                    }
                }
            });
        }

        public void setModelOtherList(ServiceItem ServiceItem) {
            ServiceItemTextList.clear();
            ServiceItemTextList = ServiceItem.getSubItems();
            allCategoriesGridItemTextAdapter.setServiceTypeTextExList(ServiceItemTextList);
        }

    }

    public interface OnItemClickListener {
        void itemClickListener(ServiceItem serviceItem);
    }

    private static OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
