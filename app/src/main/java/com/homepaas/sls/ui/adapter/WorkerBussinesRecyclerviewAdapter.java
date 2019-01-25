package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.ui.widget.glide.ImageTarget;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/28.
 */
public class WorkerBussinesRecyclerviewAdapter extends RecyclerView.Adapter<WorkerBussinesRecyclerviewAdapter.ItemVoewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<WorkerBussinesModel> list;
    private int mHighLight = -1;
    private int lastPosition = -1;
    private boolean isClicked = false;


    public WorkerBussinesRecyclerviewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public boolean isSelected(int position) {
        return mHighLight == position;
    }

    public void highLightItem(int position, boolean isClick) {
        lastPosition = mHighLight;
        mHighLight = position;
        setClicked(isClick);
//        for (int i = 0; i < getItemCount(); i++)
//            notifyItemChanged(i);
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public void setData(List<IService> iServices) {
        list.clear();
        notifyDataSetChanged();
        if (iServices != null) {
            for (IService iService : iServices) {
                WorkerBussinesModel workerBussinesModel = new WorkerBussinesModel();
                if (iService.isWorker()) {
                    workerBussinesModel.setType(Constant.SERVICE_TYPE_WORKER_INT);
                    WorkerEntity entity = (WorkerEntity) iService;
                    workerBussinesModel.setId(entity.getId());
                    workerBussinesModel.setPhoto(entity.getPhoto());
                    workerBussinesModel.setPhoneNumber(entity.getPhoneNumber());
                    workerBussinesModel.setAcceptOrder(entity.getAcceptOrder());
                    workerBussinesModel.setName(entity.getName());
                    workerBussinesModel.setGender(entity.getGender());
                    workerBussinesModel.setAge(entity.getAge());
                    workerBussinesModel.setDistance(entity.getDistance());
                    workerBussinesModel.setNativePlace(entity.getNativePlace());
                    workerBussinesModel.setPraiseCount(entity.getPraiseCount());
                    workerBussinesModel.setFavoriteCount(entity.getFavoriteCount());
                    workerBussinesModel.setGrade(entity.getGrade());
                    workerBussinesModel.setLatitude(entity.getLatitude());
                    workerBussinesModel.setLongitude(entity.getLongitude());
                    workerBussinesModel.setDisplayAttribute(entity.getDisplayAttribute());
                    workerBussinesModel.setDefaultService(entity.getDefaultService());
                    workerBussinesModel.setServices(entity.getServices());
                    workerBussinesModel.setActionEntity(entity.getActionEntity());
                    workerBussinesModel.setIsWholeSB(entity.getIsWholeWorker());
                } else {
                    workerBussinesModel.setType(Constant.SERVICE_TYPE_BUSINESS_INT);
                    BusinessEntity entity = (BusinessEntity) iService;
                    workerBussinesModel.setId(entity.getId());
                    workerBussinesModel.setPhoto(entity.getPhoto());
                    workerBussinesModel.setPhoneNumber(entity.getPhoneNumber());
                    workerBussinesModel.setAcceptOrder(entity.getAcceptOrder());
                    workerBussinesModel.setName(entity.getName());
                    workerBussinesModel.setGender(null);
                    workerBussinesModel.setAge(null);
                    workerBussinesModel.setDistance(entity.getDistance());
                    workerBussinesModel.setNativePlace(entity.getAddress());
                    workerBussinesModel.setPraiseCount(entity.getPraiseCount());
                    workerBussinesModel.setFavoriteCount(entity.getFavoriteCount());
                    workerBussinesModel.setGrade(entity.getGrade());
                    workerBussinesModel.setLatitude(entity.getLatitude());
                    workerBussinesModel.setLongitude(entity.getLongitude());
                    workerBussinesModel.setDisplayAttribute(null);
                    workerBussinesModel.setDefaultService(entity.getDefaultService());
                    workerBussinesModel.setServices(entity.getServices());
                    workerBussinesModel.setIsWholeSB(entity.getIsWholeMerchant());
                }
                list.add(workerBussinesModel);
            }
        }
        notifyDataSetChanged();
        highLightItem(-1, false);

    }

    @Override
    public ItemVoewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemVoewHolder(inflater.inflate(R.layout.worker_bussines_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemVoewHolder holder, int position) {
        WorkerBussinesModel workerBussinesModel = list.get(holder.getAdapterPosition());
        final String url = workerBussinesModel.getPhoto();
        holder.itemPosition.setText("" + (holder.getAdapterPosition() + 1));
        int defaultImage = -1;
        int type = workerBussinesModel.getType();
        if (type == Constant.SERVICE_TYPE_WORKER_INT) {
            defaultImage = R.mipmap.worker_portrait_default;
        } else {
            defaultImage = R.mipmap.business_portrait_default;
        }
        holder.distance.setText(workerBussinesModel.getDistance());
        if (type == Constant.SERVICE_TYPE_WORKER_INT) {
            holder.name.setText(workerBussinesModel.getDefaultService().getName());

        } else {
            holder.name.setText(workerBussinesModel.getName());
        }
        holder.icon.setBorderWidth((float) 0);
        Glide.with(context).load(url).placeholder(defaultImage)
                .into(new ImageTarget(holder.icon));
        holder.itemSelected.setVisibility(View.INVISIBLE);
        holder.name.setVisibility(View.VISIBLE);
        holder.distanceFl.setVisibility(View.VISIBLE);
        if (workerBussinesModel.getActionEntity() != null&&workerBussinesModel.getActionEntity().getSlogan() != null){
                holder.activityIcon.setVisibility(View.VISIBLE);
                    switch (workerBussinesModel.getActionEntity().getSlogan()) {
                        case "满立返":
                            holder.activityIcon.setImageResource(R.mipmap.promotions1);
                            break;
                        case "满立减":
                            holder.activityIcon.setImageResource(R.mipmap.promotions2);
                            break;
                        case "减再返":
                            holder.activityIcon.setImageResource(R.mipmap.promotions3);
                            break;
                    }
        } else {
            holder.activityIcon.setVisibility(View.GONE);
        }
        if (workerBussinesModel.isWhole()){
                holder.distance.setVisibility(View.GONE);
                holder.cityservice.setVisibility(View.VISIBLE);
        } else {
                holder.distance.setVisibility(View.VISIBLE);
                holder.cityservice.setVisibility(View.GONE);
        }

        if (isSelected(holder.getAdapterPosition())) {
            holder.itemSelected.setVisibility(View.VISIBLE);
            if (isClicked()) {
                holder.activityIcon.setVisibility(View.GONE);
                holder.name.setVisibility(View.GONE);
                holder.distanceFl.setVisibility(View.GONE);
            }
            holder.icon.setBorderWidth((float) 10);
            holder.icon.setBorderColor(Color.parseColor("#32BEFF"));
            Glide.with(context).load(url).placeholder(defaultImage)
                    .into(new ImageTarget(holder.icon));
        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    highLightItem(holder.getAdapterPosition(), true);
                    onItemClickListener.ItemClick(holder.itemView, holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
                }
            });
        }


    }


    public interface OnItemClickListener {
        void ItemClick(View itemview, int position, WorkerBussinesModel workerBussinesModel);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemVoewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.distance_fl)
        FrameLayout distanceFl;
        @Bind(R.id.item_selected)
        View itemSelected;
        @Bind(R.id.item_position)
        TextView itemPosition;
        @Bind(R.id.icon)
        RoundedImageView icon;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.distance)
        TextView distance;
        @Bind(R.id.cityservice)
        ImageView cityservice;
        @Bind(R.id.activity_icon)
        ImageView activityIcon;

        public ItemVoewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
