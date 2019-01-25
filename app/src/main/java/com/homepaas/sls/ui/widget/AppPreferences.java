package com.homepaas.sls.ui.widget;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.util.StaticData;

import java.util.ArrayList;
import java.util.List;

public class AppPreferences {

	public AppPreferences() {

	}

	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences("androidClient", context.MODE_PRIVATE);
	}
	public static void setTags(Context context, String guideString) {
		Editor editor = getPreferences(context).edit();
		editor.putString("oldHistoryService", guideString);
		editor.commit();
	}

	public static String getTags(Context context) {
		return getPreferences(context).getString("oldHistoryService", "");
	}

	//3.3.0搜索页的历史数据
	public static void setHistoryTags(Context context, String guideString) {
		Editor editor = getPreferences(context).edit();
		editor.putString("newHistoryService", guideString);
		editor.commit();
	}

	public static String getHistoryTags(Context context) {
		return getPreferences(context).getString("newHistoryService", "");
	}

	public static void serHotService(Context context,List<HotServiceInfo> hotServiceInfos){
		Editor editor=getPreferences(context).edit();
		Gson gson = new Gson();
		//转换成json数据，再保存
		String strJson = gson.toJson(hotServiceInfos);
		editor.clear();
		editor.putString("hotService", strJson);
		editor.commit();
	}
	/**
	 * 获取List
	 * @return
	 */
	public static List<HotServiceInfo> getHotServiceList(Context context) {
		List<HotServiceInfo> hotServiceList=new ArrayList<HotServiceInfo>();
		String strJson = getPreferences(context).getString("hotService", null);
		if (null == strJson) {
			return hotServiceList;
		}
		Gson gson = new Gson();
		hotServiceList = gson.fromJson(strJson, new TypeToken<List<HotServiceInfo>>() {
		}.getType());
		return hotServiceList;
	}





}
