package com.jhhy.cuiweitourism.net.netcallback;

import com.jhhy.cuiweitourism.net.models.FetchModel.BasicFetchModel;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.x;

import java.util.Map;

/**
 * Created by jiahe008 on 2016/4/8.
 */
public class HttpUtils {

    private static final String TAG = HttpUtils.class.getSimpleName();

    public static void executeXutils(Map<String, Object> codeMap, Map<String, Object> paramsMap, ResponseResult callback) {
        //配置参数
        org.xutils.http.RequestParams param = new org.xutils.http.RequestParams(Consts.SERVER_URL);
        param.addHeader("Content-Type", "application/json;charset=utf-8");
        JSONObject json = new JSONObject();

        try {

            json.put(Consts.KEY_HEAD, setFieldParams(codeMap));
            json.put(Consts.KEY_FIELD, setFieldParams(paramsMap));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String entityString = json.toString();
        param.setBodyContent(entityString);
        LogUtil.e(TAG, "send Data：" + param.getBodyContent());
        x.http().post(param, callback);
    }


    public static void executeXutils(BasicFetchModel arg, Callback.CommonCallback<String> callback){
        org.xutils.http.RequestParams param = new org.xutils.http.RequestParams(Consts.SERVER_URL);
        param.addHeader("Content-Type", "application/json;charset=utf-8");
        param.setBodyContent(arg.toBizJsonString());
        LogUtil.e(TAG, "发送数据：" + arg.toBizJsonString());
        x.http().post(param, callback);
    }


    private static JSONObject setFieldParams(Map<String, Object> map) {
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
