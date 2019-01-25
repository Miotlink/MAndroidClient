package com.homepaas.sls.mvp.presenter.pay;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.BalanceInfo;
import com.homepaas.sls.domain.entity.CreateDirectPayExEntity;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.entity.OrderPayWXSign;
import com.homepaas.sls.domain.entity.WxSign;
import com.homepaas.sls.domain.interactor.BalancePayInteractor;
import com.homepaas.sls.domain.interactor.DirectBalancePayInteractor;
import com.homepaas.sls.domain.interactor.GetAccountBalanceInteractor;
import com.homepaas.sls.domain.interactor.GetDirectPayAliSignInteractor;
import com.homepaas.sls.domain.interactor.GetDirectPayIdInteractor;
import com.homepaas.sls.domain.interactor.GetDirectPayWxSignInteractor;
import com.homepaas.sls.domain.interactor.GetOrderPayAliSignInteractor;
import com.homepaas.sls.domain.interactor.GetOrderPayWXSignInteractor;
import com.homepaas.sls.domain.param.GetOrderPayAliSignParams;
import com.homepaas.sls.domain.param.GetOrderpayWxSignParams;
import com.homepaas.sls.domain.repository.CreateDirectPayExRepo;
import com.homepaas.sls.domain.repository.NewAccountInfoRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.GetWXSignView;
import com.homepaas.sls.mvp.view.account.WalletBalanceView;
import com.homepaas.sls.mvp.view.order.BalancePayView;
import com.homepaas.sls.mvp.view.order.GetAliSignView;
import com.homepaas.sls.mvp.view.pay.GetBalanceView;
import com.homepaas.sls.mvp.view.pay.GetDirectPayIdView;
import com.homepaas.sls.mvp.view.pay.GetDirectPaySignView;

import javax.inject.Inject;

/**
 * Created by CMJ on 2016/6/25.
 */
@ActivityScope
public class PayPresenter implements Presenter {

    GetBalanceView getBalanceView;

    @Inject
    BalancePayInteractor balancePayInteractor;
    @Inject
    GetAccountBalanceInteractor getAccountBalanceInteractor;
    @Inject
    GetOrderPayAliSignInteractor getOrderPayAliSignInteractor;
    @Inject
    GetOrderPayWXSignInteractor getOrderPayWXSignInteractor;
    @Inject
    GetDirectPayIdInteractor getDirectPayIdInteractor;
    @Inject
    GetDirectPayWxSignInteractor getDirectPayWxSignInteractor;
    @Inject
    GetDirectPayAliSignInteractor getDirectPayAliSignInteractor;
    @Inject
    DirectBalancePayInteractor directBalancePayInteractor;
    @Inject
    NewAccountInfoRepo response;
    @Inject
    CreateDirectPayExRepo createDirectPayExRepo;
    GetAliSignView getAliSignView;
    GetWXSignView getWXSignView;
    BalancePayView balancePayView;
    GetDirectPayIdView getDirectPayIdView;
    GetDirectPaySignView getDirectPaySignView;
    private WalletBalanceView walletBalanceView;

    public void setWalletBalanceView(WalletBalanceView walletBalanceView){
        this.walletBalanceView=walletBalanceView;
    }
    public void setGetAliSignView(GetAliSignView getAliSignView) {
        this.getAliSignView = getAliSignView;
    }

    public void setGetWXSignView(GetWXSignView getWXSignView) {
        this.getWXSignView = getWXSignView;
    }

    public void setBalancePayView(BalancePayView balancePayView) {
        this.balancePayView = balancePayView;
    }

    public void setGetDirectPayIdView(GetDirectPayIdView getDirectPayIdView) {
        this.getDirectPayIdView = getDirectPayIdView;
    }

    public void setAccountBalanceView(GetBalanceView getBalanceView) {
        this.getBalanceView = getBalanceView;
    }
    public void setGetDirectPaySignView(GetDirectPaySignView getDirectPaySignView) {
        this.getDirectPaySignView = getDirectPaySignView;
    }

    @Inject
    public PayPresenter() {
    }

    /**
     * 支付宝支付
     */
    public void getAliPaySign(String orCode, String redEnvelopIds, String needPay, String balancePay) {
        getAliSignView.showLoading();
        getOrderPayAliSignInteractor.setParams(new GetOrderPayAliSignParams(orCode, redEnvelopIds, needPay, balancePay));
        getOrderPayAliSignInteractor.execute(new OldBaseObserver<String>(getAliSignView) {
            @Override
            public void onNext(String sign) {
                getAliSignView.render(sign);
            }
        });

    }

    /**
     * 获取微信支付签名
     *
     * @param orderCode
     * @param redEnvelopIds
     * @param needPay
     * @param balancePay
     */
    public void getWXPaysign(String orderCode, String redEnvelopIds, String needPay, String balancePay) {
        getWXSignView.showLoading();
        getOrderPayWXSignInteractor.setParams(new GetOrderpayWxSignParams(orderCode, redEnvelopIds, needPay, balancePay));
        getOrderPayWXSignInteractor.execute(new OldBaseObserver<OrderPayWXSign>(getWXSignView) {
            @Override
            public void onNext(OrderPayWXSign wxSign) {
                getWXSignView.render(wxSign);
            }
        });

    }

    /**
     * 余额支付
     */
    public void payByBalance(String orderId, String couponId, String balancePayMoney) {
        balancePayInteractor.setParam(orderId, couponId, balancePayMoney);
        balancePayView.showLoading();
        balancePayInteractor.execute(new OldBaseObserver<String>(balancePayView) {
            @Override
            public void onNext(String s) {
                balancePayView.onBalancePayResult(s);
            }

            @Override
            public void showError(Throwable t) {
                super.showError(t);
            }
        });

    }

    /**
     * 获取账户余额信息
     */
    public void getAccountBalance() {
        getAccountBalanceInteractor.execute(new OldBaseObserver<BalanceInfo>(getBalanceView) {
            @Override
            public void onNext(BalanceInfo balanceInfo) {
                getBalanceView.render(balanceInfo);
            }
        });
    }

    /**
     * 新账户余额信息
     */
    public void getAccountInfo() {
        response.getAccountInfo()
               .compose(RxUtil.<NewAccountInfo>applySchedulers())
                .subscribe(new OldBaseObserver<NewAccountInfo>(walletBalanceView) {
                    @Override
                    public void onNext(NewAccountInfo newAccountInfo) {
                        walletBalanceView.renderAccountInfo(newAccountInfo);
                    }
                });
    }

    /**
     * 获取支付id
     * @param receiverId
     * @param receiverType
     * @param androidFrom
     * @param totalMon
     * @param serviceTypeId
     */
    public void getDirectPayId(String receiverId, String receiverType, String androidFrom, String totalMon, String serviceTypeId) {
        getDirectPayIdInteractor.setParams(receiverId,receiverType,androidFrom,totalMon,serviceTypeId);
        getDirectPayIdView.showLoading();
        getDirectPayIdInteractor.execute(new OldBaseObserver<String>(getDirectPayIdView) {
            @Override
            public void onNext(String s) {
                getDirectPayIdView.onDirectPayIdResult(s);
            }
        });
    }


    /**
     * 获取直接支付微信签名
     * @param id
     * @param balancePayMoney
     */
    public void getDirectPayWxSign(String id,String balancePayMoney) {
        getDirectPayWxSignInteractor.setparams(id,balancePayMoney);
        getDirectPayWxSignInteractor.execute(new OldBaseObserver<WxSign>(getDirectPaySignView) {

            @Override
            public void onNext(WxSign sign) {
                getDirectPaySignView.onWxSign(sign);
            }
        });
    }

    /**
     * 获取直接支付支付宝签名
     * @param id
     * @param balancePayMoney
     */
    public void getDirectPayAliSign(String id,String balancePayMoney){
        getDirectPayAliSignInteractor.setparams(id,balancePayMoney);
        getDirectPaySignView.showLoading();
        getDirectPayAliSignInteractor.execute(new OldBaseObserver<String>(getDirectPaySignView) {
            @Override
            public void onNext(String s) {
                getDirectPaySignView.onAliSign(s);
            }
        });
    }

    public void directBalancePay(String id, String totalMon) {
        directBalancePayInteractor.setParams(id);
        getDirectPaySignView.showLoading();
        directBalancePayInteractor.execute(new OldBaseObserver<String>(getDirectPaySignView) {
            @Override
            public void onNext(String s) {
                getDirectPaySignView.onBalancePayResult(s);
            }
        });
    }

    public void createDirectPay(String receiverID, String receiverType, String paySource, String totalMoney, String activityNgId){
        getDirectPayIdView.showLoading();
        createDirectPayExRepo.getCreateDirectPayEx(receiverID,receiverType,paySource,totalMoney,activityNgId)
                .compose(RxUtil.<CreateDirectPayExEntity>applySchedulers())
                .subscribe(new OldBaseObserver<CreateDirectPayExEntity>(getDirectPayIdView) {
                    @Override
                    public void onNext(CreateDirectPayExEntity createDirectPayExEntity) {
                        getDirectPayIdView.onDirectPayIdResult(createDirectPayExEntity.getId());
                    }
                });

    }
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        balancePayInteractor.unsubscribe();
        getAccountBalanceInteractor.unsubscribe();
        getOrderPayAliSignInteractor.unsubscribe();
        getOrderPayWXSignInteractor.unsubscribe();
        getDirectPayIdInteractor.unsubscribe();
        getDirectPayWxSignInteractor.unsubscribe();
        getDirectPayAliSignInteractor.unsubscribe();
        directBalancePayInteractor.unsubscribe();
    }
}
