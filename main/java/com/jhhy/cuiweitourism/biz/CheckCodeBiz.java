package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.utils.Consts;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/3.
 */
public class CheckCodeBiz {

    private Context context;
    private Handler handler;
    private String CODE_GET_CHECK_CODE = "User_code";

//    {"head":{"code":"User_code"},"field":{"tel":"15210656911"}}
    public CheckCodeBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public void getCheckCode(final String mobile){
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_GET_CHECK_CODE);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_TEL, mobile);
                HttpUtils.executeXutils(headMap, fieldMap, checkCodeCallback);
            }
        }.start();
    }

    private ResponseResult checkCodeCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_CHECK_CODE;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
//    {"head":{"res_code":"0001","res_msg":"error","res_arg":"信息发送失败"},"body":[]}
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//{"head":{"res_code":"0001","res_msg":"success","res_arg":"信息发送成功"},"body":{"field":"[]"}}
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

}
