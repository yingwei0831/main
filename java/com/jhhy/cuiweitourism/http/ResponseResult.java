package com.jhhy.cuiweitourism.http;

import android.os.Handler;

import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.xutils.common.Callback;

import java.io.EOFException;
import java.net.SocketTimeoutException;

/**
 * Created by jiahe008 on 2016/9/1.
 */
public abstract class ResponseResult implements Callback.CommonCallback<String>, ResponseCallback{

    private static final String TAG = "ResponseResult";
    private Handler handler;

    public ResponseResult() {
    }

    public ResponseResult(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onSuccess(String result) {
        LogUtil.e(TAG, "请求成功，返回数据: " + result);
        responseSuccess(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.e(TAG, "onError: " + ex + ", " + isOnCallback);
        if (ex instanceof SocketTimeoutException){
            handler.sendEmptyMessage(Consts.NET_ERROR_SOCKET_TIMEOUT);
        }else if (ex instanceof EOFException){

        }
        LoadingIndicator.cancel();
    }

    @Override
    public void onCancelled(CancelledException cex) {
        LogUtil.e(TAG, "onCancelled: " + cex);
        LoadingIndicator.cancel();
    }

    @Override
    public void onFinished() {
        LogUtil.e(TAG, "onFinished");
    }


}
