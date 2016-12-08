package com.jhhy.cuiweitourism.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;
import com.jhhy.cuiweitourism.http.ResponseResult;
import com.jhhy.cuiweitourism.model.CityRecommend;
import com.jhhy.cuiweitourism.model.ClassifyArea;
import com.jhhy.cuiweitourism.model.Invoice;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.model.PhoneBean;
import com.jhhy.cuiweitourism.model.VisaDetail;
import com.jhhy.cuiweitourism.model.VisaHotCountry;
import com.jhhy.cuiweitourism.model.VisaHotCountryCity;
import com.jhhy.cuiweitourism.model.VisaType;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiahe008 on 2016/9/26.
 */
public class VisaBiz {

    private String TAG = VisaBiz.class.getSimpleName();

    private Context context;
    private Handler handler;

    public VisaBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    //    {"head":{"code":"Visa_index"},"field":[]}
    private String CODE_HOT_VISA_COUNTRY = "Visa_index";

    //热门签证国家(顶部)
    public void getHotCountry() {
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_HOT_VISA_COUNTRY);
                Map<String, Object> fieldMap = new HashMap<>();
                HttpUtils.executeXutils(headMap, fieldMap, hotCountryCallback);
            }
        }.start();
    }

    //国家，签证——>列表
//    {"head":{"code":"Visa_visalist"},"field":{"nationid":"6","order":"1","type":"6","qfcity":"1"}}
// 排序（order）：1 按价格升序 2 按价格降序 ""默认排序，   类型：传相应的id
    private String CODE_GET_COUNTRY_LIST = "Visa_visalist";

    public void getCountryList(final String nationId, final String order, final String type, final String fromCity) {
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_GET_COUNTRY_LIST);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put("nationid", nationId);
                fieldMap.put("order", order);
                fieldMap.put("type", type);
                fieldMap.put("qfcity", fromCity);
                HttpUtils.executeXutils(headMap, fieldMap, countryListCallback);
            }
        }.start();
    }

//    {"head":{"code":"Visa_type"},"field":[]}
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[{"id":"6","kindname":"旅游签证"},{"id":"7","kindname":"商务签证"},{"id":"8","kindname":"留学签证"},{"id":"9","kindname":"探亲签证"}]}
    //签证类型
    private String CODE_VISA_TYPE = "Visa_type";
    public void getVisaType(){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_VISA_TYPE);
                Map<String, Object> fieldMap = new HashMap<>();
                HttpUtils.executeXutils(headMap, fieldMap, visaTypeCallBack);
            }
        }.start();
    }

//    {"head":{"code":"Visa_qfcity"},"field":[]}
    //签证城市
    private String CODE_VISA_CITY = "Visa_qfcity";
    public void getVisaCity(){
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_VISA_CITY);
                Map<String, Object> fieldMap = new HashMap<>();
                HttpUtils.executeXutils(headMap, fieldMap, visaCityCallBack);
            }
        }.start();
    }

    private ResponseResult visaCityCallBack = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_CITY;
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
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[{"id":"2","kindname":"上海"},{"id":"3","kindname":"广州"}]}
                    List<PhoneBean> phoneBeanList = new ArrayList<>();
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    for (int i = 0; i < bodyAry.length(); i++) {
                        JSONObject beanObj = bodyAry.getJSONObject(i);
                        PhoneBean phoneBean = new PhoneBean();
                        phoneBean.setCity_id(beanObj.getString(Consts.KEY_ID));
                        phoneBean.setName(beanObj.getString(Consts.KEY_KIND_NAME));
                        phoneBeanList.add(phoneBean);
                    }
                    msg.obj = phoneBeanList;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult visaTypeCallBack = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_TYPE;
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
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":[{"id":"6","kindname":"旅游签证"},{"id":"9","kindname":"探亲签证"}]}
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<VisaType> listVisas = new ArrayList<>();
                    for (int i = 0; i < bodyAry.length(); i++){
                        JSONObject visaTypeObj= bodyAry.getJSONObject(i);
                        VisaType visaType = new VisaType(visaTypeObj.getString(Consts.KEY_ID), visaTypeObj.getString(Consts.KEY_KIND_NAME));
                        listVisas.add(visaType);
                    }
                    msg.obj = listVisas;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };


//    {"head":{"code":"Visa_hotvisa"},"field":[]}
    //热门签证（底部）
    private String CODE_HOT_VISA = "Visa_hotvisa";

    public void getHotVisa() {
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_HOT_VISA);
                Map<String, Object> fieldMap = new HashMap<>();
                HttpUtils.executeXutils(headMap, fieldMap, hotVisaCallBack);
            }
        }.start();
    }

//    {"head":{"code":"Visa_more"},"field":[]}

    //查看全部热门签证和国家——>二级列表
    private String CODE_ALL_AREA_COUNTRY = "Visa_more";

    public void getAllHotCountry() {
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_ALL_AREA_COUNTRY);
                Map<String, Object> fieldMap = new HashMap<>();
                HttpUtils.executeXutils(headMap, fieldMap, allHotCountryCallback);
            }
        }.start();
    }

    //    {"head":{"code":"Visa_visashow"},"field":{"id":"1"}}
    //签证详细页
    private String CODE_VISA_DETAIL = "Visa_visashow";

    public void getVisaDetail(final String id) {
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_VISA_DETAIL);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_ID, id);
                HttpUtils.executeXutils(headMap, fieldMap, visaDetailCallback);
            }
        }.start();
    }

    //TODO 发送材料至我的邮箱
    private String CODE_DELIVER_MATERIAL = "";

    public void doSentMaterialToMyMail(final String id) {
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> headMap = new HashMap<>();
                headMap.put(Consts.KEY_CODE, CODE_DELIVER_MATERIAL);
                Map<String, Object> fieldMap = new HashMap<>();
                fieldMap.put(Consts.KEY_ID, id);
                HttpUtils.executeXutils(headMap, fieldMap, sentMaterialCallback);
            }
        }.start();
    }

    //立即预定
//{"head":{"code":"Order_visaorder"},"field":{
// "memberid":"6","productaid":"1","productname":"韩国旅游签证","price":"300","usedate":"2016-10-1","dingnum":"2",
// "linkman":"张三","linktel":"1520656911","linkemail":"A@a.com"}}

// "memberid":"6","productaid":"1","productname":"韩国旅游签证","price":"300","usedate":"2016-10-1","dingnum":"2",
// "linkman":"张三","linktel":"1520656911","linkemail":"A@a.com",
// "isneedpiao":"1","piao":{"title":"北京发票","receiver":"王二麻子","mobile":"13895784597","address":"北京市昌平区"},
// "usejifen":"1","needjifen":"10","jifentprice":"5"}}
    private String CODE_RESERVE_VISA = "Order_visaorder";
    public void doReserveVisa(final String mid, final String productId, final String productName, final String price, final String useDate, final String number,
                              final String linkName, final String linkTel, final String linkMail,
                              final String isneedpiao, final Invoice invoice,
                              final String usejifen, final String needjifen, final String jifentprice){
        new Thread() {
            @Override
            public void run() {
                JSONObject headObj = new JSONObject();

                JSONObject fieldObj = new JSONObject();
                try {
                    headObj.put(Consts.KEY_CODE, CODE_RESERVE_VISA);

                    fieldObj.put("memberid", mid);
                    fieldObj.put("productaid", productId);
                    fieldObj.put("productname", productName);
                    fieldObj.put(Consts.KEY_PRICE, price);
                    fieldObj.put("usedate", useDate);
                    fieldObj.put("dingnum", number);
                    fieldObj.put("linkman", linkName);
                    fieldObj.put("linktel", linkTel);
                    fieldObj.put("linkemail", linkMail);
                    if ("1".equals(isneedpiao)){
                        HashMap<String, String> invoiceMap = new HashMap<>();
                        invoiceMap.put("title", invoice.getTitle());
                        invoiceMap.put("receiver", invoice.getReceiver());
                        invoiceMap.put("mobile", invoice.getMobile());
                        invoiceMap.put("address", invoice.getAddress());
                        JSONObject invoiceObj = new JSONObject(invoiceMap);
                        fieldObj.put("fapiao", invoiceObj);
                    }else{
                        fieldObj.put("fapiao", null);
                    }
                    fieldObj.put("isneedpiao", isneedpiao);
                    fieldObj.put("usejifen", usejifen);
                    fieldObj.put("needjifen", needjifen);
                    fieldObj.put("jifentprice", jifentprice);
                }catch (Exception e){
                    e.printStackTrace();
                }

                org.xutils.http.RequestParams param = new org.xutils.http.RequestParams(Consts.SERVER_URL);
                param.addHeader("Content-Type", "application/json;charset=utf-8");
                JSONObject json = new JSONObject();

                try {
                    json.put(Consts.KEY_HEAD, headObj);
                    json.put(Consts.KEY_FIELD, fieldObj);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String entityString = json.toString();
                param.setBodyContent(entityString);
                LogUtil.e(TAG, "发送数据：" + param.getBodyContent());
                x.http().post(param, doReserveVisaCallback);
            }
        }.start();
    }
    private ResponseResult doReserveVisaCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_RESERVE;
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
// {"head":{"res_code":"0000","res_msg":"success","res_arg":"订单添加成功"},"body":{"ordersn":"08315884285274","productname":"韩国旅游签证","price":595}}
                    JSONObject bodyObj = resultObj.getJSONObject(Consts.KEY_BODY);
                    Order order = new Order();
                    order.setOrderSN(bodyObj.getString("ordersn"));
                    order.setProductName(bodyObj.getString("productname"));
                    order.setPrice(bodyObj.getString(Consts.KEY_PRICE));
                    msg.obj = order;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult sentMaterialCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_SEND_MATERIAL;
            try {
                JSONObject resultObj = new JSONObject(result);
                String head = resultObj.getString(Consts.KEY_HEAD);
                JSONObject headObj = new JSONObject(head);
                String resCode = headObj.getString(Consts.KEY_HTTP_RES_CODE);
//                String resMsg = headObj.getString(Consts.KEY_HTTP_RES_MSG);
                String resArg = headObj.getString(Consts.KEY_HTTP_RES_ARG);
                if ("0001".equals(resCode)) {
                    msg.arg1 = 0;
                } else if ("0000".equals(resCode)) {
                    msg.arg1 = 1;
//                    msg.obj = listCountry;
                }
                msg.obj = resArg;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult countryListCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_HOT_COUNTRY_LIST;
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
//                    {
//                        "id": "3",
//                        "cityid": "1",
//                        "title": "韩国旅游签证",
//                        "handleday": "预计15个工作日",
//                        "price": "300",
//                        "cityname": "北京"
//                    }
                    List<VisaHotCountryCity> listCountry = new ArrayList<>();
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    if (bodyAry != null && bodyAry.length() != 0) {
                        for (int i = 0; i < bodyAry.length(); i++) {
                            VisaHotCountryCity hot = new VisaHotCountryCity();
                            JSONObject hotObj = bodyAry.getJSONObject(i);
                            hot.setId(hotObj.getString(Consts.KEY_ID));
                            hot.setCityID(hotObj.getString("cityid"));
                            hot.setTitle(hotObj.getString(Consts.KEY_TITLE));
                            hot.setHandleDay(hotObj.getString("handleday"));
                            hot.setPrice(hotObj.getString(Consts.KEY_PRICE));
                            hot.setCityName(hotObj.getString("cityname"));
                            listCountry.add(hot);
                        }
                    }
                    msg.obj = listCountry;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult visaDetailCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_DETAIL;
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
                    VisaDetail visaDetail = new VisaDetail();
                    JSONObject bodyObj = resultObj.getJSONObject(Consts.KEY_BODY);
                    visaDetail.setId(bodyObj.getString(Consts.KEY_ID));
                    visaDetail.setTitle(bodyObj.getString(Consts.KEY_TITLE));
                    visaDetail.setLitPic(bodyObj.getString(Consts.PIC_PATH_LITPIC));
                    visaDetail.setPrice(bodyObj.getString(Consts.KEY_PRICE));
                    visaDetail.setHandleday(bodyObj.getString("handleday"));
                    visaDetail.setVisatype(bodyObj.getString("visatype"));
                    visaDetail.setBelongconsulate(bodyObj.getString("belongconsulate"));
                    visaDetail.setPartday(bodyObj.getString("partday"));
                    visaDetail.setValidday(bodyObj.getString("validday"));
                    visaDetail.setNeedinterview(bodyObj.getString("needinterview"));
                    visaDetail.setNeedletter(bodyObj.getString("needletter"));
                    visaDetail.setHandlerange(bodyObj.getString("handlerange"));
                    visaDetail.setContent(bodyObj.getString("content").trim().replace(" ", ""));
                    visaDetail.setMaterial1(bodyObj.getString("material1"));
                    visaDetail.setMaterial2(bodyObj.getString("material2"));
                    visaDetail.setMaterial3(bodyObj.getString("material3"));
                    visaDetail.setMaterial4(bodyObj.getString("material4"));
                    visaDetail.setMaterial5(bodyObj.getString("material5"));
                    visaDetail.setMaterial5(bodyObj.getString("material5"));
                    visaDetail.setBookingtip(bodyObj.getString("bookingtip"));
                    visaDetail.setFriendtip(bodyObj.getString("friendtip"));
                    visaDetail.setJifentprice(bodyObj.getString("jifentprice"));
                    msg.obj = visaDetail;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult hotVisaCallBack = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_HOT;
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
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    List<VisaHotCountry> listHotVisa = null;
                    if (bodyAry != null && bodyAry.length() != 0) {
                        listHotVisa = new ArrayList<>();
                        for (int i = 0; i < bodyAry.length(); i++) {
                            VisaHotCountry visaHotCountry = new VisaHotCountry();
                            JSONObject visaHotObj = bodyAry.getJSONObject(i);
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":
//[{"id":"1","title":"新加坡旅游签证[北京领区]","litpic":"http:\/\/cwly.taskbees.cn:8083\/uploads\/2015\/0910\/176e08735a00fdb7c0feb376b7630003.jpg","price":"300"}]}
                            visaHotCountry.setId(visaHotObj.getString(Consts.KEY_ID));
                            visaHotCountry.setTitle(visaHotObj.getString(Consts.KEY_TITLE));
                            visaHotCountry.setLitpic(visaHotObj.getString(Consts.PIC_PATH_LITPIC));
                            visaHotCountry.setPrice(visaHotObj.getString(Consts.KEY_PRICE));
                            listHotVisa.add(visaHotCountry);
                        }
                    }
                    msg.obj = listHotVisa;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

    private ResponseResult allHotCountryCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            LogUtil.e(TAG, "请求成功，返回数据: " + result);
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_MORE_COUNTRY;
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
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":
// [{"id":"1","kindname":"亚洲","gj":[{"id":"4","kindname":"新加坡"},{"id":"6","kindname":"韩国"}]},
// {"id":"2","kindname":"欧洲","gj":[{"id":"5","kindname":"英国"}]},{"id":"3","kindname":"美洲"}]}
                    List<ClassifyArea> hotCountries; //地区集合
                    JSONArray bodyAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    if (bodyAry != null && bodyAry.length() != 0) {
                        hotCountries = new ArrayList<>();
                        for (int i = 0; i < bodyAry.length(); i++) {
                            ClassifyArea countryArea = new ClassifyArea(); //地区
                            JSONObject counObj = bodyAry.getJSONObject(i);
                            countryArea.setAreaId(counObj.getString(Consts.KEY_ID));
                            countryArea.setAreaName(counObj.getString(Consts.KEY_KIND_NAME));
                            JSONArray countryAry = counObj.getJSONArray("gj");
                            if (countryAry != null && countryAry.length() != 0) {
                                List<ClassifyArea> listSon = new ArrayList<>(); //国家集合
                                for (int j = 0; j < countryAry.length(); j++) {
                                    JSONObject areaObj = countryAry.getJSONObject(j);
                                    ClassifyArea area = new ClassifyArea(); //国家
                                    area.setAreaId(areaObj.getString(Consts.KEY_ID));
                                    area.setAreaName(areaObj.getString(Consts.KEY_KIND_NAME));
                                    listSon.add(area);
                                }
                                countryArea.setListProvince(listSon); //地区下面的国家集合
                            }
                            hotCountries.add(countryArea); //地区集合
                        }
                        msg.obj = hotCountries;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };


    private ResponseResult hotCountryCallback = new ResponseResult() {
        @Override
        public void responseSuccess(String result) {
            Message msg = new Message();
            msg.what = Consts.MESSAGE_VISA_HOT_COUNTRY;
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
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},"body":
// [{"litpic":"http:\/\/cwly.taskbees.cn:8083\/uploads\/main\/allimg\/20150925\/20150925132538.jpg","kindname":"新加坡","id":"4"}]}
                    List<CityRecommend> hotCountries = new ArrayList<>();
                    JSONArray objAry = resultObj.getJSONArray(Consts.KEY_BODY);
                    for (int i = 0; i < objAry.length(); i++) {
                        CityRecommend country = new CityRecommend();
                        JSONObject counObj = objAry.getJSONObject(i);
                        country.setCityId(counObj.getString(Consts.KEY_ID));
                        country.setCityName(counObj.getString("kindname"));
                        country.setCityImage(counObj.getString(Consts.PIC_PATH_LITPIC));
                        hotCountries.add(country);
                    }
                    msg.obj = hotCountries;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                handler.sendMessage(msg);
            }
        }
    };

}
