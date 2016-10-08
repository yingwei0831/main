package com.jhhy.cuiweitourism.http;

import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.xutils.common.Callback;

/**
 * Created by jiahe008 on 2016/9/1.
 */
public abstract class ResponseResult implements Callback.CommonCallback<String>, ResponseCallback{

    private static final String TAG = ResponseResult.class.getSimpleName();

    @Override
    public void onSuccess(String result) {
        LogUtil.e(TAG, "请求成功，返回数据: " + result);
        responseSuccess(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.e(TAG, "onError: " + ex + ", " + isOnCallback);
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
