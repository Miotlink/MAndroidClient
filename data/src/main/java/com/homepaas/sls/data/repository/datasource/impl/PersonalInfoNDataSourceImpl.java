package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.entity.mapper.PersonalInfoEntityMapper;
import com.homepaas.sls.data.exception.DeviceException;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ModifyPhotoBody;
import com.homepaas.sls.data.network.dataformat.PersonalInfoDataSegment;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.RegisterBody;
import com.homepaas.sls.data.network.dataformat.ResetPasswordBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.CaptchaRequest;
import com.homepaas.sls.data.network.dataformat.request.CheckTokenRequest;
import com.homepaas.sls.data.network.dataformat.request.CheckUpdateBody;
import com.homepaas.sls.data.network.dataformat.request.FeedbackRequest;
import com.homepaas.sls.data.network.dataformat.request.LoginRequest;
import com.homepaas.sls.data.network.dataformat.request.ModifyNicknameRequest;
import com.homepaas.sls.data.network.dataformat.request.ModifyPasswordRequest;
import com.homepaas.sls.data.network.dataformat.request.PushServiceIdRequest;
import com.homepaas.sls.data.network.dataformat.request.RegisterRequest;
import com.homepaas.sls.data.network.dataformat.request.ResetPassword1Request;
import com.homepaas.sls.data.network.dataformat.request.ResetPassword2Request;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.domain.param.LoginParam;
import com.homepaas.sls.domain.param.RegisterParam;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Administrator on 2015/12/23.
 */

@Singleton
public class PersonalInfoNDataSourceImpl implements PersonalInfoNDataSource {

    private static final String TAG = "PersonalInfoNDataSource";

    private static final boolean DEBUG = true;

    private RestApiHelper apiHelper;

    private PersonalInfoEntityMapper mapper;


    private PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public PersonalInfoNDataSourceImpl(RestApiHelper apiHelper, PersonalInfoEntityMapper mapper,
                                       PersonalInfoPDataSource personalInfoPDataSource) {
        this.apiHelper = apiHelper;
        this.mapper = mapper;
        this.personalInfoPDataSource = personalInfoPDataSource;
    }

    @Override
    public PersonalInfo get() throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        try {
            String token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<PersonalInfoDataSegment>> response = apiHelper.getPersonalInfo(token);
            //通常[200, 300)之内的状态码都被认为是正确的, 可以通过 response.isSuccess() 来判断
            if (response.code() == 200) {
                ResponseWrapper<PersonalInfoDataSegment> responseWrapper = response.body();
                Meta meta = responseWrapper.meta;
                PersonalInfoDataSegment personalInfoDataSegment = responseWrapper.data;
                if (meta != null) {
                    if (!meta.isValid()) {
                        throw new ResponseMetaDataException(meta.getErrorMsg());
                    }
                    if (personalInfoDataSegment != null && personalInfoDataSegment.getInfo() != null) {
                        return mapper.transform(personalInfoDataSegment.getInfo());
                    }
                }
            }
            throw new GetNetworkDataException();
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (GetPersistenceDataException e) {
            Log.e(TAG, "getPersonalInfo", e);
            return null;
        }
    }

    @Override
    public int modifyNickname(String token, String nickname) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        ModifyNicknameRequest request = new ModifyNicknameRequest(token, nickname);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.modifyNickname(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return 0;
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }

    }

    @Override
    public String modifyPhoto(String token, String filePath) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        TokenRequest request = new TokenRequest(token);
        try {
            Response<ResponseWrapper<ModifyPhotoBody>> response = apiHelper.modifyPhoto(request, filePath);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    ResponseWrapper<ModifyPhotoBody> body = response.body();
                    return body.data.getImageUrl();
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("网络异常");
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }


    @Override
    public RegisterBody register(RegisterParam param, String deviceId) throws GetNetworkDataException, ResponseMetaDataException {
        try {
            RegisterRequest request = new RegisterRequest(param.phone, param.password, param.captcha, param.type, deviceId,param.invitationCode);
            Response<ResponseWrapper<RegisterBody>> response = apiHelper.register(request);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    if (param.type == Constant.REGISTER_TYPE_LOGIN) {
                        ResponseWrapper<RegisterBody> body = response.body();
                        return body.data;
                    } else return null;
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException();
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //impossible
            Log.e(TAG, "register: ", e);
            return null;
        }

    }

    @Override
    public LoginBody login(LoginParam loginParam, String deviceId) throws GetNetworkDataException, ResponseMetaDataException, DeviceException {
        LoginRequest loginRequest = new LoginRequest(loginParam.getAccount(), loginParam.getPassword(), loginParam.getCaptcha(), deviceId);
        try {
            Response<ResponseWrapper<LoginBody>> response = apiHelper.login(loginRequest);
            if (response.code() == 200) {
                if (!response.body().meta.isValid()) {
                    if ("2003".equals(response.body().meta.getErrorCode())) {
                        throw new DeviceException(response.body().meta.getErrorMsg());
                    }
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
                return response.body().data;
            }
            throw new GetNetworkDataException();
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //impossible
            Log.e(TAG, "login: ", e);
            return null;
        }
    }

    @Override
    public LoginBody quickLogin(String phone, String captch, String deviceId) throws GetNetworkDataException, ResponseMetaDataException{
        LoginRequest loginRequest = new LoginRequest(phone, captch, deviceId);
        try {
            Response<ResponseWrapper<LoginBody>> response = apiHelper.quickLogin(loginRequest);
            if (response.code() == 200){
                if (!response.body().meta.isValid()) {
                    throw new ResponseMetaDataException(response.body().meta.getErrorCode(),response.body().meta.getErrorMsg());
                }
                return response.body().data;
            }
            throw new GetNetworkDataException();
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //impossible
            Log.e(TAG, "login: ", e);
            return null;
        }
    }

    @Override
    public boolean checkTokenValid(String phone, String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        CheckTokenRequest request = new CheckTokenRequest(phone, token);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.checkToken(request);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public boolean logout(String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        TokenRequest tokenRequest = new TokenRequest(token);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.logout(tokenRequest);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public CaptchaBody sendCaptcha(String phone, int type) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        CaptchaRequest request = new CaptchaRequest(phone, type);
        try {
            Response<ResponseWrapper<CaptchaBody>> response = apiHelper.sendCaptcha(request);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    return response.body().data;
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException();
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public String requestResetPassword(String phone, String captcha) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {

        ResetPassword1Request request = new ResetPassword1Request(phone, captcha);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.requestResetPassword(request);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    return "";
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码：" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }

    }

    @Override
    public String resetPassword(String phone, String captcha, String newPassword, String deviceId) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        ResetPassword2Request request = new ResetPassword2Request(phone, captcha, newPassword, deviceId);
        try {
            Response<ResponseWrapper<ResetPasswordBody>> response = apiHelper.resetPassword(request);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    return response.body().data.getToken();
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码：" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public String modifyPassword(String oldPassword, String newPassword, String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        ModifyPasswordRequest request = new ModifyPasswordRequest(token, oldPassword, newPassword);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.modifyPassword(request);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    return "";
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码：" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public String feedback(String content, String token) throws ResponseMetaAuthException, ResponseMetaDataException, GetNetworkDataException {
        FeedbackRequest request = new FeedbackRequest(token, content);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.feedback(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return "";
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public boolean uploadDeviceId(String clientId, String token) throws ResponseMetaAuthException, ResponseMetaDataException, GetNetworkDataException {
        PushServiceIdRequest request = new PushServiceIdRequest(token, clientId);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.uploadPushDeviceId(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public boolean clearDeviceId(String token) throws ResponseMetaAuthException, ResponseMetaDataException, GetNetworkDataException {
        TokenRequest request = new TokenRequest(token);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.clearPushDeviceId(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public UpdateCheck checkUpdate() throws GetNetworkDataException, ResponseMetaDataException {
        try {
            Response<ResponseWrapper<CheckUpdateBody>> response = apiHelper.checkUpdate();
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.update;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e.getMessage(), e);
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }
        return null;
    }
}
