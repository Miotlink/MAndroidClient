package com.homepaas.sls.util;

import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebSettings;

import com.homepaas.sls.SimpleTinkerInApplicationLike;

import java.io.File;


/**
 * Created by mhy on 2017/8/11.
 * webview 缓存工具
 * 缓存策略为 判断是否有网络，有的话，使用LOAD_DEFAULT， 无网络时，使用LOAD_CACHE_ELSE_NETWORK。
 */

public class WebViewCacheUtils {

    //我们请求的Url记录是保存在webviewCache.db
    private static final String DB_NAME = "webview.db";
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    /**
     * 配置webview缓存
     * 几种缓存的模式：
     * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
     * LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
     * LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
     * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
     * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
     *
     * @param settings
     */
    public static void setWebViewCacheConfig(WebSettings settings) {
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);

//        Context context = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext();
////        判断是否有网络，有的话，使用LOAD_DEFAULT， 无网络时，使用LOAD_CACHE_ELSE_NETWORK。
//        if (NetUtils.isConnected(context)) {
////            设置缓存模式
//            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        } else {
////            设置缓存模式
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }

//        // 开启database storage API功能
//        settings.setDatabaseEnabled(true);
//        String cacheDirPath = context.getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
//        File file = new File(cacheDirPath);
//        if (!file.exists())
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        LogUtils.i("TAG", "cachePath" + cacheDirPath);
//        // 设置数据库缓存路径
//        settings.setAppCachePath(cacheDirPath);
//        settings.setAppCacheEnabled(true);
//        LogUtils.i("TAG", "databasepath" + settings.getDatabasePath());
    }

//    File file = new File(getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME);

    /**
     * 清除webview本地缓存
     */
    public static void clearWebViewCache(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    clearWebViewCache(files[i]);
                }
            }
            file.delete();
        } else {
//            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

    //把assets目录下的db文件复制到dbpath下
    public SQLiteDatabase DBManager() {
        String dbPath = "/data/data/" + SimpleTinkerInApplicationLike.
                getMainApplication().getApplicationContext().getPackageName()
                + "/databases/" + DB_NAME;
        return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }
}
