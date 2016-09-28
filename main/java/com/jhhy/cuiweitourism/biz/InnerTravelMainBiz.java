package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseCallback;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.HotDestination;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.moudle.UserMessage;
import com.jhhy.cuiweitourism.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/12.
 */
public class InnerTravelMainBiz {

    private Context context;
    private Handler handler;
    private int message;

    public InnerTravelMainBiz(Context context, Handler handler, int message) {
        this.context = context;
        this.handler = handler;
        this.message = message;
    }

    private String CODE_INNER_TRAVEL_DATA = "Publics_lines"; //国内游，出境游——>跟团游，自由游

//    {"head":{"code":"Publics_lines"},"field":{"type":"1","attr":"1","page":"1","offset":"10"}}

    /**
     * 底下列表
     * @param type 1国内游、2出境游(默认跟团游)
     * @param attr attr(属性):1（跟团游）、142（自由游）
     * @param page
     */
    public void getInnerTravelData(final String type, final String attr, final String page){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_INNER_TRAVEL_DATA);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_TYPE, type);
                fieldMap.put("attr", attr);
                fieldMap.put(Consts.KEY_PAGE, page);
                fieldMap.put(Consts.KEY_OFFSET, "10");

                HttpUtils.executeXutils(headMap, fieldMap, innerTravelDataCallback);
            }
        }.start();
    }

    private ResponseResult innerTravelDataCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
//            msg.what = Consts.MESSAGE_INNER_TRAVEL_DATA;
            msg.what = message;
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
                    List<Travel> listTravel = new ArrayList<>();
                    if(bodyAry != null) {
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject travelObj = bodyAry.getJSONObject(i);
                            Travel travel = new Travel();
                            travel.setId(travelObj.getString(Consts.ORDER_ID));
                            travel.setTravelTitle(travelObj.getString(Consts.KEY_TITLE));
                            travel.setTravelIconPath(travelObj.getString(Consts.PIC_PATH_LITPIC));
                            travel.setTravelPrice(travelObj.getString(Consts.KEY_PRICE));
                            travel.setGroup(travelObj.getString(Consts.KEY_GROUP));
                            listTravel.add(travel);
                        }
                    }
                    msg.obj = listTravel;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

//    {"head":{"code":"Publics_hotarea"},"field":{"pid":"1"}}

    private String CODE_INNER_TRAVEL_HOT_DESTINATION = "Publics_hotarea";

    /**
     * 国内游、出境游通用 ——> 热门目的地
     * @param pid 1：国内游 2：出境游
     */
    public void getHotDestination(final String pid){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_INNER_TRAVEL_HOT_DESTINATION);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("pid", pid);
                HttpUtils.executeXutils(headMap, fieldMap, hotDestinationCallback);
            }
        }.start();
    }

    private ResponseResult hotDestinationCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
//            msg.what = Consts.MESSAGE_INNER_TRAVEL_HOT_DESTINATION;
            msg.what = message;
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
                    List<HotDestination> listDestination = new ArrayList<>();
                    if(bodyAry != null) {
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject destObj = bodyAry.getJSONObject(i);
                            HotDestination dest = new HotDestination(
                                    destObj.getString(Consts.KEY_ID),
                                    destObj.getString(Consts.KEY_KIND_NAME));
                            listDestination.add(dest);
                        }
                    }
                    msg.obj = listDestination;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

}
