package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityComment;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotDetailInfo;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/10/10.
 */
public class HotActivityBiz {

    private Context context;
    private Handler handler;

    public HotActivityBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    private String CODE_HOT_ACTIVITY_DETAILL = "Activity_hotpublishshow";

    //    {"head":{"code":"Activity_hotpublishshow"},"field":{"id":"1"}}
    //热门活动详情
    public void getHotActivityDetail(final String id){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_HOT_ACTIVITY_DETAILL);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("id", id);
                HttpUtils.executeXutils(headMap, fieldMap, detailCallback);
            }
        }.start();
    }

    public ResponseResult detailCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_HOT_ACTIVITY_DETAIL;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
//                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                    msg.obj = resArg;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
                    JSONObject bodyObj = resultObj.getJSONObject(Consts.KEY_BODY);
//                    "id": "1",
//                    "title": "撒哈拉沙漠",
//                    "price": "50000.00",
//                    "num": "15",
//                    "days": "5",
//                    "cftime": "1472515200",
//                    "cfcity": "20",
//                    "feeinclude": "1.往返飞机票 2.餐饮 3.保险 4.导游 5.住宿",
//                    "features": "撒哈拉沙漠约形成于250万年前，是世界第二大荒漠（仅次于南极洲），也是世界最大的沙质荒漠。它位于非洲北部，该地区气候条件非常恶劣，是地球上最不适合生物生存的地方之一。其总面积约容得下整个美国本土。“撒哈拉”是阿拉伯语的音译，在阿拉伯语中“撒哈拉”为大沙漠，源自当地游牧民族图阿雷格人的语言，原意即为“大荒漠”。",
//                    "xlxq": "位于阿特拉斯山脉和地中海以南(约北纬35°线),约北纬14°线(250毫米等雨量线)以北。
//                              撒哈拉沙漠西从大西洋沿岸开始，北部以阿特拉斯山脉和地中海为界，东部直抵红海，南部到达苏丹和尼日尔河河谷交界的萨赫勒——一个半沙漠乾草原的过渡区。
//                              撒哈拉沙漠是世界最大的沙漠，几乎占满非洲北部全部，占全洲总面积的25%。沙漠东西约长4,800公里(3000英里)，南北在1300公里至1900公里(800至1200英里)之间，总面积约9065000平方公里。
//                              横贯非洲大陆北部,东西长达5600公里,南北宽约1600公里，约占非洲总面积32%",
//                    "xcinfo": "第一天：从北京出发 第二天：到达目的地安排住宿 第三天：开始进军沙漠 第四天:进军沙漠 第五天：返回北京",
//                    "piclist": [
//                                  "http://www.cwly1118.com/uploads/2015/0909/6576cb3bfd961a0e13e6cc203b4ce012.jpg"
//                              ],
//                    "comment": {
//                                  "content": "这个活动很好",
//                                  "pllist": [
//                                            "http://www.cwly1118.com/uploads/pinglun/2016/0905/e5fd08e12911719eb8cee0983dc55392.jpg",
//                                            "http://www.cwly1118.com/uploads/pinglun/2016/0905"
//                                            ],
//                                  "nickname": "小蜗",
//                                  "face": "http://www.cwly1118.com/uploads/member/11474192836.jpg",
//                                  "addtime": null
//                              },
//                    "ctnum": "1"
                    ActivityHotDetailInfo info = new ActivityHotDetailInfo();
                    info.setId(bodyObj.getString(Consts.KEY_ID));
                    info.setTitle(bodyObj.getString(Consts.KEY_TITLE));
                    info.setPrice(bodyObj.getString(Consts.KEY_PRICE));
                    info.setNum(bodyObj.getString("num"));
                    info.setDays(bodyObj.getString("days"));
                    info.setCftime(bodyObj.getString("cftime"));
                    info.setCfcity(bodyObj.getString("cfcity"));
                    info.setFeeinclude(bodyObj.getString("feeinclude"));
                    info.setFeatures(bodyObj.getString("features"));
                    info.setXlxq(bodyObj.getString("xlxq"));
                    info.setXcinfo(bodyObj.getString("xcinfo"));
                    JSONArray picListAry = bodyObj.getJSONArray("piclist");
                    ArrayList<String> picList = null;
                    if (picListAry != null && picListAry.length() > 0) {
                        picList = new ArrayList<>();
                        for (int i = 0; i < picListAry.length(); i++) {
                            String pic = picListAry.getString(i);
                            picList.add(pic);
                        }
                    }
                    info.setPiclist(picList);
                    JSONObject commentObj = bodyObj.getJSONObject("comment");
                    ActivityComment comment = new ActivityComment();
                    comment.setContent(commentObj.getString("content"));
                    JSONArray plListAry = commentObj.getJSONArray("pllist");
                    ArrayList<String> plList = null;
                    if (plListAry != null && plListAry.length() > 0) {
                        plList = new ArrayList<>();
                        for (int i = 0; i < plListAry.length(); i++) {
                            plList.add(plListAry.getString(i));
                        }
                    }
                    comment.setPllist(plList);
                    comment.setNickname(commentObj.getString("nickname"));
                    comment.setFace(commentObj.getString("face"));
                    comment.setAddtime(commentObj.getString("addtime") == null ? "" : commentObj.getString("addtime"));
                    info.setComment(comment);

                    info.setDays(bodyObj.getString("ctnum"));
                    LogUtil.e("HotActivityBiz", "info = " + info.toString());
                    msg.obj = info;
                }
            } catch (JSONException e) {
                msg.arg1 = -1;
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };
}
