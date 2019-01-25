package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.SelectServiceOrWorkerEntity;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mhy on 2017/8/26.
 */

public class NearbyServiceWorkerAdapter extends BaseRecyclerAdapter<SelectServiceOrWorkerEntity.ChooseWorkerOrMerchantInfosBean> {

    public NearbyServiceWorkerAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.nearby_service_worker;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final SelectServiceOrWorkerEntity.ChooseWorkerOrMerchantInfosBean item) {
        final CircleImageView imageView = (CircleImageView) holder.getImageView(R.id.img_cion);
        String photo = item.getPhoto();
        String name = item.getName();
        String age = item.getAge();
        String distance = item.getDistance();
        String grade = item.getGrade();



        View view = holder.getView(R.id.layout_head_view);
        view.setVisibility(holder.getAdapterPosition() == 0 ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(photo)) {
//            Glide.with(mContext)
//                    .load(photo)
//                    .placeholder(R.mipmap.service_or_worker_icon_default)
//                    .error(R.mipmap.service_or_worker_icon_default)
//                    .into(imageView);

            Glide.with(mContext).load(photo)
                    .asBitmap()
                    .fitCenter()
                    .placeholder(R.mipmap.service_or_worker_icon_default)
                    .error(R.mipmap.service_or_worker_icon_default)
                    .into(new SimpleTarget<Bitmap>() {

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            imageView.setImageBitmap(resource);
                        }
                    });
        }
        if (!TextUtils.isEmpty(name))
            holder.setText(R.id.tv_server_name, name);

        if (!TextUtils.isEmpty(grade))
            holder.setText(R.id.tv_minute, grade);

        if (!TextUtils.isEmpty(age)) {
            holder.setText(R.id.tv_age, age);
            holder.setVisibility(R.id.tv_distance, View.VISIBLE);
        } else {
            holder.setVisibility(R.id.tv_distance, View.INVISIBLE);
            if (!TextUtils.isEmpty(distance))
                holder.setText(R.id.tv_age, distance);
        }
        if (!TextUtils.isEmpty(distance))
            holder.setText(R.id.tv_distance, distance);
        Button button = (Button) holder.getView(R.id.btn_subscribe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectClickListener != null)
                    onSelectClickListener.onSelectClick(holder.getLayoutPosition());
            }
        });
    }

    private OnSelectClickListener onSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        this.onSelectClickListener = onSelectClickListener;
    }

    public interface OnSelectClickListener {

        void onSelectClick(int position);
    }

}