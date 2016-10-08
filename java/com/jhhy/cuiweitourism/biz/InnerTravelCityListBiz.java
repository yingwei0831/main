package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/14.
 * 国内游列表页
 */
public class InnerTravelCityListBiz {

    private Context context;
    private Handler handler;
    private int requestMessage;

    public InnerTravelCityListBiz(Context context, Handler handler, int requestMessage) {
        this.context = context;
        this.handler = handler;
        this.requestMessage = requestMessage;
    }

    private String CODE_INNER_TRAVEL_CITY = "Publics_classifylist";


//    {"head":{"code":"Publics_classifylist"},"field":{
// "areaid":"8","order":"storeprice desc","day":"5","price":"0,10000","zcfdate":"2016-8-18","wcfdate":"2016-8-18","page":"1","offset":"10","attr":"1"}}
    /**
     * 获取热门目的地列表（国内游，出境游）
     * @param areaId    选择城市的id （国内、国外）
     * @param sort      排序方式        排序：为空（按时间） 1（价格降序），2（价格升序）
     * @param day       行程天数
     * @param price     价格筛选
     * @param earlyTime 最早出发日期
     * @param laterTime 最晚出发日期
     * @param page      页数
     * @param attr      1：跟团游，2：自由游
     */
    public void getCityList(final String areaId, final String sort, final String day, final String price,
                            final String earlyTime, final String laterTime, final String page, final String attr){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_INNER_TRAVEL_CITY);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("areaid", areaId);
                fieldMap.put("sort", sort);
                fieldMap.put("day", day);
                fieldMap.put(Consts.KEY_PRICE, price);
                fieldMap.put("zcfdate", earlyTime);
                fieldMap.put("wcfdate", laterTime);
                fieldMap.put(Consts.KEY_PAGE, page);
                fieldMap.put(Consts.KEY_OFFSET, "10");
                fieldMap.put(Consts.KEY_ATTR, attr);
                HttpUtils.executeXutils(headMap, fieldMap, innerCityListCallback);
            }
        }.start();
    }

// {"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[]}
    private ResponseResult innerCityListCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
//            msg.what = Consts.MESSAGE_INNER_CITY_LIST;
            msg.what = requestMessage;
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
                    List<Travel> listDestination = new ArrayList<>();
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[{"id":"13","title":"这是测试的线路这是测试的线路这是测试的线路","price":"2000","litpic":"http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0607\/f1a7e360b659d4dbf6f40e133ffe5d20.jpg","txt":"跟团游","group":"东"},{"id":"12","title":"北京5日游","price":"5000","litpic":"http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0607\/9329dff285092f3874aed5e814d437c3.jpg","txt":"跟团游","group":"东"}]}
                    if(bodyAry != null) {
                        for (int i = 0; i < bodyAry.length(); i++) {
                            JSONObject travelObj = bodyAry.getJSONObject(i);
                            Travel travel = new Travel();
                            travel.setId(travelObj.getString(Consts.KEY_ID));
                            travel.setTravelTitle(travelObj.getString(Consts.KEY_TITLE));
                            travel.setTravelPrice(travelObj.getString(Consts.KEY_PRICE));
                            travel.setTravelIconPath(travelObj.getString(Consts.PIC_PATH_LITPIC));
                            travel.setType(travelObj.getString("txt"));
                            travel.setGroup(travelObj.getString(Consts.KEY_GROUP));
                            listDestination.add(travel);
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
