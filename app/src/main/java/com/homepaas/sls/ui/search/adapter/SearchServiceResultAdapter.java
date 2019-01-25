package com.homepaas.sls.ui.search.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.NewServiceType;
import com.homepaas.sls.domain.entity.WholeCityBusines;
import com.homepaas.sls.domain.entity.WholeCityWorker;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.ui.widget.KeywordUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2016/12/21.
 */

public class SearchServiceResultAdapter extends RecyclerView.Adapter<SearchServiceResultAdapter.ReviewViewHolder> {


    private LayoutInflater mInflater;
    private List<WorkerBussinesModel> list;
    private Context context;
    private String edittextText;

    public SearchServiceResultAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setEdittextText(String edittextText){
        this.edittextText=edittextText;
    }

    public void setData(List<IService> iServices) {
        list.clear();
        notifyDataSetChanged();
        if (iServices != null) {
            for (IService iService : iServices) {
                WorkerBussinesModel workerBussinesModel = new WorkerBussinesModel();
                if(iService.getServiceType()==0){
                    workerBussinesModel.setType(Constant.SERVICE_TYPE_INT);
                    NewServiceType entity = (NewServiceType) iService;
                    workerBussinesModel.setId(entity.getId());
                    workerBussinesModel.setPhoto("");
                    workerBussinesModel.setPhoneNumber("");
                    workerBussinesModel.setAcceptOrder("");
                    workerBussinesModel.setName(entity.getName());
                    workerBussinesModel.setGender("");
                    workerBussinesModel.setAge("");
                    workerBussinesModel.setDistance("");
                    workerBussinesModel.setNativePlace("");
                    workerBussinesModel.setPraiseCount("");
                    workerBussinesModel.setFavoriteCount("");
                    workerBussinesModel.setGrade(null);
                    workerBussinesModel.setLatitude("");
                    workerBussinesModel.setLongitude("");
                    workerBussinesModel.setDisplayAttribute("");
                    workerBussinesModel.setDefaultService(null);
                    workerBussinesModel.setServices(null);
                    workerBussinesModel.setActionEntity(null);
                    workerBussinesModel.setIsWholeSB("3");
                }else if(iService.getServiceType()==1) {
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
                } else if(iService.getServiceType()==2){
                    workerBussinesModel.setType(Constant.SERVICE_TYPE_BUSINESS_INT);
                    BusinessEntity entity = (BusinessEntity) iService;
                    workerBussinesModel.setId(entity.getId());
                    workerBussinesModel.setPhoto(entity.getPhoto());
                    workerBussinesModel.setPhoneNumber(entity.getPhoneNumber());
                    workerBussinesModel.setAcceptOrder(entity.getAcceptOrder());
                    workerBussinesModel.setName(entity.getName());
                    workerBussinesModel.setGender("");
                    workerBussinesModel.setAge("");
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
                }else if(iService.getServiceType()==3) {
                    workerBussinesModel.setType(Constant.WHOLE_SERVICE_TYPE_WORKER_INT);
                    WholeCityWorker entity = (WholeCityWorker) iService;
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
                }else{
                    workerBussinesModel.setType(Constant.WHOLE_SERVICE_TYPE_BUSINESS_INT);
                    WholeCityBusines entity = (WholeCityBusines) iService;
                    workerBussinesModel.setId(entity.getId());
                    workerBussinesModel.setPhoto(entity.getPhoto());
                    workerBussinesModel.setPhoneNumber(entity.getPhoneNumber());
                    workerBussinesModel.setAcceptOrder(entity.getAcceptOrder());
                    workerBussinesModel.setName(entity.getName());
                    workerBussinesModel.setGender("");
                    workerBussinesModel.setAge("");
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
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.search__service_result, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final WorkerBussinesModel workerBussinesModel = list.get(holder.getAdapterPosition());
        final int type = workerBussinesModel.getType();
        if(workerBussinesModel.isWhole()){
            holder.wholeCityService.setVisibility(View.VISIBLE);
            holder.attributeLin.setVisibility(View.GONE);
        }else{
            holder.wholeCityService.setVisibility(View.GONE);
            holder.attributeLin.setVisibility(View.VISIBLE);
        }
        holder.serviceName.setText(KeywordUtil.matcherSearchTitle(Color.parseColor("#27b8f3"),workerBussinesModel.getName(),edittextText));
        if(type==Constant.SERVICE_TYPE_INT){
            holder.orderButton.setText("一键下单");
            holder.serviceList.setVisibility(View.GONE);
            holder.attributeLin.setVisibility(View.GONE);
        }else if(type == Constant.SERVICE_TYPE_WORKER_INT) {
            holder.orderButton.setText("找TA服务");
            holder.serviceList.setVisibility(View.VISIBLE);
        } else {
            holder.orderButton.setText("找TA服务");
            holder.serviceList.setVisibility(View.VISIBLE);
            holder.attributeLin.setVisibility(View.GONE);
        }


        if(!workerBussinesModel.getGender().isEmpty()) {
            if (workerBussinesModel.getGender().equals("1")) {
                holder.genderImage.setImageResource(R.mipmap.woman);
            } else {
                holder.genderImage.setImageResource(R.mipmap.man);
            }
        }

       if(!workerBussinesModel.getAge().isEmpty()){
           holder.ageText.setText(workerBussinesModel.getAge());
       }
        String services = "";
        if(workerBussinesModel.getServices()!=null){
        if(!workerBussinesModel.getServices().isEmpty()) {
            for (int i = 0; i < workerBussinesModel.getServices().size(); i++) {
                    services += workerBussinesModel.getServices().get(i) + " ";
            }
        }
        }
        holder.serviceList.setText(KeywordUtil.matcherSearchTitle(Color.parseColor("#27b8f3"),services,edittextText));
        if(onButtonClickListener!=null){
            holder.orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        onButtonClickListener.ItemClick(workerBussinesModel,type);
                    }
            });
        }

        if(onGoDetailClickListener!=null){
            holder.goDetailLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       onGoDetailClickListener.goDetailClick(workerBussinesModel,type);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.go_detail_lin)
        LinearLayout goDetailLin;
        @Bind(R.id.service_name)
        TextView serviceName;
        @Bind(R.id.attribute_lin)
        LinearLayout attributeLin;
        @Bind(R.id.gender_image)
        ImageView genderImage;
        @Bind(R.id.age_text)
        TextView ageText;
        @Bind(R.id.whole_city_service)
        ImageView wholeCityService;
        @Bind(R.id.service_list)
        TextView serviceList;
        @Bind(R.id.order_lin)
        LinearLayout orderLin;
        @Bind(R.id.order_button)
        Button orderButton;
        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnButtonClickListener {
        void ItemClick(WorkerBussinesModel workerBussinesModel,int type);
    }

    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }


    public interface OnGoDetailClickListener {
        void goDetailClick(WorkerBussinesModel workerBussinesModel,int type);
    }

    private OnGoDetailClickListener onGoDetailClickListener;

    public void setOnGoDetailClickListener(OnGoDetailClickListener onGoDetailClickListener) {
        this.onGoDetailClickListener = onGoDetailClickListener;
    }

}
