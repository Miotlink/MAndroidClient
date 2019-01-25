package com.homepaas.sls.data.repository;

import android.text.TextUtils;
import android.util.Log;

import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.data.exception.DelPersistenceDataException;
import com.homepaas.sls.data.exception.DeviceException;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.DeviceInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.entity.RegisterBody;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NotNormalDeviceException;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.param.LoginParam;
import com.homepaas.sls.domain.param.RegisterParam;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2015/12/23.
 */

@Singleton
public class PersonalInfoRepoImpl implements PersonalInfoRepo {

    private static final String TAG = "PersonalInfoRepo";

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private PersonalInfoPDataSource pDataSource;

    private PersonalInfoNDataSource nDataSource;

    private DeviceInfoPDataSource deviceInfoPDataSource;
    private String token;

    @Inject
    public PersonalInfoRepoImpl(PersonalInfoPDataSource pDataSource, PersonalInfoNDataSource nDataSource, DeviceInfoPDataSource deviceInfoPDataSource) {
        this.pDataSource = pDataSource;
        this.nDataSource = nDataSource;
        this.deviceInfoPDataSource = deviceInfoPDataSource;
    }

    @Override
    public PersonalInfo getPersonalInfo() throws GetDataException, AuthException {
        PersonalInfo personalInfo = null;
        try {
            personalInfo = pDataSource.get();
            try {
                pDataSource.saveTelphone(personalInfo.getPhoneNumber());
            } catch (PersistDataException e) {
                //保存本地时出错,可忽略
                Log.e(TAG, "getPersonalInfo: ", e);
                e.printStackTrace();
            }
            if (DEBUG)
                Log.d(TAG, "getPersonalInfo: p = " + personalInfo);
        } catch (GetPersistenceDataException | InvalidPersistenceDataException e) {
            try {
                personalInfo = nDataSource.get();
                if (DEBUG)
                    Log.d(TAG, "getPersonalInfo: n = " + personalInfo);
                pDataSource.save(personalInfo);
                pDataSource.saveTelphone(personalInfo.getPhoneNumber());
            } catch (GetNetworkDataException e1) {
                //请求数据错误,包括网络连接,返回状态码,响应数据错误等
                throw new GetDataException(Constant.NETWORK_ERROR, e1);
            } catch (ResponseMetaAuthException e1) {
                throw new AuthException(e1.getMessage(), e1);
            } catch (ResponseMetaDataException e1) {
                //传递meta错误数据
                throw new GetDataException(e1.getMessage(), e1);
            } catch (PersistDataException e1) {
                //保存本地时出错,可忽略
                Log.e(TAG, "getPersonalInfo: ", e1);
            }
        }
        return personalInfo;
    }

    @Override
    public void modifyNickname(String nickName) throws SaveDataException, AuthException {
        try {
            String token = pDataSource.getToken();
            nDataSource.modifyNickname(token, nickName);
            try {
                PersonalInfo personalInfo = pDataSource.get();
                personalInfo.setNickName(nickName);
                pDataSource.save(personalInfo);
            } catch (PersistDataException | GetPersistenceDataException | InvalidPersistenceDataException e) {
                Log.e(TAG, "modifyNickname: ", e);
            }
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            Log.e(TAG, "modifyNickname: ", e);
            throw new SaveDataException(Constant.NETWORK_ERROR);
        } catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        }
    }

    @Override
    public String modifyPhoto(String filePath) throws SaveDataException, AuthException {
        try {
            String token = pDataSource.getToken();
            String url = nDataSource.modifyPhoto(token, filePath);
            try {
                // xxx: 2016/3/15 0015  是否要重新获取一遍？？
                PersonalInfo personalInfo = pDataSource.get();
                personalInfo.setSmallPic(url);
                personalInfo.setHqPic(url);
                pDataSource.save(personalInfo);
            } catch (PersistDataException | InvalidPersistenceDataException e) {
                Log.e(TAG, "modifyPhoto: ", e);
            }
            return url;
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new SaveDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        }

    }

    @Override
    public RegisterBody register(RegisterParam param) throws SaveDataException {
        try {
            String deviceId = deviceInfoPDataSource.getDeviceId();
            RegisterBody response = nDataSource.register(param, deviceId);
            String token = response.getToken();
            pDataSource.saveToken(token);
            return response;
        } catch (GetNetworkDataException e) {
            throw new SaveDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        } catch (GetPersistenceDataException e) {
            throw new SaveDataException("无法获取设备信息，请确保开启读取电话状态相关权限", e);
        } catch (PersistDataException e) {
            Log.e(TAG, "register: ", e);
        }
        return null;
    }

    @Override
    public LoginBody login(LoginParam param) throws GetDataException, NotNormalDeviceException {
        try {
            String deviceId = deviceInfoPDataSource.getDeviceId();
            LoginBody response = nDataSource.login(param, deviceId);
            String token = response.getToken();
            pDataSource.saveToken(token);
            pDataSource.saveAccount(param);
            uploadDeviceId();
            return response;
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        } catch (PersistDataException e) {
            //保存数据失败，忽略
            Log.e(TAG, "login: ", e);
        } catch (DeviceException e) {
            throw new NotNormalDeviceException(e);
        } catch (GetPersistenceDataException e) {
            throw new GetDataException("无法获取设备信息，请确保开启读取电话状态相关权限", e);
        } catch (AuthException | SaveDataException e) {
            //ignore
            Log.e(TAG, "login: upload deviceId error", e);
        }
        return null;
    }

    @Override
    public LoginBody quickLogin(String phone,String captcha) throws GetDataException {
        try {
            String deviceId = deviceInfoPDataSource.getDeviceId();
            LoginBody response = nDataSource.quickLogin(phone,captcha, deviceId);
            String token = response.getToken();
            pDataSource.saveToken(token);
            pDataSource.saveAccount(new Account(phone));
            uploadDeviceId();
            return response;
        } catch (GetPersistenceDataException e) {
            throw new GetDataException("无法获取设备信息，请确保开启读取电话状态相关权限", e);
        } catch (GetNetworkDataException e) {
//            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (PersistDataException e) {
            //保存数据失败，忽略
            Log.e(TAG, "login: ", e);
        } catch (AuthException | SaveDataException e ) {
            //ignore
            Log.e(TAG, "login: upload deviceId error", e);
        }  catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e,e.getErrorCode());
        }
        return null;
    }

    @Override
    public boolean logout() throws SaveDataException {
        try {
            String token = pDataSource.getToken();
            clearDeviceId();//清除个推ID
            if (nDataSource.logout(token)) {
                pDataSource.clearToken();
                pDataSource.clearTelPhone();
                int count = pDataSource.deleteAll();
                return true;
            }
        } catch (GetPersistenceDataException | GetNetworkDataException
                | ResponseMetaDataException
                | DelPersistenceDataException
                | PersistDataException
                | AuthException
                | ResponseMetaAuthException e) {
            Log.e(TAG, "need clear token ", e);
            try {
                pDataSource.clearToken();
                pDataSource.clearTelPhone();
            } catch (PersistDataException e1) {
                Log.e(TAG, "logout: ", e1);
                return false;
            }
        } /*catch (GetNetworkDataException e) {
            throw new SaveDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        } catch (DelPersistenceDataException | PersistDataException e) {
            Log.e(TAG, "logout: ", e);
        } catch (AuthException e) {
            Log.e(TAG, "clear clientId error", e);
        }*/
        return true;
    }

    @Override
    public CaptchaBody sendCaptcha(String phone, int type) throws GetDataException, AuthException {
        try {
            return nDataSource.sendCaptcha(phone, type);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        } catch (ResponseMetaAuthException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "sendCaptcha: ", e1);
            }
            throw new AuthException(e.getMessage(), e);
        }
    }

    @Override
    public String requestResetPassword(String phone, String captcha) throws GetDataException, AuthException {
        try {
            return nDataSource.requestResetPassword(phone, captcha);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaAuthException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "requestResetPassword: ", e1);
            }
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }

    @Override
    public String resetPassword(String phone, String captcha, String newPassword) throws SaveDataException, AuthException {
        try {
            String deviceId = deviceInfoPDataSource.getDeviceId();
            String token = nDataSource.resetPassword(phone, captcha, newPassword, deviceId);
            pDataSource.saveToken(token);
            uploadDeviceId();
            return token;
        } catch (GetNetworkDataException e) {
            throw new SaveDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaAuthException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "resetPassword: ", e1);
            }
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        } catch (GetPersistenceDataException e) {
            throw new SaveDataException("无法获取设备信息，请确保开启读取电话状态相关权限", e);
        } catch (PersistDataException e) {
            Log.e(TAG, "resetPassword: Token 保存失败", e);
            return "";
        }
    }

    @Override
    public String modifyPassword(String oldPassword, String newPassword) throws SaveDataException, AuthException {
        try {
            String token = pDataSource.getToken();
            return nDataSource.modifyPassword(oldPassword, newPassword, token);
        } catch (GetNetworkDataException e) {
            throw new SaveDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "modifyPassword: ", e1);
            }
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        }
    }

    @Override
    public String feedback(String content) throws SaveDataException, AuthException {
        try {
            String token = pDataSource.getToken();
            return nDataSource.feedback(content, token);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "feedback: ", e1);
            }
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new SaveDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        }
    }

    @Override
    public boolean uploadDeviceId() throws SaveDataException, AuthException {
        try {
            String clientId = deviceInfoPDataSource.getPushDeviceId();
            String token = pDataSource.getToken();
            if (clientId != null) {
                return nDataSource.uploadDeviceId(clientId, token);
            }
        } catch (GetPersistenceDataException e) {
            //ignore
            e.printStackTrace();
        } catch (GetNetworkDataException e) {
            throw new SaveDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean clearDeviceId() throws SaveDataException, AuthException {
        try {
            String token = pDataSource.getToken();
            return nDataSource.clearDeviceId(token);
        } catch (GetPersistenceDataException | ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new SaveDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e);
        }
    }

    @Override
    public boolean isLoggedIn() throws GetDataException {
        try {
            String token = pDataSource.getToken();
            LoginParam account = pDataSource.getAccount();
            return nDataSource.checkTokenValid(account.getAccount(), token);
        } catch (GetPersistenceDataException | ResponseMetaAuthException | ResponseMetaDataException | GetNetworkDataException e) {
            throw new GetDataException(e);
        } catch (InvalidPersistenceDataException e) {
            Log.e(TAG, "isLoggedIn: ", e);
            return false;
        }
    }

    @Override
    public boolean autoLogin() throws GetDataException, AuthException {

        try {
            String token = pDataSource.getToken();
            if (TextUtils.isEmpty(token)) {
                return false;
            } else {
                LoginParam account = null;
                boolean result;
                try {
                    account = pDataSource.getAccount();
                    //token 有效
                    result = nDataSource.checkTokenValid(account.getAccount(), token);
                } catch (InvalidPersistenceDataException | GetNetworkDataException | ResponseMetaDataException | ResponseMetaAuthException e) {
                    //没账号记录无法自动登录
                    Log.e(TAG, "autoLogin: ", e);
                    result = false;
                }
                if (result) {
                    return true;
                } else if (account != null) {
                    //尝试登录
                    try {
                        LoginBody newResponse = login(account);
                        String newToken = newResponse.getToken();
                        pDataSource.saveToken(newToken);
                        return true;
                    } catch (NotNormalDeviceException e1) {
                        throw new GetDataException(e1);
                    } catch (PersistDataException e1) {
                        throw new GetDataException("token保存失败", e1);
                    }
                } else {
                    return false;
                }
            }
        } catch (GetPersistenceDataException e) {
            //获取本地token失败
            throw new GetDataException(e);
        }

    }

    @Override
    public UpdateCheck checkUpdate() throws GetDataException {
        try {
            return nDataSource.checkUpdate();
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }

    @Override
    public String getToken() {
        try {
            token = pDataSource.getToken();
            return token;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }


}
