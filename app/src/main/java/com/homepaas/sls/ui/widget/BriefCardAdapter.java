package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.WholeCityBusines;
import com.homepaas.sls.domain.entity.WholeCityWorker;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.ui.order.directOrder.BusinessOrderActivity;
import com.homepaas.sls.ui.order.directOrder.PlaceOrderActivity;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/28.
 */

public class BriefCardAdapter extends LoopPagerAdapter {



    private LayoutInflater inflater;
    private List<IService> services;
    private Context context;

    BriefCardAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        services = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void setData(List<IService> services) {
        this.services = services;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BriefCardViewHolder(inflater.inflate(R.layout.home_page_service_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (viewHolder instanceof BriefCardViewHolder) {
            ((BriefCardViewHolder) viewHolder).setData(services.get(position));
        }
    }

    public interface OnAction {
        void action(IService service, int actionCode);
    }

    private OnAction onAction;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    public class BriefCardViewHolder extends ViewHolder {
        @Bind(R.id.head_portrait)
        ImageView headPortrait;
        @Bind(R.id.m_name)
        TextView mName;
        @Bind(R.id.merchant_name)
        LinearLayout merchantName;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.gender_age)
        TextView genderAge;
        @Bind(R.id.w_worker)
        ImageView wWorker;
        @Bind(R.id.worker_wage)
        TextView workerWage;
        @Bind(R.id.worker_name)
        LinearLayout workerName;
        @Bind(R.id.rating_bar)
        MyRatingBar ratingBar;
        @Bind(R.id.scope)
        TextView scope;
        @Bind(R.id.activity_jian)
        ImageView activityJian;
        @Bind(R.id.activity_fan)
        ImageView activityFan;
        @Bind(R.id.distance)
        TextView distance;
        @Bind(R.id.merchant_address)
        TextView merchantAddress;
        @Bind(R.id.merchant_time)
        TextView merchantTime;
        @Bind(R.id.merchant_address_ll)
        LinearLayout merchantAddressLl;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.worker_address)
        LinearLayout workerAddress;
        @Bind(R.id.service_type)
        TextView serviceType;
        @Bind(R.id.showDetial)
        RelativeLayout showDetial;
        @Bind(R.id.take_order_text)
        TextView takeOrderText;
        @Bind(R.id.take_order)
        FrameLayout takeOrder;
        @Bind(R.id.call_phone_text)
        TextView callPhoneText;
        @Bind(R.id.call_phone)
        FrameLayout callPhone;
        private IService service;

        BriefCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setData(final IService service) {
            this.service = service;
            if (service != null) {
                if (service instanceof WorkerEntity)
                    setWorkerStyle();
                if (service instanceof BusinessEntity)
                    setMerchantStyle();
                if (service instanceof WholeCityWorker)
                   setWholeCityWorkertStyle();
                if (service instanceof WholeCityBusines)
                    setWholeCityBusinesStyle();

                callPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onAction != null) {
                            onAction.action(service, 1);//打电话
                        }
//                        footCardPresenter.call(service);
                    }
                });

                showDetial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onAction != null) {
                            onAction.action(service, 2);//跳转详情
                        }
                    }
//                        if (service instanceof WorkerEntity) {
//                            mNavigator.showWorkerDetail(getContext(), ((WorkerEntity) service).getId());
//                        } else
//                            mNavigator.showBusinessDetail(getContext(), ((BusinessEntity) service).getId());
//                    }
                });
            }
        }

        private String getNum(String a) {
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(a);
//            System.out.println( m.replaceAll("").trim());
            return m.replaceAll("").trim();
        }

        private WorkerEntity workerEntity;

        private void setWorkerStyle() {
            workerEntity = (WorkerEntity) service;
         if(TextUtils.equals("1",workerEntity.getBusinessWorker())){//商户工人
             merchantName.setVisibility(View.VISIBLE);
             workerName.setVisibility(View.GONE);
             mName.setText(workerEntity.getName());
             mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.merchants, 0);
                merchantAddressLl.setVisibility(View.VISIBLE);
                workerAddress.setVisibility(View.GONE);
                wWorker.setVisibility(View.GONE);
                genderAge.setVisibility(View.GONE);
                merchantAddress.setText(workerEntity.getNativePlace());
                merchantTime.setText("开店"+getNum(workerEntity.getDisplayAttribute()) + "年");
            } else {//普通工人
             merchantName.setVisibility(View.GONE);
             workerName.setVisibility(View.VISIBLE);
             if (!TextUtils.isEmpty(workerEntity.getServicePrice()))
                 workerWage.setText(workerEntity.getServicePrice());
                name.setText(workerEntity.getName());
                merchantAddressLl.setVisibility(View.GONE);
                workerAddress.setVisibility(View.VISIBLE);
                wWorker.setVisibility(View.GONE);
                genderAge.setVisibility(View.VISIBLE);
                genderAge.setText(workerEntity.getAge());
                if (TextUtils.equals("0", workerEntity.getGender())) {
                    genderAge.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.man, 0, 0, 0);
                    genderAge.setSelected(false);
                } else {
                    genderAge.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.woman, 0, 0, 0);
                    genderAge.setSelected(true);
                }
                address.setText(workerEntity.getNativePlace());
                time.setText(getNum(workerEntity.getDisplayAttribute()) + "年");
            }
            Glide.with(context).load(workerEntity.photo).placeholder(R.mipmap.avatar).into(new ImageTarget(headPortrait));

//        String[] grade = workerEntity.getGrade().split("评分");

//        String grade = workerEntity.getGrade().split("评价")[1].split("分")[0];
            ratingBar.setmScope(Float.parseFloat(workerEntity.getGrade()));
            scope.setText(workerEntity.getGrade());
            if (TextUtils.equals("1", workerEntity.getIsReduction())) {
                activityJian.setVisibility(View.VISIBLE);
            } else {
                activityJian.setVisibility(View.INVISIBLE);
            }
            if (TextUtils.equals("1", workerEntity.getIsReturn())) {
                activityFan.setVisibility(View.VISIBLE);
            } else {
                activityFan.setVisibility(View.INVISIBLE);
            }
            distance.setText(workerEntity.getDistance());
            StringBuilder stringBuilder = new StringBuilder();
            for (String ss : workerEntity.getServices()) {
                stringBuilder.append(ss);
                if (workerEntity.getServices().indexOf(ss) < (workerEntity.getServices().size() - 1))
                    stringBuilder.append("、");
            }
            serviceType.setText(stringBuilder.toString());
            if (TextUtils.equals("1", workerEntity.getAcceptOrder())) {
                takeOrderText.setSelected(false);
                takeOrder.setClickable(true);
                takeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlaceOrderActivity.start(context, Constant.SERVICE_TYPE_WORKER_INT, workerEntity.getId(), workerEntity.getGender());
                    }
                });
            } else {
                takeOrder.setClickable(false);
                takeOrderText.setSelected(true);
            }
            if (TextUtils.equals("1", workerEntity.getIscall())) {
                callPhone.setClickable(true);
                callPhoneText.setSelected(false);
            } else {
                callPhone.setClickable(false);
                callPhoneText.setSelected(true);
            }
        }

        private BusinessEntity businessEntity;
        private WholeCityWorker wholeCityWorker;
        private WholeCityBusines wholeCityBusines;
        private void setWholeCityWorkertStyle() {
            wholeCityWorker = (WholeCityWorker) service;
            merchantName.setVisibility(View.VISIBLE);
            workerName.setVisibility(View.GONE);
            merchantAddressLl.setVisibility(View.VISIBLE);
            workerAddress.setVisibility(View.GONE);
            merchantAddress.setText(wholeCityWorker.getNativePlace());
            if (!TextUtils.isEmpty(wholeCityWorker.getDisplayAttribute()))
                merchantTime.setText("开店"+getNum(wholeCityWorker.getDisplayAttribute()) + "年");
            Glide.with(context).load(wholeCityWorker.getPhoto()).placeholder(R.mipmap.avatar).into(new ImageTarget(headPortrait));
            mName.setText(wholeCityWorker.getName());
            mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.service, 0);
//        String[] grade = businessEntity.getGrade().split("评分");
//        String grade = businessEntity.getGrade().split("评价")[1].split("分")[0];
            ratingBar.setmScope(Float.parseFloat(wholeCityWorker.getGrade()));
            scope.setText(wholeCityWorker.getGrade());
            if (TextUtils.equals("1", wholeCityWorker.getIsReduction())) {
                activityJian.setVisibility(View.VISIBLE);
            } else {
                activityJian.setVisibility(View.INVISIBLE);
            }
            if (TextUtils.equals("1", wholeCityWorker.getIsReturn())) {
                activityFan.setVisibility(View.VISIBLE);
            } else {
                activityFan.setVisibility(View.INVISIBLE);
            }
            distance.setText(wholeCityWorker.getDistance());
            StringBuilder stringBuilder = new StringBuilder();
            for (String ss : wholeCityWorker.getServices()) {
                stringBuilder.append(ss);
                if (wholeCityWorker.getServices().indexOf(ss) < (wholeCityWorker.getServices().size() - 1))
                    stringBuilder.append("、");
            }
            serviceType.setText(stringBuilder.toString());
            if (TextUtils.equals("1", wholeCityWorker.getAcceptOrder())) {
                takeOrder.setClickable(true);
                takeOrderText.setSelected(false);
                takeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlaceOrderActivity.start(context, Constant.SERVICE_TYPE_WORKER_INT, wholeCityWorker.getId(), wholeCityWorker.getGender());
                    }
                });
            } else {
                takeOrder.setClickable(false);
                takeOrderText.setSelected(true);
            }
            if (TextUtils.equals("1", wholeCityWorker.getIscall())) {
                callPhone.setClickable(true);
                callPhoneText.setSelected(false);
            } else {
                callPhone.setClickable(false);
                callPhoneText.setSelected(true);
            }
        }
        private void setWholeCityBusinesStyle() {
            wholeCityBusines = (WholeCityBusines) service;
            merchantName.setVisibility(View.VISIBLE);
            workerName.setVisibility(View.GONE);
            merchantAddressLl.setVisibility(View.VISIBLE);
            workerAddress.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(wholeCityBusines.getServiceTime()))
                merchantTime.setText("开店"+getNum(wholeCityBusines.getServiceTime())+"年");
            Glide.with(context).load(wholeCityBusines.getPhoto()).placeholder(R.mipmap.avatar).into(new ImageTarget(headPortrait));
            mName.setText(wholeCityBusines.getName());
            mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.service, 0);
//        String[] grade = businessEntity.getGrade().split("评分");
//        String grade = businessEntity.getGrade().split("评价")[1].split("分")[0];
            ratingBar.setmScope(Float.parseFloat(wholeCityBusines.getGrade()));
            scope.setText(wholeCityBusines.getGrade());
            if (TextUtils.equals("1", wholeCityBusines.getIsReduction())) {
                activityJian.setVisibility(View.VISIBLE);
            } else {
                activityJian.setVisibility(View.INVISIBLE);
            }
            if (TextUtils.equals("1", wholeCityBusines.getIsReturn())) {
                activityFan.setVisibility(View.VISIBLE);
            } else {
                activityFan.setVisibility(View.INVISIBLE);
            }
            distance.setText(wholeCityBusines.getDistance());
            merchantAddress.setText(wholeCityBusines.getAddress());
            StringBuilder stringBuilder = new StringBuilder();
            for (String ss : wholeCityBusines.getServices()) {
                stringBuilder.append(ss);
                if (wholeCityBusines.getServices().indexOf(ss) < (wholeCityBusines.getServices().size() - 1))
                    stringBuilder.append("、");
            }
            serviceType.setText(stringBuilder.toString());
//            if (TextUtils.equals("1", wholeCityBusines.getAcceptOrder())) {
                takeOrder.setClickable(true);
                takeOrderText.setSelected(false);
                takeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BusinessOrderActivity.start(context, Constant.SERVICE_TYPE_BUSINESS_INT, wholeCityBusines.getId());
                    }
                });
//            } else {
//                takeOrder.setClickable(false);
//                takeOrderText.setSelected(true);
//            }
            if (TextUtils.equals("1", wholeCityBusines.getIsCall())) {
                callPhone.setClickable(true);
                callPhoneText.setSelected(false);
            } else {
                callPhone.setClickable(false);
                callPhoneText.setSelected(true);
            }
        }

        private void setMerchantStyle() {
            businessEntity = (BusinessEntity) service;
            merchantName.setVisibility(View.VISIBLE);
            workerName.setVisibility(View.GONE);
            merchantAddressLl.setVisibility(View.VISIBLE);
            workerAddress.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(businessEntity.getServiceTime()))
                merchantTime.setText("开店"+getNum(businessEntity.getServiceTime())+"年");
            Glide.with(context).load(businessEntity.getPhoto()).placeholder(R.mipmap.avatar).into(new ImageTarget(headPortrait));
            mName.setText(businessEntity.getName());
            mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.merchants, 0);
//        String[] grade = businessEntity.getGrade().split("评分");
//        String grade = businessEntity.getGrade().split("评价")[1].split("分")[0];
            ratingBar.setmScope(Float.parseFloat(businessEntity.getGrade()));
            scope.setText(businessEntity.getGrade());
            if (TextUtils.equals("1", businessEntity.getIsReduction())) {
                activityJian.setVisibility(View.VISIBLE);
            } else {
                activityJian.setVisibility(View.INVISIBLE);
            }
            if (TextUtils.equals("1", businessEntity.getIsReturn())) {
                activityFan.setVisibility(View.VISIBLE);
            } else {
                activityFan.setVisibility(View.INVISIBLE);
            }
            distance.setText(businessEntity.getDistance());
            merchantAddress.setText(businessEntity.getAddress());
            StringBuilder stringBuilder = new StringBuilder();
            for (String ss : businessEntity.getServices()) {
                stringBuilder.append(ss);
                if (businessEntity.getServices().indexOf(ss) < (businessEntity.getServices().size() - 1))
                    stringBuilder.append("、");
            }
            serviceType.setText(stringBuilder.toString());
//            if (TextUtils.equals("1", businessEntity.getAcceptOrder())) {
                takeOrder.setClickable(true);
                takeOrderText.setSelected(false);
                takeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BusinessOrderActivity.start(context, Constant.SERVICE_TYPE_BUSINESS_INT, businessEntity.getId());
                    }
                });
//            } else {
//                takeOrder.setClickable(false);
//                takeOrderText.setSelected(true);
//            }
            if (TextUtils.equals("1", businessEntity.getIsCall())) {
                callPhone.setClickable(true);
                callPhoneText.setSelected(false);
            } else {
                callPhone.setClickable(false);
                callPhoneText.setSelected(true);
            }
        }
    }
}
