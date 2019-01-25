package com.homepaas.sls.domain.repository;

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


/**
 * Created by Administrator on 2015/12/23.
 */

public interface PersonalInfoRepo {

    PersonalInfo getPersonalInfo() throws GetDataException, AuthException;

    void modifyNickname(String nickName) throws SaveDataException, AuthException;

    /**
     * 修改头像
     * @param filePath 本地图片路径
     * @return 服务器端图片地址
     * @throws SaveDataException
     * @throws AuthException
     */
    String modifyPhoto(String filePath) throws SaveDataException, AuthException;

    RegisterBody register(RegisterParam param) throws SaveDataException;

    LoginBody login(LoginParam param) throws GetDataException,NotNormalDeviceException;

    LoginBody quickLogin(String phone,String captcha) throws GetDataException;

    boolean logout() throws SaveDataException;

    CaptchaBody sendCaptcha(String phone, int type) throws GetDataException,AuthException;

    String requestResetPassword(String phone, String captcha) throws GetDataException, AuthException;

    String resetPassword(String phone,String captcha,String newPassword) throws SaveDataException, AuthException;

    String modifyPassword(String oldPassword, String newPassword) throws SaveDataException, AuthException;

    String feedback(String content)throws SaveDataException,AuthException;

    boolean uploadDeviceId()throws SaveDataException,AuthException;

    boolean clearDeviceId()throws SaveDataException,AuthException;

    @Deprecated
    boolean isLoggedIn()throws GetDataException;

    @Deprecated
    boolean autoLogin()throws GetDataException,AuthException;

    UpdateCheck checkUpdate()throws GetDataException;

    String getToken();
}
