package com.homepaas.sls;

import javax.inject.Singleton;

/**
 * Created by fmisser on 2016/8/5.
 *
 */

@Singleton
public class Global {

    /**
     * 新手活动,最大显示次数控制.
     */
    public static final int MAX_TIMES_DISPLAY_FOR_NEWCOMER_ACTIVE = 3;
    /**
     * 新手活动Pref key.
     */
    public static final String PREF_KEY_FOR_NEWCOMER_ACTIVE = "newcomer_active";
    /**
     * 指导页Pref name,根据版本号生成不同名称,用来控制每个版本显示s
     */
    private String firstPrefMain;
    /**
     * 引导页 Pref name,根据版本号生成不同名称,用来控制每个版本显示
     */
    private String firstPref;
    /**
     * 新手活动 Pref name,根据版本号生成不同名称,用来控制每个版本显示
     */
    private String firstPrefNewcomerActive;

    public Global() {
        initPref();
    }

    private void initPref() {
        firstPrefMain = "first_pref_main_" + BuildConfig.VERSION_CODE;
        firstPref = "first_pref_" + BuildConfig.VERSION_CODE;
        firstPrefNewcomerActive = "first_pref_newcomer_active_" + BuildConfig.VERSION_CODE;
    }

    public String getFirstPrefMain() {
        return firstPrefMain;
    }

    public String getFirstPref() {
        return firstPref;
    }

    public String getFirstPrefNewcomerActive() {
        return firstPrefNewcomerActive;
    }
}
