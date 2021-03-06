package com.jhhy.cuiweitourism.net.netcallback;

import android.widget.Toast;

import com.google.gson.Gson;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by birneysky on 16/9/30.
 */
public class FetchCallBack implements Callback.CommonCallback<String> {

    protected FetchResponse response;

    private static final String TAG = "FetchCallBack";

    public FetchCallBack(FetchResponse response){
        this.response = response;
    }

    @Override
    public void onSuccess(String result) {
        LogUtil.e(TAG,"onSuccess: " + result);
        try {
            JSONObject resultObj = new JSONObject(result);
            String bodyStr = resultObj.getString("body");
            String headStr = resultObj.getString("head");
            FetchResponseModel model =  new FetchResponseModel(); //new Gson().fromJson(result,FetchResponseModel.class);
            model.body = bodyStr;
            model.head = new Gson().fromJson(headStr, FetchResponseModel.HeadModel.class);
            this.response.onCompletion(model);
            //handler.sendMessage(new Message());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        String responseMsg =  ex.getMessage();
        String errorResult = ex.getLocalizedMessage();
        FetchError error = new FetchError();
        error.errroCode = -1;
        error.msg = responseMsg;
        error.info = errorResult;
        error.exceptionName = ex.toString();

       // LogUtil.e(TAG, " error.msg = " + error.msg +", error.info = "+ error.info + ", error.exceptionName = "+error.exceptionName);
        if (ex instanceof HttpException) {
            LogUtil.e(TAG, " excetpion = " + ex.toString());
        }
        else if(ex instanceof UnknownHostException){
            LogUtil.e(TAG, " excetpion = " + ex.toString());
            //Toast.makeText(getApplicationContext(), "请检查网络连接并重试", Toast.LENGTH_SHORT).show();
            error.localReason = "请检查网络连接并重试";
        }
        else if(ex instanceof ConnectException){
            LogUtil.e(TAG, " excetpion = " + ex.toString());
            error.localReason = "请检查网络连接并重试";
        }
        else if (ex instanceof SocketTimeoutException){
            LogUtil.e(TAG, " excetpion = " + ex.toString());
            error.localReason = "与服务器连接超时，请重试";
        }

        this.response.onError(error);

//            HttpException httpEx = (HttpException) ex;
//            String responseMsg = httpEx.getMessage();
//            String errorResult = httpEx.getResult();
//            FetchError error = new FetchError();
//            error.errroCode = -1;
//            error.msg = responseMsg;
//            error.info = errorResult;
//            this.response.onError(error);
//            LogUtil.e(TAG, " " + error.msg + error.info);
//        }else if (ex instanceof UnknownHostException){
//
//        }
//        LogUtil.e(TAG, " " + ex.toString());
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
