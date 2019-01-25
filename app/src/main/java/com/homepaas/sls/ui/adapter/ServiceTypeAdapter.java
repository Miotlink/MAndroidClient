package com.homepaas.sls.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.ui.order.chooseservice.SelectServiceItemFragment;
import com.homepaas.sls.ui.widget.NestGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;

/**
 * Date: 2016/9/13.
 *
 * author: fmisser
 * Description:
 */

public class ServiceTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ServiceTypeEx> serviceTypeExList = new ArrayList<>();

    private LayoutInflater mLayoutInflater;
    private static  Map<Integer,Integer> map;
    public ServiceTypeAdapter() {

    }

    public void setServiceTypeExList(List<ServiceTypeEx> list) {
        this.serviceTypeExList.clear();
        //过滤到不符合条件的数据结构
        for (ServiceTypeEx serviceTypeEx : list) {
            if (serviceTypeEx.getChildren() != null &&
                    !serviceTypeEx.getChildren().isEmpty()) {
                this.serviceTypeExList.add(serviceTypeEx);
            }
        }
        map=new HashMap<Integer, Integer>();
        for (int i=0;i<serviceTypeExList.size();i++){
            map.put(i,0);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view;
        if (viewType == 0) {
            view = mLayoutInflater.inflate(R.layout.services_type_item, parent, false);
            ServiceTypeViewHolder holder = new ServiceTypeViewHolder(view);
            holder.setIsRecyclable(false);
            return holder;
        } else {
            view = mLayoutInflater.inflate(R.layout.service_content_gridview, parent, false);
            ServiceSubTypeViewHolder holder = new ServiceSubTypeViewHolder(view);
            holder.setIsRecyclable(false);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int index = position / 2;
        ServiceTypeEx serviceTypeEx = serviceTypeExList.get(index);
        int type = position % 2;
        if (type == 0) {
            ((ServiceTypeViewHolder) holder).setModel(serviceTypeEx,index);
        } else {
            ((ServiceSubTypeViewHolder) holder).setModel(serviceTypeEx, serviceTypeEx.getChildren(),index);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return serviceTypeExList.size() * 2;
    }

    public static class ServiceTypeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.service_fl)
        FrameLayout serviceFl;
        @Bind(R.id.services_type_item_textView)
        TextView mTextView;
        @Bind(R.id.image)
        ImageView mImage;
        @Bind(R.id.image_ll)
        LinearLayout image_ll;

        @BindDrawable(R.mipmap.service_hot)
        Drawable serviceHot;
        @BindDrawable(R.mipmap.service_install)
        Drawable serviceInstall;
        @BindDrawable(R.mipmap.service_housekeep)
        Drawable serviceHousekeep;
        @BindDrawable(R.mipmap.service_fix)
        Drawable serviceFix;
        @BindDrawable(R.mipmap.service_repair)
        Drawable serviceRepair;
        @BindDrawable(R.mipmap.service_convenience)
        Drawable serviceConvenience;


        public ServiceTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModel(ServiceTypeEx serviceTypeEx,  final int groupIndex) {
            switch (serviceTypeEx.getTypeId()) {
                case "168":
                    serviceInstall.setBounds(0, 0, serviceInstall.getMinimumWidth(), serviceInstall.getMinimumHeight());
                    mTextView.setCompoundDrawables(serviceInstall, null, null, null);
                    break;
                case "169":
                    serviceConvenience.setBounds(0, 0, serviceConvenience.getMinimumWidth(), serviceConvenience.getMinimumHeight());
                    mTextView.setCompoundDrawables(serviceConvenience, null, null, null);
                    break;
                case "170":
                    serviceHousekeep.setBounds(0, 0, serviceHousekeep.getMinimumWidth(), serviceHousekeep.getMinimumHeight());
                    mTextView.setCompoundDrawables(serviceHousekeep, null, null, null);
                    break;
                case "171":
                    serviceRepair.setBounds(0, 0, serviceRepair.getMinimumWidth(), serviceRepair.getMinimumHeight());
                    mTextView.setCompoundDrawables(serviceRepair, null, null, null);
                    break;
                case "172":
                    serviceFix.setBounds(0, 0, serviceFix.getMinimumWidth(), serviceFix.getMinimumHeight());
                    mTextView.setCompoundDrawables(serviceFix, null, null, null);
                    break;
                case "0":
                default:
                    serviceHot.setBounds(0, 0, serviceHot.getMinimumWidth(), serviceHot.getMinimumHeight());
                    mTextView.setCompoundDrawables(serviceHot, null, null, null);
                    break;
            }
            mTextView.setText(serviceTypeEx.getTypeName());
            int value=map.get(groupIndex);
            if(value==0){
                mImage.setBackgroundResource(R.mipmap.pack_up);
            }else{
                mImage.setBackgroundResource(R.mipmap.pull_down);
            }

          if(onCancelButtonClickListener!=null){
              serviceFl.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Iterator it = map.keySet().iterator();
                          while(it.hasNext()){
                           int key=(Integer) it.next();
                              if(key==groupIndex){
                                  if(map.get(key)==0){
                                      map.put(groupIndex,1);
                                  }else{
                                      map.put(groupIndex,0);
                                  }
                              }

                          }
                          onCancelButtonClickListener.onCancelButtonClick();
                      }
                  });
          }

        }
    }

    public interface OnCancelButtonClickListener {
        void onCancelButtonClick();
    }

    private static  OnCancelButtonClickListener onCancelButtonClickListener;
    public void setOnCancelButtonClickListener(OnCancelButtonClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
    }


    public static class ServiceSubTypeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.service_content_gridView)
        NestGridView gridView;

        @Bind(R.id.service_background)
        LinearLayout linearLayout;

        //三级目录数据列表(整合后)
        List<ServiceTypeEx> serviceTypeExList = new ArrayList<>();

        ServiceTypeGridViewAdapter adapter;
        private int childIndex;

        public ServiceSubTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            adapter = new ServiceTypeGridViewAdapter();
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ServiceTypeEx serviceTypeEx = serviceTypeExList.get(position);
                    if (serviceTypeEx != null) {
                        if (serviceTypeEx.getChildren() != null &&
                                !serviceTypeEx.getChildren().isEmpty()) {
                            SelectServiceItemFragment.show((FragmentActivity) parent.getContext(), serviceTypeEx);
                        } else {
                            if(serviceTypeEx.getTypeName().equals("更多")){
                                if(onCancelButtonClickListener!=null){
                                    Iterator it = map.keySet().iterator();
                                    while(it.hasNext()){
                                        int key=(Integer) it.next();
                                        if(key==childIndex){
                                            if(map.get(key)==0){
                                                map.put(childIndex,1);
                                            }
                                        }
                                    }
                                    onCancelButtonClickListener.onCancelButtonClick();
                                }
                            }else{
                            EventBus.getDefault().post(serviceTypeEx);
                            }
                        }
                    }
                }
            });
        }

        public void setModel(ServiceTypeEx root, List<ServiceTypeEx> list, int childIndex) {
           this.childIndex=childIndex;
            switch (root.getTypeId()) {
                case "168":
                    linearLayout.setBackgroundResource(R.color.service_install_color);
                    break;
                case "169":
                    linearLayout.setBackgroundResource(R.color.service_convenience_color);
                    break;
                case "170":
                    linearLayout.setBackgroundResource(R.color.service_housekeep_color);
                    break;
                case "171":
                    linearLayout.setBackgroundResource(R.color.service_repair_color);
                    break;
                case "172":
                    linearLayout.setBackgroundResource(R.color.service_fix_color);
                    break;
                case "0":
                default:
                    linearLayout.setBackgroundResource(R.color.service_hot_color);
                    break;
            }

            serviceTypeExList.clear();


            //整合所有三级服务到一个列表下,如果二级列表下没有三级数据,则添加自己作为三级显示
            for (ServiceTypeEx serviceTypeEx : list) {
                if (serviceTypeEx.getChildren() != null &&
                        !serviceTypeEx.getChildren().isEmpty()) {
                    serviceTypeExList.addAll(serviceTypeEx.getChildren());
                } else {
                    serviceTypeExList.add(serviceTypeEx);
                }
            }

            int value=map.get(childIndex);
            if(value==0) {
                //FIXME!!!目前写死最多两行6个服务类型
                if (serviceTypeExList.size() > 6) {
                    serviceTypeExList = serviceTypeExList.subList(0, 5);
                    ServiceTypeEx ste=new ServiceTypeEx();
                    ste.setTypeName("更多");
                    ste.setTypeId("");
                    ste.setChildren(null);
                    serviceTypeExList.add(ste);

                }
            }
            adapter.setServiceTypeExList(serviceTypeExList);
        }
    }
}
