package com.homepaas.sls.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceTypeEx;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class TitleServiceItemAdapter extends RecyclerView.Adapter<TitleServiceItemAdapter.ItemViewHolder>{

    private List<ServiceTypeEx> serviceTypeExList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private static final String MAP_KEY="MAPKEY";

    private static Map<String,Integer> map;
    public TitleServiceItemAdapter() {
        map=new HashMap<String,Integer>();
        map.put(MAP_KEY,0);
    }

    public void setServiceTypeExList(int keyPosition,List<ServiceTypeEx> list) {
        this.serviceTypeExList = list;
        map.put(MAP_KEY,keyPosition);
        this.notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.title_adapter_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final ServiceTypeEx listItem=serviceTypeExList.get(holder.getAdapterPosition());
        holder.serviceText.setText(listItem.getTypeName());
        if(listItem.getChildren()!=null&&!listItem.getChildren().isEmpty()){
            holder.goNext.setVisibility(View.VISIBLE);
        }else {
            holder.goNext.setVisibility(View.GONE);
        }
        if(goNextOnClickListener!=null&&itemOnclickListener!=null) {
            holder.itemListFl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listItem.getChildren()!=null&&!listItem.getChildren().isEmpty()){
                        int value = map.get(MAP_KEY);
                        map.put(MAP_KEY, value + 1);
                        goNextOnClickListener.goNextClick(value + 1, listItem);
                    }else{
                        itemOnclickListener.itemClick(listItem);
                    }
                }
            });
        }



    }




    @Override
    public int getItemCount() {
        return serviceTypeExList == null ? 0 : serviceTypeExList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @Bind(R.id.service_text)
        TextView serviceText;
        @Nullable
        @Bind(R.id.go_next)
        ImageView goNext;
        @Nullable
        @Bind(R.id.item_list_fl)
        LinearLayout itemListFl;
        @Bind(R.id.image)
        ImageView image;


        private ServiceTypeEx serviceTypeEx;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private   GoNextOnClickListener goNextOnClickListener;
    private  ItemOnclickListener itemOnclickListener;

    public interface GoNextOnClickListener{
        void goNextClick(int goNextPosition,ServiceTypeEx itemService);
    }

    public interface  ItemOnclickListener{
        void itemClick(ServiceTypeEx itemService);
    }

    public void setGoNextOnClickListener(GoNextOnClickListener goNextOnClickListener){
        this.goNextOnClickListener=goNextOnClickListener;
    }

    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener){
        this.itemOnclickListener=itemOnclickListener;
    }
}
