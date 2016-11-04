package com.jhhy.cuiweitourism.net.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by jiahe008 on 2016/6/21.
 */
public class Consts {

//    public static final String SERVER_URL = "http://cwly.taskbees.cn:8083/service.php";
//    public static final String SERVER_URL = "http://www.cwly1118.com";
    public static final String SERVER_URL = "http://www.cwly1118.com/service.php";


    public static final int NET_ERROR = 9999; //网络发生故障
    public static final int NET_ERROR_SOCKET_TIMEOUT = 9991; //与服务器链接超时
    public static final int JSON_PARSE_ERROR = 9992; //json解析异常

    public static final int TIME_PERIOD = 4000; //轮播图片时间间隔

    public static final String IMG_PATH = Environment.getExternalStorageDirectory()+ File.separator+"cuiweiTourism/";

    public static final String IMG_TEMP_PATH = Environment.getExternalStorageDirectory()+ File.separator+"cuiweiTemp/"; //上传图片用，传完删除
    public static final int IMAGE_COUNT = 4; //上传图片用

    public static final String KEY_INNER_CITY_NAME = "key_inner_city_name"; //国内游

    public static final int MESSAGE_NETWORK_ERROR = -1; //没有可用的网络
    public static final String KEY_RESULT_DATA = "result_data";

    public static final String KEY_HEAD = "head";
    public static final String KEY_FIELD = "field";
    public static final String KEY_BODY = "body";
    public static final String KEY_CODE = "code";

    //服务器返回信息
    public static final String KEY_HTTP_RES_CODE = "res_code";
    public static final String KEY_HTTP_RES_MSG = "res_msg";
    public static final String KEY_HTTP_RES_ARG = "res_arg";

    public static final String KEY_REQUEST = "request_data"; //开启Activity携带数据
    public static final String KEY_RESULT = "request_data"; //关闭Activity携带数据

//    登录
    public static final int MESSAGE_LOGIN = 110;

    //注册返回
    public static final String KEY_USER_USER_MID = "mid";
    public static final String KEY_USER_NICK_NAME = "nickname";
    public static final String KEY_USER_GENDER = "sex";
    public static final String KEY_USER_ICON_PATH = "avatar";
    public static final String KEY_USER_TRUE_NAME = "truename";
    public static final String KEY_USER_CARD_ID = "cardid";
    public static final String KEY_USER_SCORE = "jifen";
//    public static final String KEY_USER_USER_TYPE = "login_type";//第三方等录或本平台登录

    //注册
    public static final int MESSAGE_REGISTER = 111;
    public static final int REQUEST_CODE_REGISTER = 1011; //请求码

    public static final String KEY_USER_MOBILE = "mobile";//手机号码
    public static final String KEY_PASSWORD = "password";//密码

    public static final String KEY_REGISTER_CODE = "codes";//注册码

    //分类
    public static final int MESSAGE_CLASSIFY = 115; //分类，国内游
    public static final int MESSAGE_CLASSIFY_OUT = 1151; //分类，出境游
    public static final int MESSAGE_CLASSIFY_NEAR = 1152; //分类，周边游
    public static final String KEY_KIND_NAME = "kindname";
    public static final String KEY_SON = "son";

//    获取验证码
    public static final int MESSAGE_CHECK_CODE = 112;
    public static final String KEY_TEL = "tel";//获取验证码

    public static final String KEY_CHECK_CODE = "verify";//验证码
    public static final String KEY_ID = "id";//注册成功返回数据

    public static final String SP_NAME = "cuiwei_tourism_sp_name"; //sp名字
    public static final String SP_LOGIN_NAME = "cuiwei_tourism_login_name"; //用户名
    public static final String SP_LOGIN_PASSWORD = "cuiwei_tourism_login_pwd"; //密码


//    首页
    public static final int MESSAGE_TAB1_RECOMMEND = 114;
    public static final int MESSAGE_TAB1_RECOMMEND_INNER = 1141;
    public static final int MESSAGE_TAB1_RECOMMEND_OUTSIDE = 1142;
    public static final int MESSAGE_TAB1_RECOMMEND_NEARBY = 1143;
    public static final String KEY_TYPE = "type"; //type:1国内游、2出境游
    public static final String TAB1_RECOMMEND_AREA = "area"; //周边游area:地区(北京)
    public static final String KEY_TITLE = "title"; //旅游title
    public static final String KEY_GROUP = "group"; //环信使用

    //    找线路
    public static final int MESSAGE_FIND_LINES = 117;
    public static final int MESSAGE_FIND_SHOP = 152; //找商铺
    public static final int MESSAGE_FIND_SHOP_LINE_LIST = 155; //找商铺——>该商铺所有线路列表
    public static final String KEY_PAGE = "page";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_ATTR = "attr"; //属性

    // 发起活动
    public static final int MESSAGE_START_ACTIVITY = 118;

//    热门活动
    public static final int MESSAGE_HOT_ACTIVITY_DETAIL = 156; //热门活动详情

    //国内游
    public static final int MESSAGE_INNER_TRAVEL_HOT_DESTINATION = 119; //国内游，出境游——>热门目的地
//    public static final int MESSAGE_INNER_TRAVEL_DATA = 120; //国内游，出境游——>跟团游，自由游
    public static final int MESSAGE_INNER_FOLLOW = 120; //国内游，出境游——>跟团游
    public static final int MESSAGE_INNER_FREEDOM = 121; //国内游，出境游——>自由游
    public static final int MESSAGE_INNER_CITY_LIST_FOLLOW = 123; //国内游，国内游热门目的地城市列表页——>跟团游
    public static final int MESSAGE_INNER_CITY_LIST_FREEDOM = 124; //国内游，国内游热门目的地城市列表页——>自由游

    public static final int MESSAGE_TRIP_DAYS = 125; //获取天数筛选
    public static final int MESSAGE_TRIP_PRICE = 126; //获取价格筛选
    public static final int MESSAGE_CALENDAR_PRICE = 127; //获取价格日历

//    签证
    public static final int MESSAGE_VISA_HOT_COUNTRY = 144; //获取热门签证国家
    public static final int MESSAGE_VISA_HOT = 147; //热门签证
    public static final int MESSAGE_VISA_MORE_COUNTRY = 145; //查看全部签证国家
    public static final int MESSAGE_VISA_HOT_COUNTRY_LIST = 148; //热门签证国家——>签证列表
    public static final int MESSAGE_VISA_DETAIL = 146; //签证详情
    public static final int MESSAGE_VISA_SEND_MATERIAL = 149; //发送材料至我的邮箱
    public static final int MESSAGE_VISA_RESERVE = 150; //立即预定 签证
    public static final int MESSAGE_VISA_TYPE = 153; //签证类型
    public static final int MESSAGE_VISA_CITY = 154; //签证城市


//  换一换
    public static final int MESSAGE_EXCHANGE = 128;

//    国内游详情页
    public static final int MESSAGE_INNER_TRAVEL_DETAIL = 129;

    //常用联系人
    public static final int MESSAGE_GET_CONTACTS = 140; //获取联系人列表
    public static final int MESSAGE_ADD_CONTACTS = 141; //添加联系人列表
    public static final int MESSAGE_MODIFY_CONTACTS = 142; //修改联系人列表
    public static final int MESSAGE_DELETE_CONTACTS = 143; //删除联系人列表
    public static final int REQUEST_CODE_EDIT_CONTACT = 1401; //编辑联系人信息
    public static final int REQUEST_CODE_ADD_CONTACT = 1402; //添加联系人信息
    public static final int REQUEST_CODE_GET_ICONS = 1403; //获取我的旅游必
    public static final int MESSAGE_MODIFY_USER_ICON = 1404; //修改头像
    public static final int MESSAGE_MODIFY_USER_INFO = 1405; //修改昵称，性别
    public static final int MESSAGE_MODIFY_USER_MOBILE = 1406; //修改手机号
    public static final int MESSAGE_MODIFY_USER_PASSWORD = 1407; //修改登录密码
    public static final int MESSAGE_MODIFY_USER_AUTHENTICATION = 1408; //账户认证 authentication

    //我的收藏
    public static final int MESSAGE_GET_MY_COLLECTION = 1409; //我的收藏
    public static final int MESSAGE_DO_COLLECTION = 1411; //收藏
    public static final int MESSAGE_GET_MY_RELEASE = 1410; //我的发布

    //    全部订单
    public static final int MESSAGE_ORDERS_ALL = 113; //全部订单
    public static final int MESSAGE_ORDERS_WAIT_PAY = 1131;
    public static final int MESSAGE_ORDERS_WAIT_COMMENT = 1132;
    public static final int MESSAGE_ORDERS_WAIT_REFUND = 1133;
    public static final int MESSAGE_ORDER_COMMIT = 1134; //提交订单
    public static final int MESSAGE_ORDER_DETAIL = 1135; //订单详情
    public static final int MESSAGE_ORDER_REFUND = 1136; //申请退款
    public static final int MESSAGE_ORDER_CANCEL_REFUND = 1137; //取消退款
    public static final int MESSAGE_ORDER_COMMENT = 1138; //取消退款
    public static final int MESSAGE_ORDER_CANCEL = 1139; //取消订单

    public static final String ACTION_ORDER_UPDATE = "jhhy.intent.action.UPDATE_ORDER"; //订单更新广播

    //支付宝
    public static final int MESSAGE_PAY_ALI = 1215; //

    public static final String ORDER_TYPE = "type";  //
    public static final String ORDER_PRODUCT_NAME = "productname";
    public static final String ORDER_ADD_TIME = "addtime";
    public static final String ORDER_STATUS = "status";
    public static final String ORDER_IS_COMMENT = "ispinlun";
    public static final String ORDER_ID = "id";
    public static final String KEY_PRICE = "price";
    public static final String PIC_PATH_LITPIC = "litpic";

//    我的消息
    public static final int MESSAGE_USER_MESSAGE = 116;
    public static final int MESSAGE_USER_COMMENT = 151;
    public static final String MSG_MEMBER_ID = "memberid";
    public static final String MSG_COMMENT = "pl"; //评论


//    环信
    public static final int MESSAGE_TO_DEFAULT = 0;
    public static final String MESSAGE_TO_INTENT_EXTRA = "message_to";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String INTENT_CODE_IMG_SELECTED_KEY = "img_selected";
    public static final int INTENT_CODE_IMG_SELECTED_DEFAULT = 0;
    public static final String MESSAGE_ATTR_MSGTYPE = "msgtype";
    public static final String WEICHAT_MSG = "weichat";
    public static final int MESSAGE_TO_PRE_SALES = 1;
    public static final int MESSAGE_TO_AFTER_SALES = 2;
//    public static final String DEFAULT_COSTOMER_APPKEY = "yingwei0831#cuiweitourism";
//    public static final String DEFAULT_COSTOMER_ACCOUNT = "15210656918";
//    public static final String DEFAULT_ACCOUNT_PWD = "admin123";
//    public static final long DEFAULT_PROJECT_ID = 48022l;
//    public static final long DEFAULT_TENANT_ID = 35l;

    public static final String EXTRA_USER_ID = "userId";
    public static final String EXTRA_SHOW_USERNICK = "showUserNick";

//    public static final String ORDER_STATUS_WAIT_PAYMENT = "1"; //待付款
//    public static final String ORDER_STATUS_WAIT_REFUND = "0"; //待处理，待退款状态

//    public static final String ORDER_STATUS_WAIT_COMMENT = "0"; //未评论
//    public static final String ORDER_STATUS_WAIT_COMMENT = "1"; //已评论

//    public static final int MSG_SET_TAGS = 1002; //极光推送
//    public static final int MSG_SET_ALIAS = 1001; // 极光推送

    public static final int REQUEST_CODE_DATE_PICKER_EARLY = 1301; //请求日期选择
    public static final int REQUEST_CODE_DATE_PICKER_LATER = 1302; //请求日期选择
    public static final int REQUEST_CODE_RESERVE_SELECT_DATE = 1303; //选择日期
    public static final int REQUEST_CODE_RESERVE_EDIT_ORDER = 1304; //填写订单
    public static final int REQUEST_CODE_RESERVE_SELECT_CONTACT = 1305; //选择常用联系人
    public static final int REQUEST_CODE_RESERVE_SELECT_COIN = 1306; //选择旅游币
    public static final int REQUEST_CODE_RESERVE_SELECT_INVOICE = 1307; //选择是否开发票
    public static final int REQUEST_CODE_RESERVE_PAY = 1308; //订单生成成功，去支付

//    选择城市
    public static final int MESSAGE_GET_CITY = 130; //获取城市列表
    public static final int REQUEST_CODE_SELECT_CITY = 1130; //选择城市
    //数据库
    /** Database version */
    public static final int DATABASE_VERSION = 1;
    /** Database file name */
    public static final String DATABASE_NAME = "cuiwei_tourism.db";
    /**
     * 火车表
     */
    public static final String TABLE_TRAIN_STATION = "cuiwei_tourism_station";
    public static final String TABLE_CITY_RECORD = "cuiwei_city_record";
    //定位
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final int AMAP_ZOOM_LEVEL = 15; //高德地图缩放级别3-19，默认10
    public static final int MESSAGE_LOCATION = 157; //定位成功
    public static final int MESSAGE_LOCATION_FAILED = 158; //定位失败，网络
    public static final int MESSAGE_LOCATION_FAILED_PERMISSION_DENIED = 159; //定位失败，权限被禁止
    public static final int MESSAGE_NETWORK_CONNECT = 160;//网络已链接
}