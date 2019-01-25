package com.homepaas.sls.ui.homepage_3_3_0.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.domain.entity.ShortCutContent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2017/3/21.
 */

public class HotServiceAdapter extends RecyclerView.Adapter<HotServiceAdapter.HotServicerViewHolder> {


    private Context context;
    private List<ShortCutContent> datas;
    private LayoutInflater inflater;

    public HotServiceAdapter() {
        itemViews = new ArrayList<>();
        positions = new ArrayList<>();
        toolTips = new ArrayList<>();
    }

    public void setDatas(List<ShortCutContent> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public HotServicerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(context);
        }
        View view = inflater.inflate(R.layout.hot_service_link_item, parent, false);
        return new HotServicerViewHolder(view);
    }

    public interface OnHandleServiceItem {
        void handle(ServiceItem serviceItem);
    }

    private OnHandleServiceItem onHandleServiceItem;

    public void setOnHandleServiceItem(OnHandleServiceItem onHandleServiceItem) {
        this.onHandleServiceItem = onHandleServiceItem;
    }

    List<View> itemViews;
    List<Integer> positions;
    List<String> toolTips;

    public interface OnDrawPopuListner {
        void drawPopu(List<Integer> position, List<View> itemView, List<String> title);
    }

    private OnDrawPopuListner onDrawPopuListner;

    public void setOnDrawPopuListner(OnDrawPopuListner onDrawPopuListner) {
        this.onDrawPopuListner = onDrawPopuListner;
    }

    @Override
    public void onBindViewHolder(final HotServicerViewHolder holder, final int position) {
        if (datas != null) {
            holder.bind(datas.get(holder.getAdapterPosition()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHandleServiceItem != null) {
                        onHandleServiceItem.handle(datas.get(position).getService());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 10 : datas.size();
    }

    public class HotServicerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.icon)
        RoundedImageView icon;
        @Bind(R.id.icon_bg)
        RelativeLayout iconBg;
        @Bind(R.id.text)
        TextView text;

        public HotServicerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(ShortCutContent shortCutContent) {

            switch (getAdapterPosition()) {
                case 0:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_1);
                    break;
                case 1:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_2);
                    break;
                case 2:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_3);
                    break;
                case 3:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_4);
                    break;
                case 4:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_5);
                    break;
                case 5:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_6);
                    break;
                case 6:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_7);
                    break;
                case 7:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_8);
                    break;
                case 8:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_9);
                    break;
                case 9:
                    iconBg.setBackgroundResource(R.mipmap.client_v330_ic_homepage_circle_10);
                    break;

            }
            if (shortCutContent != null) {
                Glide.with(icon.getContext())
                        .load(shortCutContent.getIconUrl())
                        .centerCrop()
                        .placeholder(R.mipmap.client_v330_ic_homepage_more_white)
                        .error(R.mipmap.client_v330_ic_homepage_more_white)
                        .into(icon);
                text.setText(shortCutContent.getTitle());
            }
        }

    }
}
