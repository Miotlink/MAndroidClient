package com.runtimepermission.acp;

import java.util.List;

/**
 */
public interface AcpListener {
    /**
     *权限允许成功
     */
    void onGranted();

    /**
     * 拒绝权限允许
     */
    void onDenied(List<String> permissions);
}
