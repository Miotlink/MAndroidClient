package com.homepaas.sls.tinker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;

import java.io.File;

/**
 * Created by mhy on 2017/8/9.
 * 在加载补丁后供 Tinker 回调的一个类
 * 加载成功热更新插件后，会提示你更新成功，并且这里做了锁屏操作就会加载热更新插件。
 * 注册一个处理加载补丁结果的service
 */
public class TinkerResultService extends DefaultTinkerResultService {
    private static final String TAG = "TinkerResultService";

    @Override
    public void onPatchResult(final PatchResult result) {
        super.onPatchResult(result);

        if (result == null) {
            TinkerLog.e(TAG, "TinkerResultService received null result!!!!");
            return;
        }
        TinkerLog.i(TAG, "TinkerResultService receive result: %s", result.toString());

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (result.isSuccess) {
                    Toast.makeText(getApplicationContext(), "补丁成功，请重新启动进程", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "补丁失败，请检查原因", Toast.LENGTH_LONG).show();
                }
            }
        });
        //首先，我们想要扼杀恢复进程
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());

        // 成功和新补丁，删除原始文件并立即重新启动是很好的
        // 对于旧补丁，您不能删除补丁文件
        if (result.isSuccess) {
            File rawFile = new File(result.rawPatchFilePath);
            if (rawFile.exists()) {
                TinkerLog.i(TAG, "保存删除原始补丁文件");
                SharePatchFileUtil.safeDeleteFile(rawFile);
            }
            //不像TinkerResultService，我想在后台重新启动
            //如果您现在还没有安装修补程序，那么可以使用TinkerApplicationHelper ap
            if (checkIfNeedKill(result)) {
                if (TinkerUtils.isBackground(getApplicationContext())) {
                    TinkerLog.i(TAG, "它在后台，重新启动进程");
                    restartProcess();
                } else {
                    //我们可以在后台等待进程，比如app后台
                    //修改后的屏幕重新启动进程
                    TinkerLog.i(TAG, "修改后的屏幕重新启动进程");
                    new ScreenState(getApplicationContext(), new ScreenState.IOnScreenOff() {
                        @Override
                        public void onScreenOff() {
                            restartProcess();
                        }
                    });
                }
            } else {
                TinkerLog.i(TAG, "我已经安装了新补丁版本");
            }
        }
    }

    /**
     * 您可以通过服务或广播重新启动您的进程
     */
    private void restartProcess() {
        TinkerLog.i(TAG, "：app是后台时，我可以安静地杀戮");
        //you can send service or broadcast intent to restart your process
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    static class ScreenState {
        interface IOnScreenOff {
            void onScreenOff();
        }

        ScreenState(Context context, final IOnScreenOff onScreenOffInterface) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            context.registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent in) {
                    String action = in == null ? "" : in.getAction();
                    TinkerLog.i(TAG, "ScreenReceiver action [%s] ", action);
                    if (Intent.ACTION_SCREEN_OFF.equals(action)) {

                        context.unregisterReceiver(this);

                        if (onScreenOffInterface != null) {
                            onScreenOffInterface.onScreenOff();
                        }
                    }
                }
            }, filter);
        }
    }
}
