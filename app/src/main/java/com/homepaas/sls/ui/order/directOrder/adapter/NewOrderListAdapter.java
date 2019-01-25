package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.data.repository.Constant;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.Refreshable;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;

/**
 * Created by CJJ on 2016/9/15.
 * 订单列表
 */

public class NewOrderListAdapter extends RecyclerView.Adapter<NewOrderListAdapter.Holder> implements Refreshable<OrderEntity> {

    List<OrderEntity> datas;
    LayoutInflater inflater;
    Context context;
    //分别应用三种阶段的基础颜色
    private static int colorBegin = Color.parseColor("#CCCCCC");//灰色
    private static int colorMiddle = Color.parseColor("#FD5524");//橘黄色
    private static int colorEnd = Color.parseColor("#3FBEF9");//蓝色

    private int colorNormal;
    private int colorPay;
    private int colorComment;
    private CommonDialog cancelDialog;
    private CommonDialog cancelDialog1;
    private CommonDialog notTakenDialog;

    public NewOrderListAdapter() {
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
            context = parent.getContext();
            initColor();
        }
        return new Holder(inflater.inflate(R.layout.item_order_list, parent, false));
    }

    private void initColor() {
        colorNormal = context.getResources().getColor(R.color.appTextNormal);
        colorPay = context.getResources().getColor(R.color.decorateOrange);
        colorComment = context.getResources().getColor(R.color.newAppPrimary);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void refresh(List<OrderEntity> list) {
        if (datas == null)
            datas = new ArrayList<>();
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public void setDatas(List<OrderEntity> datas) {
        this.datas = datas;
    }

    public List<OrderEntity> getDatas() {
        return datas;
    }

    class Holder extends RecyclerView.ViewHolder implements Action {
        @Bind(R.id.service_name)
        TextView serviceName;
        @Bind(R.id.status_description)
        TextView statusDescription;
        @Bind(R.id.providerinfo_box)
        LinearLayout providerInfoBox;
        @Bind(R.id.avatar)
        RoundedImageView avatar;
        @Bind(R.id.provider_name)
        TextView providerName;
        @Bind(R.id.waiting_hint)
        TextView waitingHint;
        @Bind(R.id.appoint_time)
        TextView appointTime;
        @Bind(R.id.textView5)
        TextView textView5;
        @Bind(R.id.service_address)
        TextView serviceAddress;
        @Bind(R.id.service_price)
        TextView price;
        @Bind(R.id.negative_action)
        TextView negativeAction;
        @Bind(R.id.positive_action)
        TextView positiveAction;
        @Bind(R.id.service_sum)
        TextView serviceSum;
        @Bind(R.id.service_sum_text)
        TextView serviceSumText;
        @Bind(R.id.content)
        LinearLayout mainContent;
        @Bind(R.id.icon_type)
        View iconType;
        @Bind(R.id.total_price_layout)
        LinearLayout totalPriceLayout;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void startCancel() {
            int pos = getAdapterPosition();
            String id = datas.get(pos).getOrderId();
            hostAction.startCancel(pos, id);
        }

        @Override
        public void startComment() {
            int pos = getAdapterPosition();
            String id = datas.get(pos).getOrderId();
            hostAction.startComment(pos, id);
        }

        @Override
        public void startDelete() {
            int pos = getAdapterPosition();
            String id = datas.get(pos).getOrderId();
            hostAction.startDelete(pos, id);
        }

        /**
         * 实现Action接口执行所有的Holder需要的操作，避免嵌套过多的Listener 导致低效率的 final
         */
        @Override
        public void bind() {
            int position = getAdapterPosition();
            OrderEntity entity = datas.get(position);
            String status = entity.getOrderStatus();
            boolean payOff = TextUtils.equals("1", entity.getIsPayOff());
            boolean isEvaluated = entity.getIsEvaluated().equals(Constant.EVALUATED) ? true : false;
//            boolean hasPrice = TextUtils.isEmpty(entity.getTotalPrice()) || TextUtils.equals("面议", entity.getTotalPrice());
            boolean hasPrice = !TextUtils.isEmpty(entity.getTotalPrice());
            boolean isRefunding = TextUtils.equals(entity.getRefundStatus(), "1");
            initStyleAndVisibility(status, TextUtils.equals(entity.getIsPayOff(), Constant.PAYOFF), isRefunding, hasPrice);
            statusDescription.setText(getStatusText(hasPrice, status, payOff, isEvaluated));
            serviceName.setText(entity.service.getServiceName());
            setIconType(entity.service.getRootId());
            appointTime.setText(TimeUtils.formatOrderTime(entity.getService().getServiceStartAt()));
            serviceAddress.setText(entity.getService().getAddress());
            setStatusSumText(status, payOff);
//            price.setText(TextUtils.isEmpty(entity.getPrice()) || "面议".equals(entity.getPrice()) ? "面议" : "¥" + entity.getPrice() + "/" + entity.getUnitName() + "     ×" + entity.getTotal());
            price.setText(TextUtils.isEmpty(entity.getPrice()) ? "¥" + cutUnnecessaryPrecision(entity.getStartingPrice()) + "起" : "¥" + cutUnnecessaryPrecision(entity.getPrice()) + "/" + entity.getUnitName() + "     x" + entity.getTotal());
            if (TextUtils.isEmpty(entity.providerName))//一键下单没有确定工人的阶段,隐藏头像姓名，给出提示语
            {
                providerInfoBox.setVisibility(GONE);
                waitingHint.setVisibility(View.VISIBLE);
            } else//确定了工人
            {
                providerInfoBox.setVisibility(View.VISIBLE);
                waitingHint.setVisibility(GONE);
                Glide.with(context).load(entity.getProviderPic()).into(avatar);
                providerName.setText(entity.providerName);
            }
            mainContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hostAction.startViewDetail(datas.get(getAdapterPosition()).getOrderId());
                }
            });
        }

        private void setIconType(String serviceType) {
            switch (serviceType) {
                case "168":
                    iconType.setBackgroundResource(R.color.service_install_color);
                    break;
                case "169":
                    iconType.setBackgroundResource(R.color.service_convenience_color);
                    break;
                case "170":
                    iconType.setBackgroundResource(R.color.service_housekeep_color);
                    break;
                case "171":
                    iconType.setBackgroundResource(R.color.service_repair_color);
                    break;
                case "172":
                    iconType.setBackgroundResource(R.color.service_fix_color);
                    break;
                default:
                    break;
            }
        }

        private void setStatusSumText(String status, boolean payOff) {
            OrderEntity entity = datas.get(getAdapterPosition());
            String totalPrice = entity.getTotalPrice();
            String orderStatus = entity.getOrderStatus();
            boolean hasPrice = !(TextUtils.isEmpty(totalPrice) || TextUtils.equals("面议", totalPrice));
            String refundStatus = entity.getRefundStatus();
            totalPriceLayout.setVisibility(View.VISIBLE);
            serviceSumText.setVisibility(View.VISIBLE);
            serviceSum.setVisibility(View.VISIBLE);
            switch (status) {
                case Constant.ORDER_STATUS_CANCELED://取消的情况
                    //如果有退款
                    if (TextUtils.isEmpty(refundStatus))//没有退款记录
                    {
                        if (hasPrice)//有价格
                        {
                            serviceSumText.setText("订单总价¥");
                            serviceSum.setText(totalPrice);
                        } else {
//                            serviceSumText.setText("面议");
                            totalPriceLayout.setVisibility(GONE);
                        }
                    } else {//有退款记录的情况下应该是已经有总价了和单价（单价=总价/数量）
                        //没有价格，面议
//                        serviceSumText.setText("");
//                        serviceSum.setText("面议");
//                        if (hasPrice)//有价格
//                                {
                        serviceSumText.setText("订单总价¥");
                        serviceSum.setText(totalPrice);
//                        } else {
                        totalPriceLayout.setVisibility(GONE);
//                        }
                    }
                    break;
                default:
                    if (hasPrice) {
                        //FIXME!!!这里暂时改成订单总价,因为实际支付和待支付的逻辑没有考虑优惠
                        serviceSumText.setText("订单总价¥");
                        serviceSum.setText(totalPrice);
                        float money = Float.parseFloat(totalPrice);
                        ActionEntity activity = entity.getActivity();
                        float realMoney = money;
                        //减去活动减免
                        if (activity != null) {
                            float totalMoney = Float.parseFloat(totalPrice);
                            if (activity.isSpecialSatisfied(totalMoney)) {
                                String specialMinusCount = activity.getSpecialRule().get(0).getMinus();
                                float minusMoney = TextUtils.isEmpty(specialMinusCount) ? 0.0f : Float.parseFloat(specialMinusCount);
                                realMoney -= minusMoney;
                            }
                        }
                        if (payOff)//已经支付
                        {
                            //减去优惠券减免金额
                            String discountAmount = entity.getDiscountAmount();//优惠金额
                            realMoney -= TextUtils.isEmpty(discountAmount) ? 0.0f : Float.parseFloat(discountAmount);
                            serviceSumText.setText("实付¥");
                            serviceSum.setText(cutUnnecessaryPrecision(String.valueOf(realMoney)));
                        } else {//待支付，不读取优惠券（优惠券在支付后读取，不排除未支付时优惠券字段存在，支付前忽略该字段即可）
                            serviceSumText.setText("待支付¥");
                            serviceSum.setText(cutUnnecessaryPrecision(String.valueOf(realMoney)));
                        }

                    } else {//面议
//                        totalPriceLayout.setVisibility(View.GONE);
//                        serviceSumText.setVisibility(View.GONE);
//                        serviceSum.setVisibility(View.GONE);
                        //接单前隐藏
                        if (TextUtils.equals(orderStatus, Constant.ORDER_STATUS_TAKEN)) {
                            serviceSumText.setVisibility(View.VISIBLE);
                            serviceSumText.setText("请与工人协商,并确定价格");
                            serviceSum.setVisibility(GONE);
                        } else {
                            serviceSumText.setVisibility(GONE);
                            serviceSum.setVisibility(GONE);
                        }

                    }
            }
        }

        /**
         * 根据状态初始化item 中的textView的颜色相关的样式,以及按钮的显示与隐藏
         *
         * @param status      订单状态
         * @param payOff      是否支付完成
         * @param isRefunding 是否处于退款中
         */
        private void initStyleAndVisibility(String status, final boolean payOff, final boolean isRefunding, final boolean hasPrice) {
            negativeAction.setTextColor(colorNormal);
            positiveAction.setTextColor(colorNormal);
            negativeAction.setVisibility(View.VISIBLE);
            positiveAction.setVisibility(View.VISIBLE);
            switch (status) {

//                case Constant.ORDER_STATUS_TOASSIGN://待指派
//                    negativeAction.setText(R.string.cancle_order_str);
//                    negativeAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            cancelDialog = new CommonDialog.Builder()
//                                    .setContent("取消订单  确定取消订单吗？")
//                                    .setCancelButton("先不取消", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            cancelDialog.dismiss();
//                                        }
//                                    })
//                                    .setConfirmButton("确定", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            hostAction.startCancel(getAdapterPosition(),datas.get(getAdapterPosition()).getOrderId());
//                                        }
//                                    }).showTitle(false).create();
//                            cancelDialog.show(((BaseActivity)context).getSupportFragmentManager(),null);
//                        }
//                    });
////                    positiveAction.setVisibility(View.GONE);
//
//                    //待付款
//                    positiveAction.setTextColor(colorPay);
//                    positiveAction.setText("支付");
//                    positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
//                    positiveAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {//工人未接单，不能支付，弹框提示
//                            notTakenDialog = new CommonDialog.Builder()
//                                    .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
//                                    .setTitle("温馨提示")
//                                    .showAction(false)
//                                    .create();
//                            notTakenDialog.show(((BaseActivity) context).getSupportFragmentManager(), null);
//                        }
//                    });
//                    break;

//                case Constant.ORDER_STATUS_TOASSIGN://待指派
//                case Constant.ORDER_STATUS_TOTAKE://待接单：取消,没支付可以支付
//                    negativeAction.setText(R.string.cancle_order_str);
//                    negativeAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            cancelDialog = new CommonDialog.Builder()
//                                    .setContent("取消订单  确定取消订单吗？")
//                                    .setCancelButton("先不取消", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            cancelDialog.dismiss();
//                                        }
//                                    })
//                                    .setConfirmButton("确定", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            hostAction.startCancel(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
//                                        }
//                                    }).showTitle(false).create();
//                            cancelDialog.show(((BaseActivity) context).getSupportFragmentManager(), null);
//
//                        }
//                    });
//
//                    //待付款
//                    positiveAction.setTextColor(colorPay);
//                    positiveAction.setText("支付");
//                    positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
//                    positiveAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {//工人未接单，不能支付，弹框提示
//                            notTakenDialog = new CommonDialog.Builder()
//                                    .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
//                                    .setTitle("温馨提示")
//                                    .showAction(false)
//                                    .create();
//                            notTakenDialog.show(((BaseActivity) context).getSupportFragmentManager(), null);
//                        }
//                    });
//
////                    //面议
////                    if (negotiable || payOff) {
////                        positiveAction.setVisibility(View.GONE);
////                    }else {
////                        //待付款
////                        positiveAction.setTextColor(colorPay);
////                        positiveAction.setText("支付");
////                        positiveAction.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View v) {//工人未接单，不能支付，弹框提示
////                               notTakenDialog = new CommonDialog.Builder()
////                                       .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
////                                       .setTitle("温馨提示")
////                                       .showAction(false)
////                                       .create();
////                                notTakenDialog.show(((BaseActivity)context).getSupportFragmentManager(),null);
////                            }
////                        });
////                        positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
////                    }
//                    break;
                case Constant.ORDER_STATUS_CANCELED://已取消：删除
                    positiveAction.setVisibility(GONE);
                    negativeAction.setText(R.string.delete_order_str);
                    negativeAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startDelete(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                        }
                    });
                    break;
                case Constant.ORDER_STATUS_TOASSIGN://待指派
                case Constant.ORDER_STATUS_TOTAKE: //待接单：取消,没支付可以支付
                    if (payOff)
                        negativeAction.setVisibility(GONE);
                case Constant.ORDER_STATUS_TAKEN://已接单：服务中
                    negativeAction.setVisibility(GONE);
                    if (payOff) {
                        positiveAction.setVisibility(GONE);
                        negativeAction.setText("取消订单");
                        negativeAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//付款后的取消
                                cancelDialog = new CommonDialog.Builder()
                                        .setContent("您已付款，请联系客服取消订单并退款/客服电话：4008-262-056")
                                        .setCancelButton("先不取消", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                cancelDialog.dismiss();
                                            }
                                        })
                                        .setConfirmButton("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                hostAction.startCancel(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                                            }
                                        }).showTitle(false).create();
                                cancelDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                            }
                        });
                    } else {//没有支付，提示已经接单
                        negativeAction.setText(R.string.cancle_order_str);
                        negativeAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancelDialog = new CommonDialog.Builder()
                                        .setContent("工人已接单，联系客服取消订单,客服电话：4008-262-056")
                                        .setCancelButton("先不取消", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                cancelDialog.dismiss();
                                            }
                                        })
                                        .setConfirmButton("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                hostAction.startCancel(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                                            }
                                        }).showTitle(false).create();
                                cancelDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                            }
                        });
                    }

                    //现在的逻辑接单后不一定有价格,所以需要考虑是否有价格以及是否支付三种情况
                    if (hasPrice) {
                        if (!payOff) {
                            positiveAction.setText(R.string.pay_str);
                            positiveAction.setTextColor(colorPay);
                            positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
                            positiveAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hostAction.startPay(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                                }
                            });
                        } else {
                            positiveAction.setVisibility(GONE);
                        }
                    } else {
                        positiveAction.setVisibility(GONE);
                        positiveAction.setText(R.string.pay_str);
                        positiveAction.setTextColor(colorPay);
                        positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
                        positiveAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                notTakenDialog = new CommonDialog.Builder()
                                        .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
                                        .setTitle("温馨提示")
                                        .showAction(false)
                                        .create();
                                notTakenDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                            }
                        });
                    }
                    break;
                case Constant.ORDER_STATUS_WORKER_COMPLETE://工人已经确认服务完成：待客户确认
                    negativeAction.setVisibility(GONE);
                    positiveAction.setText(R.string.confirm_order_finish);
                    positiveAction.setTextColor(context.getResources().getColor(R.color.newAppPrimary));
                    positiveAction.setBackgroundResource(R.drawable.order_positive_button_bg);
                    positiveAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startConfirm(datas.get(getAdapterPosition()).getOrderId());
                        }
                    });
                    break;
                case Constant.ORDER_STATUS_CONFIRMED://客户已经确认完成，待评价
                    if (TextUtils.equals(datas.get(getAdapterPosition()).getIsEvaluated(), Constant.EVALUATED)) {
                        //已经评价
                        positiveAction.setVisibility(GONE);
                        negativeAction.setText("删除订单");
                        if (isRefunding)
                            negativeAction.setVisibility(GONE);
                        negativeAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hostAction.startDelete(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                            }
                        });
                    } else {
                        negativeAction.setVisibility(GONE);
                        positiveAction.setText("评价");
                        positiveAction.setTextColor(context.getResources().getColor(R.color.newAppPrimary));
                        positiveAction.setBackgroundResource(R.drawable.order_positive_button_bg);
                        positiveAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hostAction.startComment(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                            }
                        });
                    }
                    break;
            }
        }
    }


    private String getStatusText(boolean hasPrice, String status, boolean payOff, boolean isEvaluated) {
        switch (status) {
            case Constant.ORDER_STATUS_TOASSIGN:
                return "等待工人接单";
            case Constant.ORDER_STATUS_CANCELED:
                return "已取消";
            case Constant.ORDER_STATUS_TOTAKE:
                if (hasPrice) {
                    if (!payOff) {
                        return "待付款";
                    } else return "等待工人接单";
                }
                return "等待工人接单";
            case Constant.ORDER_STATUS_TAKEN:
                if (hasPrice) {
                    if (!payOff) {
                        return "待付款";
                    } else {
                        return "待服务";
                    }
                } else return "待改价";
            case Constant.ORDER_STATUS_CONFIRMED:
                if (isEvaluated) {
                    return "已完成";
                } else {
                    return "待评价";
                }
            case Constant.ORDER_STATUS_WORKER_COMPLETE:
                return "待确认";
        }
        return "";
    }

    public interface Action {
        void startCancel();

        void startComment();

        void startDelete();

        void bind();
    }

    /**
     * Activity or Fragment needs to implements this interface
     * to be notified to execute specific action and need to callback to notify
     * this adapter to refresh layout
     */
    public interface HostAction {
        void startCancel(int pos, String id);

        void startComment(int pos, String id);

        void startDelete(int pos, String id);

        void startPay(int pos, String id);

        void startViewDetail(String orderId);

        void startConfirm(String orderId);
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }
}
