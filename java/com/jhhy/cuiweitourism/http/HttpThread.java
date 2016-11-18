package com.jhhy.cuiweitourism.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.Map;

/**
 * Created by jiahe008 on 2016/4/19.
 */
public class HttpThread extends Thread {

    private static final String TAG = HttpThread.class.getSimpleName();
    private Context context;
    private Map<String, String> headMap;
    private Map<String, String> fieldMap;
    private ResponseResult callback;
    private Handler handler;

    public HttpThread(Context context, Map<String, String> headMap, Map<String, String> fieldMap, ResponseResult callback, Handler handler){
        this.context = context;
        this.headMap = headMap;
        this.fieldMap = fieldMap;
        this.callback = callback;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        //检查网络连接情况
        if(!NetworkUtil.checkNetwork(context)){
            Message msg = new Message();
            msg.what = Consts.MESSAGE_NETWORK_ERROR;
            handler.sendMessage(msg);
            return;
        }
        //配置参数
        org.xutils.http.RequestParams param = new org.xutils.http.RequestParams(Consts.SERVER_URL);
        param.addHeader("Content-Type", "application/json;charset=utf-8");
        JSONObject json = new JSONObject();

        try {
            json.put(Consts.KEY_HEAD, setFieldParams(headMap));
            json.put(Consts.KEY_FIELD, setFieldParams(fieldMap));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        param.setBodyContent(json.toString());
        LogUtil.i(TAG, "发送数据：" + param.getBodyContent());
        x.http().post(param, callback);
    }

    private static JSONObject setFieldParams(Map<String, String> map) {
        JSONObject fieldObj = new JSONObject();
        if(null == map) return fieldObj;
        try {
            for(String key : map.keySet()){
                fieldObj.put(key, map.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fieldObj;
    }
}
