package com.homepaas.sls.ui.personalcenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.SystemCertificationMapper;
import com.homepaas.sls.ui.adapter.AuthenticateIconAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * recyclerView Adapter
 */
public class BusinessCollectionsAdapter extends RecyclerSwipeAdapter<BusinessCollectionsAdapter.BusinessViewHolder> {

    private LayoutInflater mInflater;

    private List<BusinessCollectionEntity> mBusinessInfoModelList;

    private OnCollectedItemClickListener mListener;

    public BusinessCollectionsAdapter(List<BusinessCollectionEntity> dataList) {
        mBusinessInfoModelList = dataList;
    }

    public void setOnCollectedItemClickListener(OnCollectedItemClickListener listener) {
        mListener = listener;
    }

    public void setBusinessInfoModelList(List<BusinessCollectionEntity> businessInfoModelList) {
        this.mBusinessInfoModelList = businessInfoModelList;
        notifyDataSetChanged();
    }

    public List<BusinessCollectionEntity> getmBusinessInfoModelList() {
        return mBusinessInfoModelList;
    }

    @Override
    public BusinessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.shop_collection_item, parent, false);
        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BusinessViewHolder viewHolder, int position) {
        viewHolder.bindData(mBusinessInfoModelList.get(position));
        mItemManger.bindView(viewHolder.itemView, position);
        viewHolder.swipeLayout.close();
        if (mListener != null) {
            viewHolder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(viewHolder.getAdapterPosition(),
                            mBusinessInfoModelList.get(viewHolder.getAdapterPosition()).getId(), false);
                }
            });
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mListener.onItemDelete(adapterPosition,
                            mBusinessInfoModelList.get(adapterPosition).getId(), false);
                    mBusinessInfoModelList.remove(adapterPosition);
//                    notifyItemRemoved(adapterPosition);
//                    viewHolder.swipeLayout.close();
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBusinessInfoModelList == null || mBusinessInfoModelList.size() == 0 ? 0 : mBusinessInfoModelList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }

    public static class BusinessViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.swipe_layout)
        SwipeLayout swipeLayout;
        @Bind(R.id.click)
        View click;

        @Bind(R.id.photo)
        ImageView photo;

        @Bind(R.id.name)
        TextView businessName;

        @Bind(R.id.shop_type)
        TextView mainBusiness;

        @Bind(R.id.like)
        TextView like;

        @Bind(R.id.collection)
        TextView collection;

        @Bind(R.id.score)
        TextView grade;

        @Bind(R.id.delete)
        TextView delete;

        @Bind(R.id.icon_recyclerView)
        RecyclerView icons;

        public BusinessViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BusinessCollectionEntity infoModel) {
            Glide.with(itemView.getContext())
                    .load(infoModel.getPhotos().get(0).getSmallPic())
                    .placeholder(R.mipmap.portrait_default)
                    .into(photo);
            businessName.setText(infoModel.getName());
            if (infoModel.getServices() != null && infoModel.getServices().getDefaultService() != null) {
                mainBusiness.setText(infoModel.getServices().getDefaultService().getName());
            }
            like.setText(infoModel.getPraiseCount());
            like.setCompoundDrawablesWithIntrinsicBounds("1".equals(infoModel.getIsPraise()) ? R.mipmap.my_collection_item_like_checked :
                    R.mipmap.my_collection_item_like_unchecked, 0, 0, 0);
            collection.setText(infoModel.getFavoriteCount());
            grade.setText(infoModel.getGradeNumber());
            AuthenticateIconAdapter adapter = (AuthenticateIconAdapter) icons.getAdapter();
            SystemCertificationMapper systemCertificationMapper = new SystemCertificationMapper();
            if (adapter == null) {
                adapter = new AuthenticateIconAdapter(systemCertificationMapper.transform(infoModel));
                icons.setAdapter(adapter);
            } else {
                adapter.setSystemCertificationList(systemCertificationMapper.transform(infoModel));
            }
        }

    }
}
