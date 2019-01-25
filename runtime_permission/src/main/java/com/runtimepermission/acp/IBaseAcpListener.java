package com.runtimepermission.acp;

/**
 */
public interface IBaseAcpListener extends AcpListener {
    /**
     * 6.0之后的单击事件 回调
     */
    void onMarshmallowLaterClick();

    /**
     * 6.0之后权限捕获异常的释放资源回调
     */
    void onRelease();
}
