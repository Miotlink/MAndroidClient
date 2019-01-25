package com.homepaas.sls.speech;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.homepaas.sls.BuildConfig;

import java.util.Arrays;

/**
 * a simple implementation of {@link RecognitionListener}
 *
 * @author zhudongjie .
 */
public abstract class RecognitionAdapter implements RecognitionListener {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private final String tag = getClass().getSimpleName();


    @Override
    public void onPartialResults(Bundle partialResults) {
        if (DEBUG)
            Log.d(tag, "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        if (DEBUG)
            Log.d(tag, "onEvent: eventType = " + eventType);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        if (DEBUG)
            Log.d(tag, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        if (DEBUG)
            Log.d(tag, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        if (DEBUG)
            Log.d(tag, "onRmsChanged: "+rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        if (DEBUG)
            Log.d(tag, "onBufferReceived: " + Arrays.toString(buffer));
    }

    @Override
    public void onEndOfSpeech() {
        if (DEBUG)
            Log.d(tag, "onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":").append(error);
        Log.e(tag, "识别失败 " + sb.toString());
    }

}
