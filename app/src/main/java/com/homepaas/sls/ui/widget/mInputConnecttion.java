package com.homepaas.sls.ui.widget;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

public class mInputConnecttion extends InputConnectionWrapper implements InputConnection {

public mInputConnecttion(InputConnection target, boolean mutable) {
    super(target, mutable);
}

/**
 * 对输入的内容进行拦截
 *
 * @param text
 * @param newCursorPosition
 * @return
 */
@Override
public boolean commitText(CharSequence text, int newCursorPosition) {
    // 只能输入汉字和字母
    if (!text.toString().matches("[\u4e00-\u9fa5]+")&& !text.toString().matches("[a-zA-Z /]+")) {
        return false;
    }
    return super.commitText(text, newCursorPosition);
}

@Override
public boolean sendKeyEvent(KeyEvent event) {
    return super.sendKeyEvent(event);
}

@Override
public boolean setSelection(int start, int end) {
    return super.setSelection(start, end);
}
}
