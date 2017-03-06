package com.jhhy.cuiweitourism.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelCommentImageGridAdapter;
import com.jhhy.cuiweitourism.biz.InnerTravelDetailBiz;
import com.jhhy.cuiweitourism.biz.UserCollectionBiz;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.model.ADInfo;
import com.jhhy.cuiweitourism.model.TravelDetail;
import com.jhhy.cuiweitourism.model.TravelDetailDay;
import com.jhhy.cuiweitourism.model.User;
import com.jhhy.cuiweitourism.model.UserComment;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.easemob.EasemobLoginActivity;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.MyFileUtils;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.CircleImageView;
import com.jhhy.cuiweitourism.view.InnerTravelDetailDescribeView;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class InnerTravelDetailActivity extends BaseActionBarActivity implements GestureDetector.OnGestureListener, XScrollView.IXScrollViewListener, View.OnClickListener, AdapterView.OnItemClickListener, View.OnTouchListener {

    private String TAG = "InnerTravelDetailActivity";

    private XScrollView mScrollView;
    private View content;

    private LinearLayout layoutTravelDetailBottom; //底部控件
    private RadioButton rbCollection; //收藏
    private TextView tvShare; //分享
    private Button btnArgument; //讨价还价
    private Button btnReserve; //立即预定
    //顶部导航栏
    private LinearLayout layoutIndicatorTop;
    private int indicatorHeightTop; //顶部导航栏高度
    private Button btnProductTop;
    private Button btnPriceTop;
    private Button btnDescribeTop;
    private Button btnNoticeTop;
    private View viewProductTop;
    private View viewPriceTop;
    private View viewDescribeTop;
    private View viewNoticeTop;
    //Scrollview中导航栏
    private LinearLayout layoutIndicatorBottom;
    private Button tvProduct;
    private Button btnPrice;
    private Button tvDescribe;
    private Button tvNotice;
    private View viewProduct;
    private View viewPrice;
    private View viewDescribe;
    private View viewNotice;

    private LinearLayout layoutComment; //评论
    private WebView mWebViewProduct; //商品详情
    private LinearLayout layoutPrice; //费用说明
    private TextView tvStandard; //费用说明——>标准
    private TextView tvPriceNotInclude; //费用说明——>费用不包含
    private LinearLayout layoutTravelDescribe; //行程描述
    private LinearLayout layoutReserveNotice; //预订须知
    private TextView tvReserveNotice; //预订须知

    private TextView tvTravelTitle;
    private TextView tvPrice; //价格
    private TextView tvCommentCount;
    private CircleImageView civIcon;
    private TextView tvNickName;
    private TextView tvAddTime;
    private TextView tvCommentContent;
    private MyGridView gvCommentImages; //评论中的图片
    private InnerTravelCommentImageGridAdapter imageAdapter; //评论中的图片适配器
    private List<String> listImages = new ArrayList<>();

    private boolean click; //是否是点击indicator

//    private String[] titles = new String[]{"线路特色", "行程安排", "标准", "预订须知"};

//    private String jsonString = "\"<div class=\\\"para\\\" label-module=\\\"para\\\" style=\\\"font-size: 14px; word-wrap: break-word; color: rgb(51, 51, 51); margin-bottom: 15px; text-indent: 28px; line-height: 24px; zoom: 1; font-family: arial, 宋体, sans-serif; white-space: normal; background-color: rgb(255, 255, 255);\\\">颐和园，中国<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/subview/5405/13482963.htm\\\" data-lemmaid=\\\"175141\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">清朝</a>时期<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/646836.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">皇家园林</a>，前身为<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/1476143.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">清漪园</a>，坐落在北京西郊，距城区十五公里，占地约二百九十公顷，与<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/subview/2476/5824630.htm\\\" data-lemmaid=\\\"9328\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">圆明园</a>毗邻。它是以<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/188034.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">昆明湖</a>、<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/956201.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">万寿山</a>为基址，以杭州<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/1598.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">西湖</a>为蓝本，汲取江南园林的设计手法而建成的一座大型山水园林，也是保存最完整的一座皇家行宫御苑，被誉为“皇家园林博物馆”，也是国家重点旅游景点。</div><div class=\\\"para\\\" label-module=\\\"para\\\" style=\\\"font-size: 14px; word-wrap: break-word; color: rgb(51, 51, 51); margin-bottom: 15px; text-indent: 28px; line-height: 24px; zoom: 1; font-family: arial, 宋体, sans-serif; white-space: normal; background-color: rgb(255, 255, 255);\\\">清朝<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/2436.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">乾隆皇帝</a>继位以前，在北京西郊一带，建起了四座大型<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/646836.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">皇家园林</a>。<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/2677.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">乾隆</a>十五年（1750年），乾隆皇帝为孝敬其母孝圣皇后动用448万两白银在这里改建为清漪园，形成了从现<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/32477.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">清华园</a>到香山长达二十公里的皇家园林区。<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/84502.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">咸丰</a>十年（1860年），清漪园被<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/586244.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">英法联军</a>焚毁。<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/29852.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">光绪</a>十四年（1888年）重建，改称颐和园，作消夏游乐地。光绪二十六年（1900年），颐和园又遭“八国联军”的破坏，珍宝被劫掠一空。清朝灭亡后，颐和园在军阀混战和国民党统治时期，又遭破坏。</div><div class=\\\"para\\\" label-module=\\\"para\\\" style=\\\"font-size: 14px; word-wrap: break-word; color: rgb(51, 51, 51); margin-bottom: 15px; text-indent: 28px; line-height: 24px; zoom: 1; font-family: arial, 宋体, sans-serif; white-space: normal; background-color: rgb(255, 255, 255);\\\">1961年3月4日，颐和园被公布为第一批<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/163959.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">全国重点文物保护单位</a>，与同时公布的<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/15110.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">承德避暑山庄</a>、<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/4131.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">拙政园</a>、<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/23789.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">留园</a>并称为<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/400248.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">中国四大名园</a>，1998年11月被列入《<a target=\\\"_blank\\\" href=\\\"http://baike.baidu.com/view/340391.htm\\\" style=\\\"color: rgb(19, 110, 194); text-decoration: none;\\\">世界遗产名录</a>》。2007年5月8日，颐和园经国家旅游局正式批准为国家5A级旅游景区。 2009年，颐和园入选中国世界纪录协会中国现存最大的皇家园林。<span style=\\\"font-size: 12px; line-height: 0; position: relative; vertical-align: baseline; top: -0.5em; margin-left: 2px; color: rgb(51, 102, 204); cursor: default; padding: 0px 2px;\\\">[1]</span><a style=\\\"color: rgb(19, 110, 194); position: relative; top: -50px; font-size: 0px; line-height: 0;\\\" name=\\\"ref_[1]_9572580\\\"></a> </div><p><br/></p>\"";

//    private ScrollView scrollView;

    private String id; //旅游详情的id
    private TravelDetail detail; //用来接收获取到的旅游详情

    //顶部图片展示
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private ViewFlipper flipper;
    private LinearLayout layoutPoint;
    private List<String> imageUrls = new ArrayList<>();
    private ImageView[] indicators; // 轮播图片数组
    private int currentPosition = 0; // 轮播当前位置

    private static final int FLING_MIN_DISTANCE = 20;
    private static final int FLING_MIN_VELOCITY = 0;
    private GestureDetector mGestureDetector; // MyScrollView的手势

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                switch (msg.what) {
                    case Consts.MESSAGE_INNER_TRAVEL_DETAIL:
                        TravelDetail detailNew = (TravelDetail) msg.obj;
                        if (detailNew != null) {
                            detail = detailNew;
                            LogUtil.e(TAG, "线路详情："+detail);
                            refreshView();
                        }
                        break;
                    case Consts.MESSAGE_DO_COLLECTION: //收藏
                        ToastUtil.show(getApplicationContext(), String.valueOf(msg.obj));
                        if ("1".equals(detail.getIscollect())){
                            rbCollection.setChecked(false);
                            detail.setIscollect("0");
                        } else {
                            rbCollection.setChecked(true);
                            detail.setIscollect("1");
                        }
                        break;
                    case Consts.NET_ERROR:
                        ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                        break;
                    case Consts.NET_ERROR_SOCKET_TIMEOUT:
                        ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                        break;
                }
            } else {
                ToastUtil.show(getApplicationContext(), (String) msg.obj);
            }
            LoadingIndicator.cancel();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_inner_travel_detail);
        getData();
        super.onCreate(savedInstanceState); //TODO
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setupView();
//        addListener();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        InnerTravelDetailBiz biz = new InnerTravelDetailBiz(getApplicationContext(), handler);
        String mid = "";
        if (MainActivity.logged){
            mid = MainActivity.user.getUserId();
        }
        biz.getInnerTravelDetail(id, mid);
        LoadingIndicator.show(InnerTravelDetailActivity.this, getString(R.string.http_notice));

        imageUrls.add("drawable://" + R.drawable.ic_empty);
    }

//    private void setupView() { //TODO

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.title_inner_travel_detail));
        ivTitleRight.setImageResource(R.mipmap.icon_telephone_hollow);

        mScrollView = (XScrollView) findViewById(R.id.scroll_view_detail);
        mScrollView.setPullRefreshEnable(false);
        mScrollView.setPullLoadEnable(false);
        mScrollView.setAutoLoadEnable(false);
        mScrollView.setIXScrollViewListener(this);
//        mScrollView.setRefreshTime(Utils.getCurrentTime());

        layoutIndicatorTop = (LinearLayout) findViewById(R.id.layout_inner_travel_main_indicator_top);
        layoutIndicatorTop.setVisibility(View.GONE);

        btnProductTop = (Button) findViewById(R.id.btn_inner_travel_detail_indicator_top_product);
        btnPriceTop = (Button) findViewById(R.id.btn_inner_travel_detail_indicator_top_price);
        btnDescribeTop = (Button) findViewById(R.id.btn_inner_travel_detail_indicator_top_describe);
        btnNoticeTop = (Button) findViewById(R.id.btn_inner_travel_detail_indicator_top_notice);
        viewProductTop = findViewById(R.id.view_inner_travel_detail_indicator_top_product);
        viewPriceTop = findViewById(R.id.view_inner_travel_detail_indicator_top_price);
        viewDescribeTop = findViewById(R.id.view_inner_travel_detail_indicator_top_describe);
        viewNoticeTop = findViewById(R.id.view_inner_travel_detail_indicator_top_notice);

        layoutTravelDetailBottom = (LinearLayout) findViewById(R.id.layout_travel_detail_bottom);
        rbCollection = (RadioButton) findViewById(R.id.rb_inner_travel_collection);
        tvShare = (TextView) findViewById(R.id.tv_inner_travel_share);
        btnArgument = (Button) findViewById(R.id.btn_inner_travel_argument);
        btnReserve = (Button) findViewById(R.id.btn_inner_travel_reserve);


        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_inner_travel_detail_content, null);
        if (null != content) {
            layoutIndicatorBottom = (LinearLayout) content.findViewById(R.id.layout_inner_travel_main_detail_bottom); //底部GridView的导航栏
            tvProduct = (Button) content.findViewById(R.id.tv_inner_travel_detail_content_product_bottom);
            btnPrice = (Button) content.findViewById(R.id.tv_inner_travel_detail_content_price_bottom);
            tvDescribe = (Button) content.findViewById(R.id.tv_inner_travel_detail_content_describe_bottom);
            tvNotice = (Button) content.findViewById(R.id.tv_inner_travel_detail_content_notice_bottom);

            viewProduct = content.findViewById(R.id.view_product_bottom);
            viewPrice = content.findViewById(R.id.view_price_bottom);
            viewDescribe = content.findViewById(R.id.view_describe_bottom);
            viewNotice = content.findViewById(R.id.view_notice_bottom);

            tvTravelTitle = (TextView) content.findViewById(R.id.tv_travel_detail_title);
            tvPrice = (TextView) content.findViewById(R.id.tv_travel_price);
            layoutComment = (LinearLayout) content.findViewById(R.id.layout_comment);
            tvCommentCount = (TextView) content.findViewById(R.id.tv_travel_comment_count);
            civIcon = (CircleImageView) content.findViewById(R.id.inner_travel_detail_comment_user_icon);
            tvNickName = (TextView) content.findViewById(R.id.inner_travel_detail_comment_username);
            tvAddTime = (TextView) content.findViewById(R.id.tv_travel_comment_add_time);
            tvCommentContent = (TextView) content.findViewById(R.id.tv_comment_content);
            gvCommentImages = (MyGridView) content.findViewById(R.id.inner_travel_detail_gridview); //评论中的图片

            mWebViewProduct = (WebView) content.findViewById(R.id.webview_inner_travel_detail_content_product);
            layoutPrice = (LinearLayout) content.findViewById(R.id.layout_travel_price);
            tvStandard = (TextView) content.findViewById(R.id.tv_travel_price_include);
            tvPriceNotInclude = (TextView) content.findViewById(R.id.tv_travel_price_not_include);
            layoutTravelDescribe = (LinearLayout) content.findViewById(R.id.layout_travel_describe);
            layoutReserveNotice = (LinearLayout) content.findViewById(R.id.layout_reserve_notice);
            tvReserveNotice = (TextView) content.findViewById(R.id.tv_travel_notice);

            mGestureDetector = new GestureDetector(getApplicationContext(), this);

            flipper = (ViewFlipper) content.findViewById(R.id.viewflipper);
            layoutPoint = (LinearLayout) content.findViewById(R.id.layout_indicator_point);

            addImageView(imageUrls.size());
            addIndicator(imageUrls.size());
            setIndicator(currentPosition);

            mGestureDetector = new GestureDetector(getApplicationContext(), this);
            flipper.setOnTouchListener(this);

            dianSelect(currentPosition);
            MyScrollView myScrollView = (MyScrollView) content.findViewById(R.id.viewflipper_myScrollview);
            myScrollView.setGestureDetector(mGestureDetector);
        }
        mScrollView.setView(content);

//        int[] bottomAry = new int[2];
//        layoutTravelDetailBottom.getLocationOnScreen(bottomAry);
//        this.bottom = bottomAry[1];
//        LogUtil.e(TAG, "bottom = " + this.bottom);
        /**
         * 此处是为了顶部导航栏的显示和隐藏
         */
        mScrollView.setOnXScrollChangedI(new XScrollView.onXScrollChangedI() {
            @Override
            public void onXScrollChangedImpl(int l, int t, int oldl, int oldt) {
                int barH = actionBar.getHeight();
                //页面中的导航栏
                int[] s = new int[2];
                layoutIndicatorBottom.getLocationOnScreen(s);
                int statusHeight = Utils.getStatusBarHeight(getApplicationContext());
//                int titleHeight = layoutTitle.getHeight();
                indicatorHeightTop = layoutIndicatorTop.getHeight();
                if (s[1] <= statusHeight + barH) { // + titleHeight
                    layoutIndicatorTop.setVisibility(View.VISIBLE);
                } else {
                    layoutIndicatorTop.setVisibility(View.GONE);
                }
                if (!click) {
                    //商品详情——>线路特写
                    int[] detailAry = new int[2];
                    mWebViewProduct.getLocationOnScreen(detailAry);
                    if (detailAry[1] <= statusHeight + barH) {
                        changeIndicator(1);
                    }

                    //行程描述——>标准
                    int[] describe = new int[2];
                    layoutTravelDescribe.getLocationOnScreen(describe);
                    if (describe[1] <= statusHeight + indicatorHeightTop + barH) {
                        changeIndicator(3);
                    }

                    //费用说明——>行程安排
                    int[] priceAry = new int[2];
                    layoutPrice.getLocationOnScreen(priceAry);
                    if (priceAry[1] <= statusHeight + indicatorHeightTop + barH) {
                        changeIndicator(2);
                    }

                    //预订须知
                    int[] reserve = new int[2];
                    layoutReserveNotice.getLocationOnScreen(reserve);
                    if (reserve[1] <= statusHeight + indicatorHeightTop + barH) {
                        changeIndicator(4);
                    }
                }
            }
        });
        imageAdapter = new InnerTravelCommentImageGridAdapter(getApplicationContext(), listImages);
        gvCommentImages.setAdapter(imageAdapter);
    }


    private void refreshView() {
        String start = "<html><style>body img{width:100%;}</style><body>";
        String start2 = "<html><style></style><body>";
        String end = "</body></html>";
        //TODO 顶部图片
        List<String> picAddr = detail.getPictureList();
        List<ADInfo> newADInfos = new ArrayList<>();
        imageUrls.clear();
        for (int i = 0; i < picAddr.size(); i++) {
            imageUrls.add(i, picAddr.get(i));
            ADInfo adInfo = new ADInfo();
            adInfo.setUrl(picAddr.get(i));
            newADInfos.add(adInfo);
        }
        updateBinner(newADInfos);
        //Title
        String title = detail.getTitle();
        tvTravelTitle.setText(title);
        //Price
        String price = detail.getPrice();
        tvPrice.setText(price);
        //comment count
        String count = detail.getCommentCount();
        tvCommentCount.setText("累计评价（" + count + "）");
        //TODO comment, 头像
        UserComment comment = detail.getComment();
        if (comment != null) {
            setIconData(comment.getUserIcon());
            tvNickName.setText(comment.getNickName());
            tvAddTime.setText(comment.getCommentTime());
            tvCommentContent.setText(comment.getContent());
            listImages = comment.getPicList();
            imageAdapter.setData(listImages);
            imageAdapter.notifyDataSetChanged();
        } else {
            layoutComment.setVisibility(View.GONE);
        }
        //商品详情——>线路特色
        String tripDetail = detail.getLineDetails();
        mWebViewProduct.loadDataWithBaseURL(null, start + tripDetail + end, "text/html", "utf-8", null);
        //费用说明——>标准
//        String priceContain = detail.getPriceInclude();
//        String priceNotContain = detail.getPriceNotContain();
        tvStandard.setText(Html.fromHtml(start2 + detail.getStandard() + end));
//        tvPriceNotInclude.setText(priceNotContain);
        //行程描述——>行程安排
        List<TravelDetailDay> tripDescribe = detail.getTripDescribe();
        if (tripDescribe != null && tripDescribe.size() > 0) {
            for (int i = 0; i < tripDescribe.size(); i++) {
                TravelDetailDay tripDay = tripDescribe.get(i);
                InnerTravelDetailDescribeView viewStep = new InnerTravelDetailDescribeView(getApplicationContext(), handler);
                if (i == 0) {
                    viewStep.isFirstStep(true);
                }
                if (i == tripDescribe.size() - 1) {
                    viewStep.isLastStep(true);
                }
                viewStep.setTvDayText(String.format(Locale.getDefault(), "第%s天", tripDay.getDay()));
                viewStep.setTvTitleText(tripDay.getTitle());
                viewStep.setTvContent(tripDay.getDetail());
                layoutTravelDescribe.addView(viewStep);
            }
        }
        //预订须知
        tvReserveNotice.setText(Html.fromHtml(start2 + detail.getRemark() + end));
        rbCollection.setChecked("1".equals(detail.getIscollect()));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view); //TODO

        switch (view.getId()) {
            case R.id.title_main_iv_right_telephone: //电话
                if (detail == null || TextUtils.isEmpty(detail.getTel())){
                    ToastUtil.show(getApplicationContext(), "该商户未上传电话信息");
                }else{
                    //弹窗显示400电话
                    showTelephoneNumber();
                }
                break;
            case R.id.tv_inner_travel_detail_content_product_bottom:
            case R.id.btn_inner_travel_detail_indicator_top_product:
                click = true;
                changeIndicator(1);
                mWebViewProduct.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.scrollTo(0, mWebViewProduct.getTop() - indicatorHeightTop);
                        click = false;
                    }
                });
                break;
            case R.id.tv_inner_travel_detail_content_price_bottom:
            case R.id.btn_inner_travel_detail_indicator_top_price:
                click = true;
                changeIndicator(3);
                layoutTravelDescribe.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.scrollTo(0, layoutTravelDescribe.getTop() - indicatorHeightTop);
                        click = false;
                    }
                });
                break;
            case R.id.tv_inner_travel_detail_content_describe_bottom:
            case R.id.btn_inner_travel_detail_indicator_top_describe:
                click = true;
                changeIndicator(2);
                layoutPrice.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.scrollTo(0, layoutPrice.getTop() - indicatorHeightTop);
                        click = false;
                    }
                });
                break;
            case R.id.tv_inner_travel_detail_content_notice_bottom:
            case R.id.btn_inner_travel_detail_indicator_top_notice:
                click = true;
                layoutReserveNotice.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.scrollTo(0, layoutReserveNotice.getTop() - indicatorHeightTop);
                        click = false;
                    }
                });
                changeIndicator(4);
                break;
            case R.id.tv_travel_comment_count: //累计评价
                //TODO 进入评价详情页面
                Intent intentComment = new Intent(getApplicationContext(), CommentAllActivity.class);
                Bundle bundleComment = new Bundle();
                bundleComment.putString("articleId", detail.getId());
                bundleComment.putString("type", "1");
                intentComment.putExtras(bundleComment);
                startActivity(intentComment);
                break;
            case R.id.rb_inner_travel_collection: //收藏
                doCollection();
                break;
            case R.id.tv_inner_travel_share: //分享
                showShare(getApplicationContext());
                break;
            case R.id.btn_inner_travel_argument: //讨价还价
                if (MainActivity.logged) {
                    Intent intent = new Intent(getApplicationContext(), EasemobLoginActivity.class);
                    String im = detail.getIm();
                    String sjmc = detail.getSjmc();
                    if (im == null || im.length() == 0){
                        ToastUtil.show(getApplicationContext(), "当前商户暂未提供客服功能");
                        return;
                    }
//                    intent.putExtra("name", );
//                    intent.putExtra("icon", );
                    intent.putExtra("im", im);
                    intent.putExtra("sjmc", sjmc);
                    startActivity(intent);
                } else {
                    userLogin();
                }
                break;
            case R.id.btn_inner_travel_reserve: //立即预定
                if (MainActivity.logged) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putSerializable("detail", detail);
                    Intent intent = new Intent(getApplicationContext(), PriceCalendarReserveActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_DATE); //选择日期
//                PriceCalendarReserveActivity.actionStart(getApplicationContext(), bundle);
                } else {
                    userLogin();
                }
                break;
        }
    }

    private void showTelephoneNumber() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(detail.getTel());
        builder.setTitle(getString(R.string.title_telephone));
        builder.setPositiveButton(R.string.tab1_inner_travel_pop_commit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void userLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    private int REQUEST_LOGIN = 2913; //请求登录

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            if (requestCode == REQUEST_LOGIN){
                ToastUtil.show(getApplicationContext(), "登录失败");
            }
        } else {
            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_DATE) { //选择日期
                //TODO 日期选择返回

            }else  if (requestCode == REQUEST_LOGIN) { //登录成功
                User user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    MainActivity.logged = true;
                    MainActivity.user = user;
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO 进入查看大图页面

    }

//    private void addListener() { //TODO

    @Override
    protected void addListener() {
        super.addListener();

        rbCollection.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        btnArgument.setOnClickListener(this);
        btnReserve.setOnClickListener(this);

        tvProduct.setOnClickListener(this);
        btnPrice.setOnClickListener(this);
        tvDescribe.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        btnProductTop.setOnClickListener(this);
        btnPriceTop.setOnClickListener(this);
        btnDescribeTop.setOnClickListener(this);
        btnNoticeTop.setOnClickListener(this);
        tvCommentCount.setOnClickListener(this);

        gvCommentImages.setOnItemClickListener(this);
        mWebViewProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     *
     * @param motionEvent
     * @param motionEvent1
     * @param v
     * @param v1
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     *
     * @param motionEvent
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * GestureDetector.OnGestureListener 回调
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY) {
//            Log.i(TAG, "==============开始向左滑动了================");
            showNextView();
            return true;
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY) {
//            Log.i(TAG, "==============开始向右滑动了================");
            showPreviousView();
            return true;
        } else {
            return false;
        }
    }

    private void addImageView(int length) {
        for (int i = 0; i < length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls.get(i));
            info.setContent("图片-->" + i);
            infos.add(info);
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
    }

    private void addIndicator(int size) {
//        if(indicators == null) {
        indicators = new ImageView[size];
//        }
        layoutPoint.removeAllViews();
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_cycle_viewpager_indicator, null);
            indicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
            layoutPoint.addView(view);
        }

    }

    private void setIndicator(int current) {
        for (int i = 0; i < indicators.length; i++) {
            if (i == current) {
                indicators[current].setImageResource(R.drawable.icon_point_pre);
            } else {
                indicators[i].setImageResource(R.drawable.icon_point);
            }
        }
    }

    /**
     * 对应被选中的点的图片
     *
     * @param id
     */
    private void dianSelect(int id) {
        indicators[id].setImageResource(R.drawable.icon_point_pre);
    }

    /**
     * 对应未被选中的点的图片
     *
     * @param id
     */
    private void dianUnselect(int id) {
        indicators[id].setImageResource(R.drawable.icon_point);
    }

    public void updateBinner(List<ADInfo> listsBinner) {
        infos = listsBinner;
        flipper.removeAllViews();
        for (int i = 0; i < infos.size(); i++) {
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
        addIndicator(infos.size());
        setIndicator(0);
    }

    private void showNextView() {
//        Log.i(TAG, "========showNextView=======向左滑动=======");
        flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_left_out));
        flipper.showNext();
        currentPosition++;
        if (currentPosition == flipper.getChildCount()) {
            dianUnselect(currentPosition - 1);
            currentPosition = 0;
            dianSelect(currentPosition);
        } else {
            dianUnselect(currentPosition - 1);
            dianSelect(currentPosition);
        }
//		Log.i(TAG, "==============第"+currentPage+"页==========");
    }

    private void showPreviousView() {
//        Log.i(TAG, "========showPreviousView=======向右滑动=======");
        dianSelect(currentPosition);
        flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_right_out));
        flipper.showPrevious();
        currentPosition--;
        if (currentPosition == -1) {
            dianUnselect(currentPosition + 1);
            currentPosition = flipper.getChildCount() - 1;
            dianSelect(currentPosition);
        } else {
            dianUnselect(currentPosition + 1);
            dianSelect(currentPosition);
        }
//		Log.i(TAG, "==============第"+currentPage+"页==========");
    }

    private void setIconData(String url) {
        final String subString = url.substring(url.lastIndexOf("/") + 1);
        if (MyFileUtils.fileIsExists(Consts.IMG_PATH + subString)) {
            Bitmap iconBmp = BitmapFactory.decodeFile(Consts.IMG_PATH + subString);
            if (iconBmp != null) {
                civIcon.setImageBitmap(iconBmp);
            }
        } else {
            ImageLoaderUtil imageLoader = ImageLoaderUtil.getInstance(getApplicationContext());
            imageLoader.getImage(civIcon, url);
            imageLoader.setCallBack(new ImageLoaderUtil.ImageLoaderCallBack() {
                @Override
                public void refreshAdapter(Bitmap loadedImage) {
                    if (loadedImage != null) {
                        civIcon.setImageBitmap(loadedImage);
                    }
                }
            });
        }

    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, InnerTravelDetailActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    private void changeIndicator(int tag) {
        tvProduct.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        viewProduct.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBgIndicator));
        btnProductTop.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        viewProductTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBgIndicator));

        btnPrice.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        viewPrice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBgIndicator));
        btnPriceTop.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        viewPriceTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBgIndicator));

        tvDescribe.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        viewDescribe.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBgIndicator));
        btnDescribeTop.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        viewDescribeTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBgIndicator));

        tvNotice.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        viewNotice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBgIndicator));
        btnNoticeTop.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        viewNoticeTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBgIndicator));
        if (tag == 1) {
            tvProduct.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            viewProduct.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            btnProductTop.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            viewProductTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
        } else if (tag == 2) {
            tvDescribe.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            viewDescribe.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            btnDescribeTop.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            viewDescribeTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
        } else if (tag == 3) {
            btnPrice.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            viewPrice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            btnPriceTop.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            viewPriceTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));

        } else if (tag == 4) {
            tvNotice.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            viewNotice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            btnNoticeTop.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
            viewNoticeTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
        }
    }

    private void doCollection() {
        if (MainActivity.logged) {
            UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
            biz.doCollection(MainActivity.user.getUserId(), "1", detail.getId());
        } else {
//            ToastUtil.show(getApplicationContext(), "请登录后再试");
            userLogin();
            rbCollection.setChecked(false);
        }
    }

    private void showShare(Context context) {
        if (detail == null) return;
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("翠微旅游");
        // titleUrl是标题的网络链接，仅在人人网和QQ和QQ空间使用
        oks.setTitleUrl(Consts.BASE_SHARE_URL + detail.getId());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(detail.getTitle());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(detail.getPictureList().get(0));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(WelcomeActivity.TEST_IMAGE);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(Consts.BASE_SHARE_URL + detail.getId());//http://www.cwly1118.com/service.php?m=test&a=lineshare&id=291
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setText(detail.getTitle() + "\n" + Consts.BASE_SHARE_URL + detail.getId());
                }
            }
        });
        // 启动分享GUI
        oks.show(context);
    }
}
