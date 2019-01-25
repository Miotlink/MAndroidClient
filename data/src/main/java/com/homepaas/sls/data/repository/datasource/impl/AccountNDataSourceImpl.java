package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.AccountDetailBody;
import com.homepaas.sls.data.network.dataformat.AccountInfoBody;
import com.homepaas.sls.data.network.dataformat.AlipaySignBody;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.PayDetailBody;
import com.homepaas.sls.data.network.dataformat.RechargeListBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.WeChatPayBody;
import com.homepaas.sls.data.network.dataformat.request.GetPaySignRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.AccountNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AccountDetail;
import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.domain.entity.PayDetail;
import com.homepaas.sls.domain.entity.Recharge;
import com.homepaas.sls.domain.entity.WeChatPaySign;
import com.homepaas.sls.domain.param.RechargePaySignParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
@Singleton
public class AccountNDataSourceImpl implements AccountNDataSource {

    private static final String TAG = "AccountNDataSourceImpl";

    @Inject
    PersonalInfoPDataSource pDataSource;

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public AccountNDataSourceImpl() {
    }

    @Override
    public AccountInfo getAccount(String token) throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException {
        try {
            TokenRequest request = new TokenRequest(token);
            Response<ResponseWrapper<AccountInfoBody>> response = apiHelper.getAccount(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.accountInfo;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public List<AccountDetail> getAccountDetails(String token) throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException {
        try {
            TokenRequest request = new TokenRequest(token);
            Response<ResponseWrapper<AccountDetailBody>> response = apiHelper.getAccountDetails(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.accountDetails;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public List<PayDetail> getPayDetailList(String token) throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException {
        try {
            TokenRequest request = new TokenRequest(token);
            Response<ResponseWrapper<PayDetailBody>> response = apiHelper.getPayDetails(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.payDetails;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public List<Recharge> getRechargeList() throws GetNetworkDataException, ResponseMetaDataException {
        try {
            Response<ResponseWrapper<RechargeListBody>> response = apiHelper.getRecharges();
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.rechargeList;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            // ignore ,this Exception should never happen
            Log.e(TAG, "getRechargeList: ", e);
        }
        return new ArrayList<>();
    }

    @Override
    public String getAlipaySigh(String token, RechargePaySignParam param) throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException {
        GetPaySignRequest request = new GetPaySignRequest(token, param.needPay, param.totalMoney, param.activity);
        try {
            Response<ResponseWrapper<AlipaySignBody>> response = apiHelper.getAlipaySign(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.alipaySign;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }

    }

    @Override
    public WeChatPaySign getWeChatPaySign(String token, RechargePaySignParam param) throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException {
        GetPaySignRequest request = new GetPaySignRequest(token, param.needPay, param.totalMoney, param.activity);
        try {
            Response<ResponseWrapper<WeChatPayBody>> response = apiHelper.getWeChatPaySign(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.weChatPaySign;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }
}
