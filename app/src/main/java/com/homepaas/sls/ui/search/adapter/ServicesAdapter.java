package com.homepaas.sls.ui.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.mvp.model.LifeServiceModel;
import com.homepaas.sls.mvp.model.ServiceItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/1/14.
 */
public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onServiceItemClicked(ServiceItemModel model);
    }

    private final static int SERVICES_TYPE_ITEM_VIEW_TYPE = 0;

    private final static int SERVICES_CONTENT_ITEM_VIEW_TYPE = 1;

    private final static int SERVICES_CONTENT2_ITEM_VIEW_TYPE = 2;

    private static final int SERVICES_CONTENT3_ITEM_VIEW_TYPE = 3;

    List<LifeServiceModel> models = new ArrayList<>();

    private LayoutInflater mLayoutInflater;

    private OnItemClickListener listener;

    public ServicesAdapter() {

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    OnItemClickListener viewHolderItemClickListener = new OnItemClickListener() {
        @Override
        public void onServiceItemClicked(ServiceItemModel model) {
            listener.onServiceItemClicked(model);
        }
    };

    public void setServices(List<LifeServiceModel> services) {
        this.models = services;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view;
        if (viewType == SERVICES_TYPE_ITEM_VIEW_TYPE) {
            view = mLayoutInflater.inflate(R.layout.services_type_item, parent, false);
            return new ServiceTypeViewHolder(view);
        } else if (viewType == SERVICES_CONTENT_ITEM_VIEW_TYPE) {
            view = mLayoutInflater.inflate(R.layout.services_content_item, parent, false);
            return new ServiceContentViewHolder(view);
        } else if (viewType == SERVICES_CONTENT2_ITEM_VIEW_TYPE) {
            view = mLayoutInflater.inflate(R.layout.services_content2_item, parent, false);
            return new ServiceContent2ViewHolder(view);
        } else {
            view = mLayoutInflater.inflate(R.layout.services_content3_item, parent, false);
            return new ServiceContent3ViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int pos = position;
        for (LifeServiceModel model : models) {
            if (pos == 0) {
                return SERVICES_TYPE_ITEM_VIEW_TYPE;
            }
            pos--;

            List<ServiceItemModel> itemModels = model.getItems();
            int size = itemModels.size();
            int count = (size + 2) / 3;

            if (pos < count) {
                if (pos == count - 1) {
                    if (size % 3 == 0) {
                        return SERVICES_CONTENT3_ITEM_VIEW_TYPE;
                    } else if (size % 3 == 2) {
                        return SERVICES_CONTENT2_ITEM_VIEW_TYPE;
                    } else {
                        return SERVICES_CONTENT_ITEM_VIEW_TYPE;
                    }
                } else {
                    return SERVICES_CONTENT3_ITEM_VIEW_TYPE;
                }
            }
            pos -= count;
        }
        return SERVICES_TYPE_ITEM_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int pos = position;
        int index = 0;
        for (LifeServiceModel model : models) {
            if (pos == 0) {
                ((ServiceTypeViewHolder) holder).setModel(models.get(index));
                return;
            }
            pos--;

            List<ServiceItemModel> itemModels = model.getItems();
            int size = itemModels.size();
            int count = (size + 2) / 3;

            if (pos < count) {
                if (pos == count - 1) {
                    if (size % 3 == 0) {
                        ((ServiceContent3ViewHolder) holder).setModels(itemModels.get(pos * 3), itemModels.get(pos * 3 + 1), itemModels.get(pos * 3 + 2));
                        ((ServiceContent3ViewHolder) holder).setListener(viewHolderItemClickListener);
                        return;
                    } else if (size % 3 == 2) {
                        ((ServiceContent2ViewHolder) holder).setModels(itemModels.get(pos * 3), itemModels.get(pos * 3 + 1));
                        ((ServiceContent2ViewHolder) holder).setListener(viewHolderItemClickListener);
                        return;
                    } else {
                        ((ServiceContentViewHolder) holder).setModel(itemModels.get(pos * 3));
                        ((ServiceContentViewHolder) holder).setListener(viewHolderItemClickListener);
                        return;
                    }
                } else {
                    ((ServiceContent3ViewHolder) holder).setModels(itemModels.get(pos * 3), itemModels.get(pos * 3 + 1), itemModels.get(pos * 3 + 2));
                    ((ServiceContent3ViewHolder) holder).setListener(viewHolderItemClickListener);
                    return;
                }
            }
            pos -= count;

            index++;
        }
    }

    @Override
    public int getItemCount() {
        int typeCount = models.size();
        int itemCount = 0;
        for (LifeServiceModel model : models) {
            List<ServiceItemModel> itemModels = model.getItems();
            itemCount += (itemModels.size() + 2) / 3;
        }
        return typeCount + itemCount;
    }

    public static class ServiceTypeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.services_type_item_textView)
        TextView mTextView;

//        @Bind(R.id.services_type_logo)
//        ImageView mLogo;

        public ServiceTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModel(LifeServiceModel model) {
            mTextView.setText(model.getName());
//            Glide.with(mLogo.getContext())
//                    .load(data.getLogo())
//                    .placeholder(R.mipmap.service_type_log_default)
//                    .into(mLogo);
        }
    }

    public static class ServiceContentViewHolder extends RecyclerView.ViewHolder {

        private ServiceItemModel model;

        private OnItemClickListener listener;

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Bind(R.id.services_content_item_textView)
        TextView mTextView;


        public ServiceContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModel(ServiceItemModel model) {
            this.model = model;
            mTextView.setText(model.getName());

        }

        @OnClick(R.id.services_content_item_textView)
        void clickItem() {
            listener.onServiceItemClicked(model);
        }
    }

    public static class ServiceContent2ViewHolder extends RecyclerView.ViewHolder {

        private ServiceItemModel model1, model2;

        private OnItemClickListener listener;

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Bind(R.id.services_content2_item_textView1)
        TextView mTextView1;

        @Bind(R.id.services_content2_item_textView2)
        TextView mTextView2;

        public ServiceContent2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModels(ServiceItemModel model1, ServiceItemModel model2) {
            this.model1 = model1;
            this.model2 = model2;
            mTextView1.setText(model1.getName());
            mTextView2.setText(model2.getName());
        }

        @OnClick(R.id.services_content2_item_textView1)
        void clickItem1() {
            listener.onServiceItemClicked(model1);
        }

        @OnClick(R.id.services_content2_item_textView2)
        void clickItem2() {
            listener.onServiceItemClicked(model2);
        }
    }


    public static class ServiceContent3ViewHolder extends RecyclerView.ViewHolder {

        private ServiceItemModel model1, model2, model3;

        private OnItemClickListener listener;

        @Bind(R.id.services_content3_item_textView1)
        TextView mTextView1;

        @Bind(R.id.services_content3_item_textView2)
        TextView mTextView2;

        @Bind(R.id.services_content3_item_textView3)
        TextView mTextView3;

        public ServiceContent3ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModels(ServiceItemModel model1, ServiceItemModel model2, ServiceItemModel model3) {
            this.model1 = model1;
            this.model2 = model2;
            this.model3 = model3;
            mTextView1.setText(model1.getName());
            mTextView2.setText(model2.getName());
            mTextView3.setText(model3.getName());
        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @OnClick(R.id.services_content3_item_textView1)
        void clickItem1() {
            listener.onServiceItemClicked(model1);
        }

        @OnClick(R.id.services_content3_item_textView2)
        void clickItem2() {
            listener.onServiceItemClicked(model2);
        }

        @OnClick(R.id.services_content3_item_textView3)
        void clickItem3() {
            listener.onServiceItemClicked(model3);
        }
    }
}
