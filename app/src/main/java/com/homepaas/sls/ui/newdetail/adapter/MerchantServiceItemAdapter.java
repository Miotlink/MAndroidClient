package com.homepaas.sls.ui.newdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.MerchantServiceTypePrice;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/2/13.
 */

public class MerchantServiceItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_THREE = 0; //没有子集服务
    private static final int ITEM_FOUR = 1;//有子集服务
    private static Context context;

    public MerchantServiceItemAdapter(Context context) {
        this.context = context;
    }

    private LayoutInflater inflater;

    private List<MerchantServiceTypePrice> list = new ArrayList<>();

    private boolean hasFourService = false;

    public void setMerchantServiceTypePrice(MerchantServiceTypePrice threeMerchantServiceTypePrice) {
        list.clear();
        if (threeMerchantServiceTypePrice != null) {
            if (threeMerchantServiceTypePrice.getChildList() != null && !threeMerchantServiceTypePrice.getChildList().isEmpty()) {
                hasFourService = true;
                list.addAll(threeMerchantServiceTypePrice.getChildList());
            } else {
                hasFourService = false;
                list.add(threeMerchantServiceTypePrice);
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (hasFourService) {
            return ITEM_FOUR;
        } else {
            return ITEM_THREE;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        if (viewType == ITEM_THREE) {
            view = inflater.inflate(R.layout.merchant_service_item__three_adapter, parent, false);
            MerchantServiceThreeView merchantServiceThreeView = new MerchantServiceThreeView(view);
            merchantServiceThreeView.setIsRecyclable(false);
            return merchantServiceThreeView;
        } else {
            view = inflater.inflate(R.layout.merchant_service_item__four_adapter, parent, false);
            MerchantServiceFourView merchantServiceFourView = new MerchantServiceFourView(view);
            merchantServiceFourView.setIsRecyclable(false);
            return merchantServiceFourView;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MerchantServiceTypePrice merchantServiceTypePrice = list.get(position);
        if (hasFourService) {
            ((MerchantServiceFourView) holder).setFourDate(merchantServiceTypePrice);
            if (onFourBuyClickListener != null) {
                ((MerchantServiceFourView) holder).fourBuyService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onFourBuyClickListener.onFourBuyClick(merchantServiceTypePrice);
                    }
                });
            }
            if (onGoDetailClickListener != null) {
                ((MerchantServiceFourView) holder).fourGoDetailRel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onGoDetailClickListener.onGoDetailClick(merchantServiceTypePrice);
                    }
                });
            }
        } else {
            ((MerchantServiceThreeView) holder).setThreeDate(merchantServiceTypePrice);
            if (onThreeBuyClickListener != null) {
                ((MerchantServiceThreeView) holder).threeBuyService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onThreeBuyClickListener.onThreeBuyClick(merchantServiceTypePrice);
                    }
                });
            }
            if (onGoDetailClickListener != null) {
                ((MerchantServiceThreeView) holder).threeGoDetailRel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onGoDetailClickListener.onGoDetailClick(merchantServiceTypePrice);
                    }
                });
            }
        }


    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MerchantServiceThreeView extends RecyclerView.ViewHolder {
        @Bind(R.id.three_service_name)
        TextView threeServiceName;
        @Bind(R.id.three_service_price)
        TextView threeServicePrice;
        @Bind(R.id.three_buy_service)
        TextView threeBuyService;
        @Bind(R.id.three_go_detail_rel)
        RelativeLayout threeGoDetailRel;

        public MerchantServiceThreeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setThreeDate(MerchantServiceTypePrice merchantServiceTypePrice) {
            threeServiceName.setText(merchantServiceTypePrice.getName());
            threeServicePrice.setText(merchantServiceTypePrice.getPrice());
        }
    }

    public static class MerchantServiceFourView extends RecyclerView.ViewHolder {
        @Bind(R.id.four_picture)
        ImageView fourPicture;
        @Bind(R.id.four_go_detail)
        ImageView fourGoDetail;
        @Bind(R.id.four_service_name)
        TextView fourServiceName;
        @Bind(R.id.four_service_content)
        TextView fourServiceContent;
        @Bind(R.id.four_service_price)
        TextView fourServicePrice;
        @Bind(R.id.four_buy_service)
        TextView fourBuyService;
        @Bind(R.id.four_go_detail_rel)
        RelativeLayout fourGoDetailRel;

        public MerchantServiceFourView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setFourDate(MerchantServiceTypePrice merchantServiceTypePrice) {
            fourServiceName.setText(merchantServiceTypePrice.getName());
            fourServiceContent.setText(merchantServiceTypePrice.getDescription());
            fourServicePrice.setText(merchantServiceTypePrice.getPrice());
            Glide.with(context).load(merchantServiceTypePrice.getIcon()).placeholder(R.mipmap.default_image)
                    .into(new ImageTarget(fourPicture));
        }
    }

    public interface OnThreeBuyClickListener {
        void onThreeBuyClick(MerchantServiceTypePrice merchantServiceTypePrice);
    }

    private OnThreeBuyClickListener onThreeBuyClickListener;

    public void setOnThreeBuyClickListener(OnThreeBuyClickListener onThreeBuyClickListener) {
        this.onThreeBuyClickListener = onThreeBuyClickListener;
    }

    public interface OnFourBuyClickListener {
        void onFourBuyClick(MerchantServiceTypePrice merchantServiceTypePrice);
    }

    private OnFourBuyClickListener onFourBuyClickListener;

    public void setOnFourBuyClickListener(OnFourBuyClickListener onFourBuyClickListener) {
        this.onFourBuyClickListener = onFourBuyClickListener;
    }

    public interface OnGoDetailClickListener {
        void onGoDetailClick(MerchantServiceTypePrice merchantServiceTypePrice);
    }

    private OnGoDetailClickListener onGoDetailClickListener;

    public void setOnGoDetailClickListener(OnGoDetailClickListener onGoDetailClickListener) {
        this.onGoDetailClickListener = onGoDetailClickListener;
    }

}
