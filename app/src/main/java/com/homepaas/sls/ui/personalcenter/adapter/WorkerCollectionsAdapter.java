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
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.SystemCertificationMapper;
import com.homepaas.sls.ui.adapter.AuthenticateIconAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * recyclerView Adapter
 */
public class WorkerCollectionsAdapter extends RecyclerSwipeAdapter<WorkerCollectionsAdapter.WorkerViewHolder> {

    private LayoutInflater mInflater;

    private List<WorkerCollectionEntity> mWorkerInfoModelList;

    private OnCollectedItemClickListener mListener;

    public WorkerCollectionsAdapter(List<WorkerCollectionEntity> dataList) {
        mWorkerInfoModelList = dataList;
    }

    public void setOnCollectedItemClickListener(OnCollectedItemClickListener listener) {
        mListener = listener;
    }

    public void setWorkerInfoModelList(List<WorkerCollectionEntity> workerInfoModelList) {
        this.mWorkerInfoModelList = workerInfoModelList;
        notifyDataSetChanged();
    }

    public List<WorkerCollectionEntity> getmWorkerInfoModelList() {
        return mWorkerInfoModelList;
    }

    @Override
    public WorkerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.worker_collection_item, parent, false);

        return new WorkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WorkerViewHolder viewHolder, int position) {
        viewHolder.bindData(mWorkerInfoModelList.get(position));
        mItemManger.bindView(viewHolder.itemView, position);
        viewHolder.swipeLayout.close();

        if (mListener != null) {
            viewHolder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(viewHolder.getAdapterPosition(),
                            mWorkerInfoModelList.get(viewHolder.getAdapterPosition()).getId(), true);
                }
            });

//            viewHolder.like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    mListener.onLikeChecked(viewHolder.getAdapterPosition(),
//                            isChecked, mWorkerInfoModelList.get(viewHolder.getAdapterPosition()));
//                }
//            });
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mListener.onItemDelete(adapterPosition,
                            mWorkerInfoModelList.get(adapterPosition).getId(), true);
                    mWorkerInfoModelList.remove(adapterPosition);
//                    notifyItemRemoved(adapterPosition);
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mWorkerInfoModelList == null || mWorkerInfoModelList.size() == 0 ? 0 : mWorkerInfoModelList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }

    public static class WorkerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.click)
        View click;

        @Bind(R.id.photo)
        ImageView photo;

        @Bind(R.id.name)
        TextView workerName;

        @Bind(R.id.like)
        TextView like;

        @Bind(R.id.collection)
        TextView collection;

        @Bind(R.id.score)
        TextView grade;

        @Bind(R.id.delete)
        TextView delete;
        @Bind(R.id.swipe_layout)
        SwipeLayout swipeLayout;
        @Bind(R.id.worker_type)
        TextView item1;

        @Bind(R.id.worker_hometown)
        TextView item2;

        @Bind(R.id.worker_years)
        TextView item3;

        @Bind(R.id.icon_recyclerView)
        RecyclerView icons;

        public WorkerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(WorkerCollectionEntity infoModel) {
            Glide.with(itemView.getContext())
                    .load(infoModel.getPhoto().get(0).getSmallPic())
                    .placeholder(R.mipmap.portrait_default)
                    .into(photo);
            workerName.setText(infoModel.getName());
            like.setText(infoModel.getPraiseCount());
            like.setCompoundDrawablesWithIntrinsicBounds("1".equals(infoModel.getIsPraise()) ? R.mipmap.my_collection_item_like_checked :
                    R.mipmap.my_collection_item_like_unchecked, 0, 0, 0);
            collection.setText(infoModel.getFavoriteCount());
            grade.setText(infoModel.getGradeNumber());
            item1.setText(infoModel.getServices().getDefaultService().getName());
            item2.setText(infoModel.getNativePlace());
            item3.setText(infoModel.getWorkingYears());
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
