package com.homepaas.sls.util;

import android.content.Context;
import android.text.TextUtils;

import com.meituan.android.walle.WalleChannelReader;

/**
 * Created by mhy on 2017/8/3.
 * 第三方平台工具类
 */

public class PlatformUtils {
    private static final String MARKET_ID = "marketId";

    /**
     * 获取美团的多渠道信息
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        String channel = WalleChannelReader.getChannel(context.getApplicationContext());
        LogUtils.i("TAG", "channel:" + channel);
        if (TextUtils.isEmpty(channel))
            return "官方";
        else
            return channel;
    }

    /**
     * 获取后台需要的渠道标识
     *
     * @param context
     * @return
     */
    public static String getChannelMarketId(Context context) {
        // 或者也可以直接根据key获取
        String marketId = WalleChannelReader.get(context, MARKET_ID);
        LogUtils.i("TAG", "channel:" + marketId);
        if (TextUtils.isEmpty(marketId))
            return "0";
        else
            return marketId;
    }
}
