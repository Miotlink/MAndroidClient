package com.homepaas.sls.ui.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/3/22.
 */

public class AllCategoriesMenuAdapter extends RecyclerView.Adapter<AllCategoriesMenuAdapter.AllCategoriesMenuView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<ServiceItem> ServiceItemList;
    private int selectPosition = 0;

    public AllCategoriesMenuAdapter(Context context) {
        this.context = context;
    }

    public void setMenuList(List<ServiceItem> ServiceItemList) {
        this.ServiceItemList = ServiceItemList;
        notifyDataSetChanged();
    }

    public void setPosittion(int lastSelectPosition,int selectPosition) {
        this.selectPosition = selectPosition;
        notifyItemChanged(lastSelectPosition);
        notifyItemChanged(selectPosition);
    }

    @Override
    public AllCategoriesMenuView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.all_categories_menu, parent, false);
        return new AllCategoriesMenuView(view);
    }

    @Override
    public void onBindViewHolder(final AllCategoriesMenuView holder, int position) {
        int defaultImage = R.mipmap.service_hot;
        Glide.with(context).load(ServiceItemList.get(holder.getAdapterPosition()).getIconUrl()).placeholder(defaultImage)
                .into(new ImageTarget(holder.menuImage));
        holder.menuServiceName.setText(ServiceItemList.get(holder.getAdapterPosition()).getServiceName());
        if (position == selectPosition) {
            holder.menuLin.setBackgroundResource(R.color.merchant_menu_background);
        } else {
            holder.menuLin.setBackgroundResource(R.color.white);
        }
        if (onMenuItemClickListener != null) {
            holder.menuLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMenuItemClickListener.menuItemClickListener(holder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return ServiceItemList == null ? 0 : ServiceItemList.size();
    }

    public static class AllCategoriesMenuView extends RecyclerView.ViewHolder {
        @Bind(R.id.menu_image)
        ImageView menuImage;
        @Bind(R.id.menu_service_name)
        TextView menuServiceName;
        @Bind(R.id.menu_lin)
        LinearLayout menuLin;

        public AllCategoriesMenuView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnMenuItemClickListener {
        void menuItemClickListener(int menuPosition);
    }

    private OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

}
