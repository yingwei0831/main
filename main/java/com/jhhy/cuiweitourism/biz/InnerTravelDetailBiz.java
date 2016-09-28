package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.TravelDetail;
import com.jhhy.cuiweitourism.moudle.TravelDetailDay;
import com.jhhy.cuiweitourism.moudle.UserComment;
import com.jhhy.cuiweitourism.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/1.
 */
public class InnerTravelDetailBiz {

    private Context context;
    private Handler handler;
//    private int messageCode; //国内游/出境游

    private String CODE_TRIP_DETAIL = "Publics_show";

    public InnerTravelDetailBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

//{"head":{"code":"Publics_show"},"field":{"id":"14"}}
    /**
     * 国内游（出境游）详情页
     * @param id 某城市id
     */
    public void getInnerTravelDetail(final String id){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_TRIP_DETAIL);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_ID, id);
                HttpUtils.executeXutils(headMap, fieldMap, travelDetailCallback);
            }
        }.start();
    }

//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":
//{"piclist":["http:\/\/cwly.taskbees.cn:8083"],
// "features":"",
// "price":null,
// "needjifen":null,
// "xcms":null,
// "typeid":1,
// "plcount":"0"}}
    private ResponseResult travelDetailCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_INNER_TRAVEL_DETAIL;
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
                    JSONObject bodyObj = resultObj.getJSONObject(Consts.KEY_BODY);
                    TravelDetail detail = null;
                    if (bodyObj != null){
                        detail = new TravelDetail();
                        detail.setId(bodyObj.getString(Consts.KEY_ID).trim());
                        detail.setTitle(bodyObj.getString(Consts.KEY_TITLE).trim());
                        detail.setPrice(bodyObj.getString(Consts.KEY_PRICE).trim());
                        detail.setRemark(bodyObj.getString("beizu").trim());
                        detail.setPriceInclude(bodyObj.getString("feeinclude").trim());
                        detail.setFeatures(bodyObj.getString("features").trim());
                        detail.setTransport(bodyObj.getString("transport").trim());
                        detail.setLineDetails(bodyObj.getString("xlxq").trim());
                        detail.setPriceNotContain(bodyObj.getString("fybubh").trim());
                        JSONArray picListAry = bodyObj.getJSONArray("piclist");
                        List<String> picList = null;
                        if (picListAry != null){
                            picList = new ArrayList<>();
                            for (int i = 0; i < picListAry.length(); i++){
                                picList.add(picListAry.getString(i).trim());
                            }
                        }
                        detail.setPictureList(picList);
                        detail.setBusinessId(bodyObj.getString("supplierlist").trim());
                        detail.setNeedScore(bodyObj.getString("needjifen").trim());
                        List<TravelDetailDay> tripDetail = null;
                        JSONArray tripDetailAry = bodyObj.getJSONArray("xcms");
                        if (tripDetailAry != null && tripDetailAry.length() != 0){
                            tripDetail = new ArrayList<>();
                            for (int i = 0; i < tripDetailAry.length(); i++){
                                JSONObject tripDayObj = tripDetailAry.getJSONObject(i);
                                TravelDetailDay tripDay = new TravelDetailDay();
                                tripDay.setDay(tripDayObj.getString("day").trim());
                                tripDay.setTitle(tripDayObj.getString(Consts.KEY_TITLE).trim());
                                tripDay.setDetail(tripDayObj.getString("jieshao").trim());
                                tripDetail.add(tripDay);
                            }
                        }
                        detail.setTripDescribe(tripDetail);
                        JSONObject commentObj = bodyObj.getJSONObject("comment");
                        UserComment userComment = null;
                        if (commentObj != null){
                            userComment = new UserComment();
                            userComment.setNickName(commentObj.getString(Consts.KEY_USER_NICK_NAME).trim());
                            userComment.setUserIcon(commentObj.getString("face").trim());
                            userComment.setCommentTime(commentObj.getString(Consts.ORDER_ADD_TIME).trim());
                            userComment.setContent(commentObj.getString("content").trim());
                            List<String> commentPicList = null;
                            JSONArray comPicListAry = commentObj.getJSONArray("pllist");
                            if (comPicListAry != null && comPicListAry.length() != 0){
                                commentPicList = new ArrayList<>();
                                for (int i = 0; i < comPicListAry.length(); i++){
                                    commentPicList.add(comPicListAry.getString(i).trim());
                                }
                            }
                            userComment.setPicList(commentPicList);
                        }
                        detail.setComment(userComment);
                        detail.setTypeId(bodyObj.getString("typeid").trim());
                        detail.setCommentCount(bodyObj.getString("plcount").trim());
                    }
                    msg.obj = detail;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
