package com.homepaas.sls.ui.homepage_3_3_0.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.RecommendBlock;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.widget.SpaceItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/22.
 */

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NagvitionViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<RecommendBlock> datas;

    public NavigationAdapter() {
    }

    public void setDatas(List<RecommendBlock> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public NagvitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(context);
        }
        View view = inflater.inflate(R.layout.nagviation_layout, parent, false);
        return new NagvitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NagvitionViewHolder holder, final int position) {
        if (datas != null) {
            holder.bind(datas.get(holder.getAdapterPosition()));
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCheckMoreService != null) {
                        onCheckMoreService.check(datas.get(position).getServiceId());
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 4 : datas.size();
    }

    public interface OnCheckMoreService {
        void check(String serviceId);
    }

    private OnCheckMoreService onCheckMoreService;

    public void setOnCheckMoreService(OnCheckMoreService onCheckMoreService) {
        this.onCheckMoreService = onCheckMoreService;
    }

    public class NagvitionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.service_navigation_title_1)
        TextView serviceNavigationTitle1;
        @Bind(R.id.service_navigation_subtitle_1)
        TextView serviceNavigationSubtitle1;
        @Bind(R.id.more)
        TextView more;
        @Bind(R.id.service_navigation_recyclerview_1)
        RecyclerView serviceNavigationRecyclerview1;
        @Bind(R.id.service_navigation_1)
        LinearLayout serviceNavigation1;

        private int recyclerviewPadding;
        private int decoration;

        public NagvitionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            decoration = context.getResources().getDimensionPixelSize(R.dimen.border);
            serviceNavigationRecyclerview1.addItemDecoration(new SpaceItemDecoration(decoration));
            recyclerviewPadding = serviceNavigationRecyclerview1.getPaddingLeft() + serviceNavigationRecyclerview1.getPaddingRight();
        }

        public void bind(RecommendBlock recommendBlock) {
            if (recommendBlock != null) {
                serviceNavigationTitle1.setText(recommendBlock.getTitle());
                serviceNavigationSubtitle1.setText(recommendBlock.getSubTitle());
                //服务数量大于3条，底部查看更多布局
                if (recommendBlock.getProductCount() > 3) {
                    more.setVisibility(View.VISIBLE);
                } else {
                    more.setVisibility(View.GONE);
                }
                if (recommendBlock.getRecommendItems() != null && recommendBlock.getRecommendItems().size() > 0) {
                    List<ServiceItem> recommendItems = recommendBlock.getRecommendItems();
                    ServiceNavigationAdapter adapter = new ServiceNavigationAdapter(recommendItems);
                    adapter.setDecoration(decoration);
                    adapter.setRecyclerviewPadding(recyclerviewPadding);
//                    serviceNavigationRecyclerview1.addItemDecoration(new SpaceItemDecoration(1));
                    serviceNavigationRecyclerview1.setNestedScrollingEnabled(false);
                    serviceNavigationRecyclerview1.setAdapter(adapter);
                }
            }
        }
    }
}
