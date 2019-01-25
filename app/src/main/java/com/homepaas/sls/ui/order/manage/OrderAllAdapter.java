package com.homepaas.sls.ui.order.manage;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
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
import com.homepaas.sls.domain.entity.Order;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.Refreshable;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CMJ on 2016/6/22.
 */

public class OrderAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Refreshable<Order> {
    private static final String TAG = "OrderAllAdapter";
    public static final int PLACED = 0;//取消，支付-------下单未支付
    public static final int TAKEN = 1;//支付-------------接单未支付
    public static final int CONFIRM = 2;//确认完成-------完成状态需要确认完成
    public static final int EVALUATE = 3;//评价----------确认完成需要评价
    public static final int CANCEL = 4;//已经取消的订单-------未支付也未接单的订单被成功取消
    private final LinearLayout.LayoutParams noRightMarginParams;

    List<Order> datas;
    LayoutInflater inflater;
    OrderManageActivity context;
    View.OnClickListener cancelListener;
    View.OnClickListener confirmListener;
    private CommonDialog commonDialogPlace;
    private CommonDialog commonDialogConfirm;
    private CommonDialog commonDialogEvaluate;
    private CommonDialog commonDialogCancel;
    private CommonDialog commonDialogDelete;

    public OrderAllAdapter(OrderManageActivity context) {
        this.context = context;
        this.datas = new ArrayList<>(0);
        inflater = LayoutInflater.from(context);
        cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        confirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        noRightMarginParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        noRightMarginParams.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,0,context.getResources().getDisplayMetrics());
    }

    public void deleteAtPosition(int position) {
        notifyItemRemoved(position);
    }

    public void notifyPosition(int position){
        notifyItemChanged(position);
    }


    @Override
    public int getItemViewType(int position) {
        Order order = datas.get(position);
        String status = order.getOrderStatus();
//        boolean isPayOff = TextUtils.equals(order.getIsPayOff(), String.valueOf(Constant.PAYOFF));

        if (TextUtils.equals(status, String.valueOf(Constant.COMPLETE)))
            return CONFIRM;
        if (TextUtils.equals(status, String.valueOf(Constant.CONFIRM)))
            return EVALUATE;
        if (TextUtils.equals(status, String.valueOf(Constant.PLACE)))
            return PLACED;
        if (TextUtils.equals(status, String.valueOf(Constant.TAKEN)))
            return TAKEN;
        if (TextUtils.equals(status, String.valueOf(Constant.CANCEL)))
            return CANCEL;

        return PLACED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case PLACED:
                return new PlaceHolder(inflater.inflate(R.layout.item_myorder_status_placed, parent, false));
            case TAKEN:
                return new TakenHolder(inflater.inflate(R.layout.item_myorder_status_taken, parent, false));
            case CONFIRM:
                return new ConfirmHolder(inflater.inflate(R.layout.item_myorder_status_confirm, parent, false));
            case EVALUATE:
                return new EvaluateHolder(inflater.inflate(R.layout.item_myorder_status_evaluate, parent, false));
            case CANCEL:
                return new CancelHolder(inflater.inflate(R.layout.item_myorder_status_cancel, parent, false));
        }

        return new EvaluateHolder(inflater.inflate(R.layout.item_myorder_status_evaluate, parent, false));

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int position) {

        Log.i(TAG, "onBindViewHolder: "+datas.size());
        if ((holder.getAdapterPosition()<=0&&datas.isEmpty())||holder.getAdapterPosition()>=datas.size()){
            notifyDataSetChanged();
            return;
        }
//        int itemViewType = getItemViewType(position);
        final Order order = datas.get(holder.getAdapterPosition());
        final String orderId = datas.get(holder.getAdapterPosition()).getOrderId();
        final String serviceProviderName = datas.get(holder.getAdapterPosition()).getServiceProviderName();
        final String orderStatus = datas.get(holder.getAdapterPosition()).getOrderStatus();
        final String totalMoney = datas.get(holder.getAdapterPosition()).getPrice();
        final String serviceType = datas.get(holder.getAdapterPosition()).getServiceProvideType();
        double price = Double.valueOf(datas.get(holder.getAdapterPosition()).getPrice());
        boolean isPayOff = TextUtils.equals(String.valueOf(Constant.PAYOFF), order.getIsPayOff());

        if (holder instanceof PlaceHolder) {
            final PlaceHolder placeHolder = (PlaceHolder) holder;
            Log.i(TAG, "onBindViewHolder: " + holder.getAdapterPosition() + "---" + isPayOff + "--Place");
            Glide.with(context).load(order.getServiceProvidePic()).into(placeHolder.avatar);
            placeHolder.orderStatus.setText(getStatusText(orderStatus));
//            placeHolder.serviceType.setText(order.getService().getServiceName());
            placeHolder.orderContent.setText(order.getService().getContent());
            placeHolder.orderIcon.setText(order.getServiceProviderName());
            placeHolder.time.setText(TimeUtils.formatDate(order.getCreateTime()));
            if (price == 0) {
                placeHolder.orderMoney.setText("待议价");
                placeHolder.buttonConfirm.setVisibility(View.GONE);
            } else {
                placeHolder.orderMoney.setText(price + "元");
                placeHolder.buttonConfirm.setVisibility(View.VISIBLE);
            }
            placeHolder.buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialogCancel = new CommonDialog.Builder().setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            commonDialogCancel.dismiss();
                        }
                    }).setConfirmButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActionListener.cancelOrder(datas.get(holder.getAdapterPosition()).getOrderId(), holder.getAdapterPosition());
                        }
                    }).showTitle(false).setContent("是否确认取消订单？").create();
                    commonDialogCancel.show(context.getSupportFragmentManager(), null);
                }
            });

            placeHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderActionListener.viewDetail(datas.get(holder.getAdapterPosition()).getOrderId(), orderStatus, serviceType);
                }
            });
            if (!isPayOff && price > 0) {
                placeHolder.buttonConfirm.setOnClickListener(new View.OnClickListener() {//点击跳转至支付页面
                    @Override
                    public void onClick(View v) {
                        orderActionListener.payOrder(datas.get(holder.getAdapterPosition()).getOrderId(), totalMoney, serviceProviderName);
                    }
                });
                placeHolder.buttonConfirm.setVisibility(View.VISIBLE);
            } else {
                placeHolder.buttonConfirm.setVisibility(View.GONE);
                placeHolder.buttonCancel.setLayoutParams(noRightMarginParams);
            }
            return;

        }
        //已接单
        if (holder instanceof TakenHolder) {
            final TakenHolder takenHolder = (TakenHolder) holder;
            Log.i(TAG, "onBindViewHolder: " + holder.getAdapterPosition() + "---" + isPayOff + "--Taken");
            Glide.with(context).load(order.getServiceProvidePic()).into(takenHolder.avatar);
            takenHolder.orderContent.setText(order.getService().getContent());
            takenHolder.orderStatus.setText(getStatusText(orderStatus));
            takenHolder.orderIcon.setText(order.getServiceProviderName());
            takenHolder.orderMoney.setText(price + "元");
            takenHolder.serviceType.setText(order.getService().getServiceName());
            takenHolder.time.setText(TimeUtils.formatDate(order.getCreateTime()));
            if (price == 0) {
                takenHolder.orderMoney.setText("待议价");
                takenHolder.buttonPay.setVisibility(View.GONE);
                takenHolder.buttonCancel.setLayoutParams(noRightMarginParams);
            }
            if (isPayOff) {
                takenHolder.buttonPay.setVisibility(View.GONE);
            }
            takenHolder.buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialogCancel = new CommonDialog.Builder().setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            commonDialogCancel.dismiss();
                        }
                    }).setConfirmButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActionListener.cancelOrder(datas.get(takenHolder.getAdapterPosition()).getOrderId(), takenHolder.getAdapterPosition());
                        }
                    }).showTitle(false).setContent("是否确认取消订单？").create();
                    commonDialogCancel.show(context.getSupportFragmentManager(), null);
                }
            });
            takenHolder.buttonPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderActionListener.payOrder(orderId, totalMoney, serviceProviderName);
                }
            });
            takenHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderActionListener.viewDetail(orderId, orderStatus, serviceType);
                }
            });
            return;
        }
        //服务完成，待确认
        if (holder instanceof ConfirmHolder) {
            final ConfirmHolder confirmHolder = (ConfirmHolder) holder;
            Log.i(TAG, "onBindViewHolder: " + holder.getAdapterPosition() + "---" + isPayOff + "--Confirm");
            Glide.with(context).load(order.getServiceProvidePic()).into(confirmHolder.avatar);
            confirmHolder.orderContent.setText(order.getService().getContent());
            confirmHolder.orderMoney.setText(order.getPrice() + "元");
            confirmHolder.orderIcon.setText(order.getServiceProviderName());
            confirmHolder.serviceType.setText(order.getService().getServiceName());
            confirmHolder.time.setText(TimeUtils.formatDate(order.getCreateTime()));
            if (price == 0)
            {
                confirmHolder.orderMoney.setText("待议价");
                confirmHolder.buttonPay.setVisibility(View.GONE);
            }else{
                confirmHolder.buttonPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       orderActionListener.payOrder(datas.get(holder.getAdapterPosition()).getOrderId(), totalMoney, serviceProviderName);
                    }
                });
            }
            confirmHolder.buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialogConfirm = new CommonDialog.Builder()
                            .setContent("确认订单已服务完成？")
                            .setConfirmButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    orderActionListener.confirmOrder(datas.get(confirmHolder.getAdapterPosition()).getOrderId(), holder.getAdapterPosition());
                                }
                            }).showTitle(false)
                            .setCancelButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    commonDialogConfirm.dismiss();
                                }
                            })
                            .create();
                    commonDialogConfirm.show(context.getSupportFragmentManager(), null);
                }
            });
            confirmHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderActionListener.viewDetail(orderId, orderStatus, serviceType);
                }
            });
            return;
        }
        //服务完成，待评价
        if (holder instanceof EvaluateHolder) {
            final EvaluateHolder evaluateHolder = (EvaluateHolder) holder;
            Log.i(TAG, "onBindViewHolder: " + holder.getAdapterPosition() + "---" + isPayOff + "--Evaluate");
            Glide.with(context).load(order.getServiceProvidePic()).into(evaluateHolder.avatar);
            evaluateHolder.orderContent.setText(order.getService().getContent());
            evaluateHolder.orderIcon.setText(order.getServiceProviderName());
            evaluateHolder.serviceType.setText(order.getService().getServiceName());
            evaluateHolder.time.setText(TimeUtils.formatDate(order.getCreateTime()));
            if (price == 0) {
                evaluateHolder.orderMoney.setText("待议价");
            } else {
                evaluateHolder.orderMoney.setText(price + "元");
            }
            //是否已经评论过
            if (TextUtils.equals(Constant.EVALUATED,order.getIsEvaluated())){
                evaluateHolder.buttonConfirm.setVisibility(View.GONE);
                evaluateHolder.buttonDelete.setLayoutParams(noRightMarginParams);
            }else{
                evaluateHolder.buttonConfirm.setVisibility(View.VISIBLE);
            }
            evaluateHolder.buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderActionListener.evaluateOrder(orderId);
                }
            });
            evaluateHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialogDelete = new CommonDialog.Builder()
                            .setConfirmButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    orderActionListener.deleteOrder(evaluateHolder.getAdapterPosition(), datas.get(evaluateHolder.getAdapterPosition()).getOrderId());
                                    commonDialogDelete.dismiss();
                                }
                            }).setContent("确认删除订单？").setConfirmTextColor(R.color.appPrimary).showTitle(false)
                            .setCancelButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    commonDialogDelete.dismiss();
                                }
                            }).setCancelTextColor(R.color.appTextPrimary).create();
                    commonDialogDelete.show(context.getSupportFragmentManager(), null);
                }
            });
            evaluateHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderActionListener.viewDetail(orderId, orderStatus, serviceType);
                }
            });
            return;
        }

        if (holder instanceof CancelHolder) {
            final CancelHolder cancelHolder = (CancelHolder) holder;

            Glide.with(context).load(order.getServiceProvidePic()).into(cancelHolder.avatar);
            cancelHolder.orderContent.setText(order.getService().getContent());
            cancelHolder.orderIcon.setText(order.getServiceProviderName());
            cancelHolder.orderMoney.setText(price + "元");
            cancelHolder.serviceType.setText(order.getService().getServiceName());
            cancelHolder.time.setText(TimeUtils.formatDate(order.getCreateTime()));
            if (price == 0) {
                cancelHolder.orderMoney.setText("待议价");
            }
            cancelHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commonDialogDelete == null)
                        commonDialogDelete = new CommonDialog.Builder()
                                .setConfirmButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        orderActionListener.deleteOrder(holder.getAdapterPosition(), datas.get(holder.getAdapterPosition()).getOrderId());
                                    }
                                }).setConfirmTextColor(R.color.appPrimary).showTitle(false)
                                .setCancelButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        commonDialogDelete.dismiss();
                                    }
                                }).setContent("是否确认删除订单？").setCancelTextColor(R.color.appTextPrimary).create();
                    commonDialogDelete.show(context.getSupportFragmentManager(), null);

                }
            });

            cancelHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderActionListener.viewDetail(orderId, orderStatus, serviceType);
                }
            });
        }
    }

    private void bindData(final int position, RecyclerView.ViewHolder holder) {
    }

    private String getStatusText(String orderStatus) {

        switch (Integer.valueOf(orderStatus)) {
            case Constant.PLACE:
                return "已下单";
            case Constant.TAKEN:
                return "已接单";
            case Constant.COMPLETE:
                return "服务完成";
            case Constant.CONFIRM:
                return "待评价";
            case Constant.CANCEL:
                return "已取消";
        }
        return "";
    }


    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    public void setData(List<Order> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    @Override
    public void refresh(List<Order> list) {
        setData(list);
        notifyDataSetChanged();
    }

    public void addData(List<Order> list) {
        datas.addAll(list);
        notifyDataSetChanged();
    }

    static class PlaceHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.order_icon)
        TextView orderIcon;
        @Bind(R.id.order_status)
        TextView orderStatus;
        @Bind(R.id.order_content)
        TextView orderContent;
        @Bind(R.id.order_money)
        TextView orderMoney;
        @Bind(R.id.content_layout)
        RelativeLayout contentLayout;
        @Bind(R.id.service_type)
        TextView serviceType;
        @Bind(R.id.button_cancel)
        TextView buttonCancel;
        @Bind(R.id.button_confirm)
        TextView buttonConfirm;

        public PlaceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TakenHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.order_icon)
        TextView orderIcon;
        @Bind(R.id.order_status)
        TextView orderStatus;
        @Bind(R.id.order_content)
        TextView orderContent;
        @Bind(R.id.order_money)
        TextView orderMoney;
        @Bind(R.id.content_layout)
        RelativeLayout contentLayout;
        @Bind(R.id.service_type)
        TextView serviceType;
        @Bind(R.id.button_cancel)
        TextView buttonCancel;
        @Bind(R.id.button_pay)
        TextView buttonPay;

        public TakenHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ConfirmHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.order_icon)
        TextView orderIcon;
        @Bind(R.id.order_status)
        TextView orderStatus;
        @Bind(R.id.order_content)
        TextView orderContent;
        @Bind(R.id.order_money)
        TextView orderMoney;
        @Bind(R.id.content_layout)
        RelativeLayout contentLayout;
        @Bind(R.id.service_type)
        TextView serviceType;
        @Bind(R.id.button_pay)
        TextView buttonPay;
        @Bind(R.id.button_confirm)
        TextView buttonConfirm;

        public ConfirmHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class EvaluateHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.order_icon)
        TextView orderIcon;
        @Bind(R.id.order_status)
        TextView orderStatus;
        @Bind(R.id.order_content)
        TextView orderContent;
        @Bind(R.id.order_money)
        TextView orderMoney;
        @Bind(R.id.content_layout)
        RelativeLayout contentLayout;
        @Bind(R.id.service_type)
        TextView serviceType;
        @Bind(R.id.button_delete)
        TextView buttonDelete;
        @Bind(R.id.button_confirm)
        TextView buttonConfirm;

        public EvaluateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CancelHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.order_icon)
        TextView orderIcon;
        @Bind(R.id.order_status)
        TextView orderStatus;
        @Bind(R.id.order_content)
        TextView orderContent;
        @Bind(R.id.order_money)
        TextView orderMoney;
        @Bind(R.id.content_layout)
        RelativeLayout contentLayout;
        @Bind(R.id.service_type)
        TextView serviceType;
        @Bind(R.id.button_delete)
        TextView buttonDelete;

        public CancelHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OrderActionListener orderActionListener;

    public void setOrderActionListener(OrderActionListener orderActionListener) {
        this.orderActionListener = orderActionListener;
    }

    public interface OrderActionListener {

        void deleteOrder(int position, String orderId);

        void cancelOrder(String orderId, int position);

        void payOrder(String orderId, String totalMoney, String serviceProviderName);

        void confirmOrder(String orderId, int position);

        void viewDetail(String orderId, String status, String serviceType);

        void evaluateOrder(String orderId);
    }

}
