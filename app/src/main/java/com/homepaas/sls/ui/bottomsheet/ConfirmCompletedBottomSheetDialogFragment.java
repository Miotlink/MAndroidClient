package com.homepaas.sls.ui.bottomsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.order.ComplaintActivity;
import com.homepaas.sls.util.StaticData;


/**
 * Created by mhy on 2017/8/28.
 * 确认完成弹框 底部弹框
 * <p>
 * 显示的前置条件
 * 前置条件
 * (在订单列表页/详情页)点击“确认完成”按钮
 * or
 * 当有订单的状态为“已完成-待确认”、且用户首次进入订单列表页或该笔订单详情页时，自动弹出
 */

public class ConfirmCompletedBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public static String ENTRANCE = "entrance";


    public String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public static ConfirmCompletedBottomSheetDialogFragment newInstance(String entrance, String serverInfo, String orderID) {
        Bundle args = new Bundle();
        args.putString(StaticData.ORDER_ID, orderID);
        args.putString(ENTRANCE, entrance);
        args.putString(StaticData.SERVICE_INFO, serverInfo);
        ConfirmCompletedBottomSheetDialogFragment fragment = new ConfirmCompletedBottomSheetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setConfirmCompletedListener(onConfirmCompletedListener confirmCompletedListener) {
        this.confirmCompletedListener = confirmCompletedListener;
    }

    private onConfirmCompletedListener confirmCompletedListener;

    //确认完成回调监听
    public interface onConfirmCompletedListener {
        void onConfirmCompleted(String flag, String orderID, String orderCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_confirm_completed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle arguments = getArguments();
        RelativeLayout imgClose;
        TextView tvContent;
        Button btnSumbit;
        TextView tvServerIssue;
        imgClose = (RelativeLayout) view.findViewById(R.id.rl_img_close);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        btnSumbit = (Button) view.findViewById(R.id.btn_sumbit);
        tvServerIssue = (TextView) view.findViewById(R.id.tv_server_issue);

        tvContent.setText(getArguments().getString(StaticData.SERVICE_INFO));
        //服务遇到问题
        tvServerIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认完成”组件隐藏、 进入投诉页
                dismiss();
                getActivity().startActivity(new Intent(getActivity(), ComplaintActivity.class).putExtra(StaticData.ORDER_ID, arguments.getString(StaticData.ORDER_ID)));
            }
        });
        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //调用确认完成接口
                //订单状态变为“已完成-待评价”、确认完成”组件隐藏、“评价”组件出现
                if (confirmCompletedListener != null) {
                    confirmCompletedListener.onConfirmCompleted(arguments.getString(ENTRANCE), arguments.getString(StaticData.ORDER_ID), getOrderCode());
                }
//                EventBus.getDefault().post(new ConfirmCompletedEvent(arguments.getString(ENTRANCE), arguments.getString(StaticData.ORDER_ID), getOrderCode()));
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
