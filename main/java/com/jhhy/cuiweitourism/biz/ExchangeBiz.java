package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.utils.Consts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by birney1 on 16/9/12.
 */
public class ExchangeBiz {

    private Context context;
    private Handler handler;

    private String CODE_EXCHANGE = "Publics_huanyihuan";

    public ExchangeBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

//    {"head":{"code":"Publics_huanyihuan"},"field":[]}
    public void getHotRecommend(){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_EXCHANGE);
                HttpUtils.executeXutils(headMap, null, exchageCallback);
            }
        }.start();
    }

    private ResponseResult exchageCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_EXCHANGE;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                    msg.obj = resArg;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<CityRecommend> listRecom = new ArrayList<>();
                    if (bodyAry != null && bodyAry.length() != 0){
                        for (int i = 0; i < bodyAry.length(); i ++) {
                            JSONObject recomObj = bodyAry.getJSONObject(i);
                            CityRecommend recom = new CityRecommend(
                                    recomObj.getString(Consts.KEY_ID),
                                    recomObj.getString(Consts.KEY_KIND_NAME),
                                    recomObj.getString(Consts.PIC_PATH_LITPIC)
                            );
                            listRecom.add(recom);
                        }
                    }
                    msg.obj = listRecom;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                handler.sendMessage(msg);
            }
        }
    };
}
