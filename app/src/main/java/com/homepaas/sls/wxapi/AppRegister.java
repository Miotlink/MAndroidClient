package com.homepaas.sls.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

	private static final String TAG = "AppRegister";
	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI iwxapi = WXAPIFactory.createWXAPI(context,null);
		iwxapi.registerApp(Constants.APP_ID);

		Log.i(TAG, "onReceive: ");
	}
}
