package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.List;

/**
 * on 2016/6/7 0007
 *
 * @author zhudongjie
 */
// TODO: 将PersonalInfoRepo的部分功能移过来
public interface LoginInfoRepo {

     List<Account> getAccountList()throws GetDataException;
}
