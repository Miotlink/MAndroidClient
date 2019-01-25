package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.DepositInfo;
import com.homepaas.sls.domain.entity.ExpressOrderInfo;
import com.homepaas.sls.domain.entity.KdEOrderInfo;
import com.homepaas.sls.domain.entity.OrderBtnInfo;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.ui.widget.MoreLoadable;
import com.homepaas.sls.ui.widget.Refreshable;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by JWC on 2017/7/20.
 */

public class ClientOrderListAdapter extends RecyclerView.Adapter<ClientOrderListAdapter.ClientOrderListView> implements Refreshable<OrderEntity>, MoreLoadable<OrderEntity> {
    private LayoutInflater layoutInflater;
    private List<OrderEntity> orderEntityList;

    @Override
    public ClientOrderListView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_client_order_list, parent, false);
        return new ClientOrderListView(view);
    }

    @Override
    public void onBindViewHolder(ClientOrderListView holder, int position) {
        OrderEntity orderEntity = orderEntityList.get(holder.getAdapterPosition());
        holder.bindData(orderEntity);
    }

    @Override
    public int getItemCount() {
        return orderEntityList == null ? 0 : orderEntityList.size();
    }

    public void setDatas(List<OrderEntity> datas) {
        this.orderEntityList = datas;
    }

    public List<OrderEntity> getDatas() {
        return orderEntityList;
    }

    @Override
    public void refresh(List<OrderEntity> list) {
        if (orderEntityList == null)
            orderEntityList = new ArrayList<>();
        orderEntityList.clear();
        orderEntityList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<OrderEntity> moreData) {
        int pos = orderEntityList.size();
        orderEntityList.addAll(moreData);
        notifyItemRangeInserted(pos, moreData.size());
    }

    public class ClientOrderListView extends RecyclerView.ViewHolder {
        @Bind(R.id.service_name)
        TextView serviceName;
        @Bind(R.id.status_description)
        TextView statusDescription;
        @Bind(R.id.service_time)
        TextView serviceTime;
        @Bind(R.id.tv_server_time_title)
        TextView tvServerTimeTitle;
        @Bind(R.id.service_address_title)
        TextView serviceAddressTitle;
        @Bind(R.id.service_address)
        TextView serviceAddress;
        @Bind(R.id.run_receive_address)
        TextView runReceiveAddress;
        @Bind(R.id.run_receive_address_rel)
        RelativeLayout runReceiveAddressRel;
        @Bind(R.id.claims_title)
        TextView claimsTitle;
        @Bind(R.id.cliams_amount)
        TextView cliamsAmount;
        @Bind(R.id.claims_rel)
        RelativeLayout claimsRel;
        @Bind(R.id.service_price_title)
        TextView servicePriceTitle;
        @Bind(R.id.service_price_amount)
        TextView servicePriceAmount;
        @Bind(R.id.service_price_rel)
        RelativeLayout servicePriceRel;
        @Bind(R.id.total_price_amount)
        TextView totalPriceAmount;
        @Bind(R.id.total_price_rel)
        RelativeLayout totalPriceRel;
        @Bind(R.id.pay_amount_title)
        TextView payAmountTitle;
        @Bind(R.id.pay_amount)
        TextView payAmount;
        @Bind(R.id.pay_amount_rel)
        RelativeLayout payAmountRel;
        @Bind(R.id.ordinary_order_lin)
        LinearLayout ordinaryOrderLin;
        @Bind(R.id.express_odd_Numbers)
        TextView expressOddNumbers;
        @Bind(R.id.express_odd_numbers_rel)
        RelativeLayout expressOddNumbersRel;
        @Bind(R.id.clinet_send_address)
        TextView clinetSendAddress;
        @Bind(R.id.client_receive_address)
        TextView clientReceiveAddress;
        @Bind(R.id.mailing_time)
        TextView mailingTime;
        @Bind(R.id.express_info_lin)
        LinearLayout expressInfoLin;
        @Bind(R.id.go_to_detail_lin)
        LinearLayout goToDetailLin;
        @Bind(R.id.claims_status)
        TextView claimsStatus;
        @Bind(R.id.claims_button)
        TextView claimsButton;
        @Bind(R.id.delete_button)
        TextView deleteButton;
        @Bind(R.id.ok_button)
        TextView okButton;
        @Bind(R.id.button_rel)
        RelativeLayout buttonRel;
        @Bind(R.id.img_service_icon)
        ImageView imgServiceIcon;


        public ClientOrderListView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final OrderEntity orderEntity) {
            Service service = orderEntity.getService();
            final OrderBtnInfo orderBtnInfo = orderEntity.getOrderBtnInfo();
            KdEOrderInfo kdEOrderInfo = orderEntity.getKdEOrderInfo();

            //跑腿实体类
            ExpressOrderInfo expressOrderInfo = orderEntity.getExpressOrderInfo();

            if (service != null) {
                serviceName.setText(service.getServiceName());
            }
            if (orderBtnInfo != null) {
                statusDescription.setText(orderBtnInfo.getTitle());
            }
            //判断是不是快递
            String isKdEOrder = orderEntity.getIsKdEOrder();
            if (TextUtils.equals("1", isKdEOrder)) {
                expressInfoLin.setVisibility(View.VISIBLE);
                ordinaryOrderLin.setVisibility(View.GONE);
                if (service != null) {
                    clinetSendAddress.setText(service.getAddress());
                }
                if (kdEOrderInfo != null) {
                    expressOddNumbers.setText(kdEOrderInfo.getLogisticCode());
                    KdEOrderInfo.ServiceAddressResponse serviceAddressResponse = kdEOrderInfo.getTargetAddress();
                    if (serviceAddressResponse != null) {
                        clientReceiveAddress.setText(serviceAddressResponse.getAddress1() + " " + serviceAddressResponse.getAddress2());
                    }
                    mailingTime.setText(TimeUtils.formatOrderTime(orderEntity.getService().getServiceStartAt()));
                }
                //状态
                statusDescription.setText("");
                //实付和待付
                payAmountRel.setVisibility(GONE);
                //底部按钮
                buttonRel.setVisibility(GONE);
            } else {
                expressInfoLin.setVisibility(View.GONE);
                ordinaryOrderLin.setVisibility(View.VISIBLE);
                if (service != null) {
                    if (!TextUtils.isEmpty(isKdEOrder) && TextUtils.equals(isKdEOrder, "2")) {  //跑腿订单
                        serviceTime.setText(TimeUtils.formatOrderTime(orderEntity.getService().getServiceStartAt()));
                        serviceAddressTitle.setText("寄件地址");
                        tvServerTimeTitle.setText("寄件时间");
                        runReceiveAddressRel.setVisibility(View.VISIBLE);
                        if (expressOrderInfo != null && expressOrderInfo.getTargetAddress() != null) {
                            runReceiveAddress.setText(expressOrderInfo.getTargetAddress().getAddress1() + expressOrderInfo.getTargetAddress().getAddress2());
                        }
                        //订单列表及订单详情页操作按钮，跑腿修改成跟小时工等其他服务一样，
                    } else {
                        serviceTime.setText(TimeUtils.formatOrderTime(service.getServiceStartAt()));
                        serviceAddressTitle.setText("服务地址");
                        tvServerTimeTitle.setText("服务时间");
                        runReceiveAddressRel.setVisibility(View.GONE);
                    }
                    serviceAddress.setText(service.getAddress());
                }
                //服务价格
                String isNegotiable = orderEntity.getNegotiable();
                DepositInfo depositInfo = orderEntity.getDepositInfo();
                servicePriceRel.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(isNegotiable) && TextUtils.equals("0", isNegotiable)) {
//                    servicePriceRel.setVisibility(View.VISIBLE);
//                    servicePriceTitle.setText(R.string.service_price_str);
//                    servicePriceAmount.setText(orderEntity.getPrice() + "/" + orderEntity.getUnitName() + " x" + orderEntity.getTotal());
                } else {
                    if (depositInfo != null && !TextUtils.isEmpty(depositInfo.getDepositAmount())) {
                        servicePriceRel.setVisibility(View.VISIBLE);
                        servicePriceTitle.setText(R.string.deposit);
                        servicePriceAmount.setText("¥" + depositInfo.getDepositAmount());
                    }
                }
                totalPriceRel.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(orderEntity.getTotalPrice())) {
                    totalPriceRel.setVisibility(View.VISIBLE);
                    totalPriceAmount.setText("¥" + orderEntity.getTotalPrice());
                }
                //实付和待付
                payAmountRel.setVisibility(GONE);
                String payStatus = orderEntity.getPayStatus();
                if (TextUtils.equals("0", payStatus)) {
                    payAmountRel.setVisibility(View.VISIBLE);
                    payAmountTitle.setText("实付");
                    payAmount.setText("¥" + orderEntity.getPayAmount());
                } else if (TextUtils.equals("1", payStatus)) {
                    payAmountRel.setVisibility(View.VISIBLE);
                    payAmountTitle.setText("待支付");
                    payAmount.setText("¥" + orderEntity.getPayAmount());
                }
                //底部按钮
                buttonRel.setVisibility(View.VISIBLE);
                showButton(orderEntity);
            }
            //服务图片
            Glide.with(imgServiceIcon.getContext()).load(orderEntity.getService().getServiceProductIcon())
                    .asBitmap()
                    .fitCenter()
                    .placeholder(R.mipmap.default_image)
                    .error(R.mipmap.default_image)
                    .into(imgServiceIcon);

            //去订单详情页
            if (hostAction != null) {
                goToDetailLin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hostAction.startViewDetail(orderEntity.getOrderId(), orderEntity.getIsKdEOrder());
                    }
                });
            }
        }

        /**
         * 显示底部按钮
         */
        private void showButton(final OrderEntity orderEntity) {
            claimsStatus.setVisibility(View.GONE);
            final ClaimsInfo claimsInfo = orderEntity.getClaimsInfo();
            String leftDownDisplayType = orderEntity.getLeftDownDisplayType();
            if (TextUtils.equals("1", leftDownDisplayType) && !TextUtils.isEmpty(orderEntity.getRefundStatus())) {
                claimsStatus.setVisibility(View.VISIBLE);
                claimsStatus.setText(orderEntity.getRefundStatus());
            } else if (TextUtils.equals("2", leftDownDisplayType) && claimsInfo != null && claimsInfo.getClaimsManagementInfo() != null
                    && !TextUtils.isEmpty(claimsInfo.getClaimsManagementInfo().getAuditStatus())) {
                claimsStatus.setVisibility(View.VISIBLE);
                claimsStatus.setText(claimsInfo.getClaimsManagementInfo().getAuditStatus());
            }
            claimsButton.setVisibility(View.GONE);
            //查看赔付
            if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayShowClaimsBtn())) {
                claimsButton.setVisibility(View.VISIBLE);
                claimsButton.setText(R.string.client_view_the_payment);
                if (hostAction != null && claimsInfo != null && claimsInfo.getClaimsManagementInfo() != null) {
                    claimsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hostAction.startSeeCompensation(claimsInfo.getClaimsManagementInfo().getSettlementId());
                        }
                    });
                }
            }
            //查看赔付原因
            if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayBackClaimsReasonBtn())) {
                claimsButton.setVisibility(View.VISIBLE);
                claimsButton.setText(R.string.client_view_the_reason);
                if (hostAction != null && claimsInfo != null && claimsInfo.getClaimsManagementInfo() != null) {
                    claimsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hostAction.startSeeReason(claimsInfo.getClaimsManagementInfo().getBackReason());
                        }
                    });
                }
            }

            //保险赔付
            if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayApplyClaimsBtn())) {
                claimsButton.setVisibility(View.VISIBLE);
                claimsButton.setText(R.string.client_insurance_claims);
                if (hostAction != null && claimsInfo != null && claimsInfo.getClaimsManagementInfo() != null) {
                    claimsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hostAction.startInsuranceCompensation(orderEntity, claimsInfo.getClaimsManagementInfo().getSettlementId());
                        }
                    });
                }
            }
            deleteButton.setVisibility(View.GONE);
            okButton.setVisibility(View.GONE);
            //增加服务数量
            if (orderEntity.getOrderBtnInfo() != null && TextUtils.equals("1", orderEntity.getOrderBtnInfo().getIsDisplayAddServiceBtn())) {
                okButton.setVisibility(View.VISIBLE);
                okButton.setText(R.string.client_add_service);
                if (hostAction != null) {
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hostAction.addService(orderEntity.getService().getServiceId(), orderEntity.getOrderId());
                        }
                    });
                }
            }
            //去支付
            if (orderEntity.getOrderBtnInfo() != null && TextUtils.equals("1", orderEntity.getOrderBtnInfo().getIsDisplayGotoPayBtn())) {
                okButton.setVisibility(View.VISIBLE);
                okButton.setText(R.string.client_to_pay_order);
                if (hostAction != null) {
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hostAction.startPay(getAdapterPosition(), orderEntity.getOrderId());
                        }
                    });
                }
            }
            //确认服务完成
            if (orderEntity.getOrderBtnInfo() != null && TextUtils.equals("1", orderEntity.getOrderBtnInfo().getIsDisplayClientConfirmBtn())) {
                okButton.setVisibility(View.VISIBLE);
                okButton.setText(R.string.client_confirm_order);
                if (hostAction != null) {
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (orderEntity != null && orderEntity.getService() != null) {
                                hostAction.startConfirm(orderEntity.getService(), orderEntity.getOrderId(), orderEntity.getOrderCode());
                            }
                        }
                    });
                }
            }
            //去评价
            if (orderEntity.getOrderBtnInfo() != null && TextUtils.equals("1", orderEntity.getOrderBtnInfo().getIsDisplayGotoEvaluateBtn())) {
                okButton.setVisibility(View.VISIBLE);
                okButton.setText(R.string.client_to_evaluate_order);
                if (hostAction != null) {
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (orderEntity != null && orderEntity.getService() != null) {
                                StringBuffer serviceDetails = new StringBuffer();
                                serviceDetails.append("(");
                                serviceDetails.append(TimeUtils.formatOrderTime(orderEntity.getService().getServiceStartAt()));
                                serviceDetails.append("的");
                                serviceDetails.append(orderEntity.getService().getServiceName());
                                serviceDetails.append("服务");
                                serviceDetails.append(")");
                                hostAction.startComment(orderEntity.getService().getServiceId(), orderEntity.getOrderCode(), serviceDetails.toString());
                            }
                        }
                    });
                }
            }
            if (claimsStatus.getVisibility() == GONE && claimsButton.getVisibility() == GONE && deleteButton.getVisibility() == GONE && okButton.getVisibility() == GONE) {
                buttonRel.setVisibility(GONE);
            } else {
                buttonRel.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface HostAction {
        //支付过的订单去取消时用
        void startRemind();

        //没有支付过的订单取消时用
        void startCancel(int pos, String id);

        //增加服务数量调用
        void addService(String serviceId, String orderCode);

        //去评价的时候用
        void startComment(String serviceId, String orderCode, String serviceDetails);

        //删除订单的时候用
        void startDelete(int pos, String id);

        //去支付的时候用
        void startPay(int pos, String id);

        // 查看订单详情
        void startViewDetail(String orderId, String isKdEOrder);





        //确认服务完成
        void startConfirm(Service service, String orderId, String orderCode);

        //保险赔付
        void startInsuranceCompensation(OrderEntity orderEntity, String id);

        //查看原因
        void startSeeReason(String reason);

        //查看赔付
        void startSeeCompensation(String id);
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }
}
