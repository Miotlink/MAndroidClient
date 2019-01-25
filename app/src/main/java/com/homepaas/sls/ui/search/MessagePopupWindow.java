package com.homepaas.sls.ui.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.IServiceInfo.Type;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.SystemCertificationMapper;
import com.homepaas.sls.mvp.presenter.ShortDetailPresenter;
import com.homepaas.sls.mvp.view.ShortDetailView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.adapter.AuthenticateIconAdapter;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * on 2016/1/16 0016
 *
 * @author zhudongjie .
 */
// XXX: 2016/3/1 0001 改用 BottomSheet ?
//2016/9/29首页改版之后不再使用
@Deprecated
@ActivityScope
public class MessagePopupWindow extends PopupWindow implements ShortDetailView {

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(boolean isCancel) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(Throwable e) {
        if (mActivity instanceof CommonBaseActivity) {
            ((CommonBaseActivity) mActivity).showError(e);
        }
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int ResId) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    public interface OnDataChangedListener {

        void onBusinessChanged(BusinessInfoModel businessInfo);

        void onWorkerChanged(WorkerCollectionEntity workerInfo);
    }

    public interface OnDialListener {

        void onDial(String id, @Type int type);
    }


    private Activity mActivity;

    private BusinessViewHolder mBusinessViewHolder;

    private WorkerViewHolder mWorkerViewHolder;


    private ShortDetailPresenter mPresenter;

    @Inject
    Navigator mNavigator;

    private OnDataChangedListener mOnDataChangedListener;

    private OnDialListener mOnDialListener;

    @Inject
    @SuppressLint("InflateParams")
    public MessagePopupWindow(Activity activity, ShortDetailPresenter presenter) {
        super(MATCH_PARENT, WRAP_CONTENT);
        mPresenter = presenter;
        mActivity = activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        mBusinessViewHolder = new BusinessViewHolder(inflater.inflate(R.layout.business_short_detail, null));
        mWorkerViewHolder = new WorkerViewHolder(inflater.inflate(R.layout.worker_short_detail, null));
        setAnimationStyle(R.style.PopupWindowAnimation);
        mPresenter.setDetailView(this);
    }

    protected void throttle(final View view) {
        if (view != null) {
            view.setEnabled(false);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                }
            }, 500);
        }
    }

    @Override
    public void bindWorker(WorkerCollectionEntity worker) {
        mPresenter.setServiceInfo(worker);
        setContentView(mWorkerViewHolder.contentView);
        mWorkerViewHolder.setData(worker);
    }

    @Override
    public void bindBusiness(BusinessInfoModel business) {
        mPresenter.setServiceInfo(business);
        setContentView(mBusinessViewHolder.contentView);
        mBusinessViewHolder.setData(business);
    }

    @Override
    public void refreshWorker(WorkerCollectionEntity infoModel) {
        bindWorker(infoModel);
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onWorkerChanged(infoModel);
        }
    }

    @Override
    public void refreshBusiness(BusinessInfoModel infoModel) {
        bindBusiness(infoModel);
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onBusinessChanged(infoModel);
        }
    }


    @Override
    public void callIfEnable(boolean enable) {
        mBusinessViewHolder.mCallButton.setEnabled(enable);
        mWorkerViewHolder.mCallButton.setEnabled(enable);
        if (!enable) {
            return;
        }
        if (mOnDialListener != null) {
            IServiceInfo serviceInfo = mPresenter.getServiceInfo();
            mOnDialListener.onDial(serviceInfo.getId(), serviceInfo.type());
        }
    }

    @Override
    public void likeAnim() {
        // playAnim(mLikeAnim);
    }

    @Override
    public void collectAnim() {
        // playAnim(mCollectionAnim);
    }

//    private void playAnim(final View view) {
//        view.setVisibility(View.VISIBLE);
//        AnimationSet set = new AnimationSet(true);
//        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -40f);
//        AlphaAnimation aa = new AlphaAnimation(1, 0);
//        set.addAnimation(ta);
//        set.addAnimation(aa);
//        set.setDuration(500);
//        set.setFillAfter(false);
//        set.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                view.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        view.startAnimation(set);
//    }

    @Override
    public void showLogin() {
        LoginDialogFragment.show((FragmentActivity) mActivity, new LoginDialogFragment.OnLoginListener() {
            @Override
            public void onLogin() {

            }
        });
    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        mOnDataChangedListener = onDataChangedListener;
    }


    public void setOnDialListener(OnDialListener onDialListener) {
        mOnDialListener = onDialListener;
    }


//
//    @OnClick(R.id.collection)
//    void onCollectionClick(View view) {
//        boolean checked = ((ManuallyCheckBox) view).isChecked();
//        if (mWorker) {
//            mPresenter.collectWorker(mId, !checked);
//        } else {
//            mPresenter.collectBusiness(mId, !checked);
//        }
//        throttle(view);
//    }
//
//
//    @OnClick(R.id.like)
//    void onLikeClick(View view) {
//        boolean checked = ((ManuallyCheckBox) view).isChecked();
//        if (mWorker) {
//            mPresenter.likeWorker(mId, !checked);
//        } else {
//            mPresenter.likeBusiness(mId, !checked);
//        }
//        throttle(view);
//    }


    class WorkerViewHolder {

        View contentView;

        @Bind(R.id.photo)
        ImageView mPhoto;

        @Bind(R.id.name)
        TextView mName;

        @Bind(R.id.like)
        TextView mLike;

        @Bind(R.id.collection)
        TextView mCollection;

        @Bind(R.id.score)
        TextView mScore;

        @Bind(R.id.message_item1)
        TextView mItem1;

        @Bind(R.id.message_item2)
        TextView mItem2;

        @Bind(R.id.message_item3)
        TextView mItem3;

        @Bind(R.id.recyclerView)
        RecyclerView mRecyclerView;

        @Bind(R.id.direct_pay)
        ImageButton mDirectPay;

        @Bind(R.id.call_button)
        ImageButton mCallButton;

        @Bind(R.id.place_order_button)
        ImageButton mPlaceOrder;

        WorkerViewHolder(View contentView) {
            ButterKnife.bind(this, contentView);
            this.contentView = contentView;
        }

        void setData(WorkerCollectionEntity info) {
            Glide.with(mActivity).load(info.getPhoto())
                    .placeholder(R.mipmap.worker_portrait_default)
                    .into(new ImageTarget(mPhoto));
            mName.setText(info.getName());
            mItem1.setText(info.getSplicedServices());
            mItem2.setText(info.getNativePlace());
            mItem3.setText(info.getWorkingYears());
            mLike.setText(String.valueOf(info.getLikeCount()));
            mCollection.setText(String.valueOf(info.getCollectedCount()));
            mScore.setText(info.getGradeNumber());
            mCallButton.setEnabled(info.isCallable());
            mPlaceOrder.setEnabled(info.isPlaceOrderEnable());
            AuthenticateIconAdapter adapter = (AuthenticateIconAdapter) mRecyclerView.getAdapter();
            SystemCertificationMapper systemCertificationMapper = new SystemCertificationMapper();
            if (adapter == null) {
                adapter = new AuthenticateIconAdapter(systemCertificationMapper.transform(info));
                mRecyclerView.setAdapter(adapter);
            } else {
                adapter.setSystemCertificationList(systemCertificationMapper.transform(info));
            }
        }


        @OnClick(R.id.place_order_button)
        void placeOrderClick() {
            IServiceInfo serviceInfo = mPresenter.getServiceInfo();
            mNavigator.getOrder(mActivity, serviceInfo.type(), serviceInfo.getId());
        }

        @OnClick(R.id.call_button)
        void callClick(View view) {
            mPresenter.attemptCall();
            view.setEnabled(false);
        }

        @OnClick(R.id.direct_pay)
        void directPay() {
            IServiceInfo serviceInfo = mPresenter.getServiceInfo();
            String type = serviceInfo.type() == IServiceInfo.TYPE_WORKER ? Constant.PAY_TYPE_WORKER : Constant.PAY_TYPE_BUSINESS;
            mNavigator.directpay(mActivity, serviceInfo.getId(), type, serviceInfo.getDefaultServiceId(), serviceInfo.getName());
        }


        @OnClick(R.id.content_main)
        void onShowDetail() {
            IServiceInfo serviceInfo = mPresenter.getServiceInfo();
//            mNavigator.showWorkerDetail(mActivity, serviceInfo.getId());
            mNavigator.showMerchantWorkerDetail(mActivity, Constant.SERVICE_TYPE_WORKER, serviceInfo.getId());
            dismiss();
        }
    }


    class BusinessViewHolder {

        WorkerCollectionEntity infoModel;

        @Bind(R.id.photo)
        ImageView mPhoto;

        @Bind(R.id.name)
        TextView mName;

        @Bind(R.id.like)
        TextView mLike;

        @Bind(R.id.collection)
        TextView mCollection;

        @Bind(R.id.score)
        TextView mScore;

        @Bind(R.id.message_item1)
        TextView mItem1;

        @Bind(R.id.recyclerView)
        RecyclerView mRecyclerView;

        @Bind(R.id.direct_pay)
        ImageButton mDirectPay;

        @Bind(R.id.call_button)
        ImageButton mCallButton;

        @Bind(R.id.place_order_button)
        ImageButton mPlaceOrder;

        View contentView;

        BusinessViewHolder(View contentView) {
            this.contentView = contentView;
            ButterKnife.bind(this, contentView);

        }

        void setData(BusinessInfoModel info) {
            Glide.with(mActivity).load(info.getPhoto())
                    .placeholder(R.mipmap.worker_portrait_default)
                    .into(new ImageTarget(mPhoto));
            mName.setText(info.getName());
            mItem1.setText(info.getSplicedServices());
            mLike.setText(String.valueOf(info.getLikeCount()));
            mCollection.setText(String.valueOf(info.getCollectedCount()));
            mScore.setText(info.getGradeNumber());
            mCallButton.setEnabled(info.isCallable());
            mPlaceOrder.setEnabled(info.isPlaceOrderEnable());
            AuthenticateIconAdapter adapter = (AuthenticateIconAdapter) mRecyclerView.getAdapter();
            SystemCertificationMapper systemCertificationMapper = new SystemCertificationMapper();
            if (adapter == null) {
                adapter = new AuthenticateIconAdapter(systemCertificationMapper.transform(info));
                mRecyclerView.setAdapter(adapter);
            } else {
                adapter.setSystemCertificationList(systemCertificationMapper.transform(info));
            }
        }

        @OnClick(R.id.place_order_button)
        void placeOrderClick() {
            IServiceInfo serviceInfo = mPresenter.getServiceInfo();
            mNavigator.getOrder(mActivity, serviceInfo.type(), serviceInfo.getId());
        }

        @OnClick(R.id.call_button)
        void callClick(View view) {
            mPresenter.attemptCall();
            view.setEnabled(false);
        }

        @OnClick(R.id.direct_pay)
        void directPay() {
            IServiceInfo serviceInfo = mPresenter.getServiceInfo();
            String type = serviceInfo.type() == IServiceInfo.TYPE_WORKER ? Constant.PAY_TYPE_WORKER : Constant.PAY_TYPE_BUSINESS;
            mNavigator.directpay(mActivity, serviceInfo.getId(), type, serviceInfo.getDefaultServiceId(), serviceInfo.getName());
        }


        @OnClick(R.id.content_main)
        void onShowDetail() {
            IServiceInfo serviceInfo = mPresenter.getServiceInfo();
//            mNavigator.showBusinessDetail(mActivity, serviceInfo.getId());
            mNavigator.showMerchantWorkerDetail(mActivity, Constant.SERVICE_TYPE_BUSINESS, serviceInfo.getId());
        }

    }
}
