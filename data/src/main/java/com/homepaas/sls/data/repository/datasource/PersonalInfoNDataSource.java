package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.DeviceException;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.entity.RegisterBody;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.param.LoginParam;
import com.homepaas.sls.domain.param.RegisterParam;

/**
 * Created by Administrator on 2015/12/23.
 */

public interface PersonalInfoNDataSource {

    PersonalInfo get()
            throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    int modifyNickname(String token,String nickname)
            throws GetNetworkDataException, ResponseMetaAuthException,ResponseMetaDataException;

    String modifyPhoto(String token,String filePath)
            throws GetNetworkDataException,ResponseMetaAuthException, ResponseMetaDataException;

    /**
     * 注册
     *
     * @param param 注册参数
     * @param deviceId 设备号
     * @return token
     * @throws GetNetworkDataException
     */
    RegisterBody register(RegisterParam param, String deviceId) throws GetNetworkDataException, ResponseMetaDataException;

    /**
     * 登录
     *
     * @param loginParam 登录参数
     * @return token
     */
    LoginBody login(LoginParam loginParam, String deviceId) throws GetNetworkDataException, ResponseMetaDataException, DeviceException;

    LoginBody quickLogin(String phone, String captch, String deviceId)throws GetNetworkDataException, ResponseMetaDataException;

    /**
     * 服务器端不再提供专门验证的该接口，token校验放在请求参数中
     */
    @Deprecated
    boolean checkTokenValid(String phone,String token)throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;;

    boolean logout(String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    CaptchaBody sendCaptcha(String phone, int type) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    String requestResetPassword(String phone,String captcha)throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    /**
     *
     * @param phone
     * @param captcha
     * @param newPassword
     * @param deviceId
     * @return token
     */
    String resetPassword(String phone,String captcha,String newPassword,String deviceId)throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    String modifyPassword(String oldPassword,String newPassword,String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    String feedback(String content,String token) throws ResponseMetaAuthException, ResponseMetaDataException, GetNetworkDataException;

    boolean uploadDeviceId(String clientId,String token)throws ResponseMetaAuthException, ResponseMetaDataException, GetNetworkDataException;

    boolean clearDeviceId(String token)throws ResponseMetaAuthException, ResponseMetaDataException, GetNetworkDataException;

    UpdateCheck checkUpdate() throws GetNetworkDataException, ResponseMetaDataException;
}
