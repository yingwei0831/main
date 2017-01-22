package com.jhhy.cuiweitourism.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Gravity;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.model.ADInfo;
import com.jhhy.cuiweitourism.net.biz.ActivityActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HomePageCustonDetail;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityComment;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.MyFileUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.CircleImageView;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

public class HotActivityDetailActivity extends BaseActionBarActivity implements GestureDetector.OnGestureListener, XScrollView.IXScrollViewListener, View.OnClickListener, AdapterView.OnItemClickListener, View.OnTouchListener {

    private String TAG = "HotActivityDetailActivity";

    private XScrollView mScrollView;
    private View content;

    private Button btnReserve; //立即预定
    //顶部导航栏
    private LinearLayout layoutIndicatorTop;
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

    private WebView mWebViewProduct; //商品详情
    private View layoutPrice;
    private TextView tvPriceInclude; //费用说明——>费用包含
    private TextView tvPriceNotInclude; //费用说明——>费用不包含
    private LinearLayout layoutTravelDescribe; //行程描述
    private TextView tvTravelDescribe; //行程描述
    private View layoutNotice;
    private TextView tvReserveNotice; //预订须知

//    private View layoutTitle;
//    private TextView tvTitle;
    private TextView tvPrice; //价格

    private View layoutComment; //评论大布局
    private TextView tvCommentCount;
    private CircleImageView civIcon;
    private TextView tvNickName;
    private TextView tvAddTime;
    private TextView tvCommentContent;
    private MyGridView gvImages;


    private String[] titles = new String[]{"商品详情", "费用说明", "行程描述", "预订须知"};

//    private ScrollView scrollView;

    private String id; //热门活动详情的id
    private ActivityHotDetailInfo detail; //用来接收获取到的旅游详情

    //顶部图片展示
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private ViewFlipper flipper;
    private LinearLayout layoutPoint;
    private List<String> imageUrls = new ArrayList<>();
    private ImageView[] indicators; // 轮播图片数组
    private int currentPosition = 0; // 轮播当前位置

    private final int FLING_MIN_DISTANCE = 20;
    private final int FLING_MIN_VELOCITY = 0;
    private GestureDetector mGestureDetector; // MyScrollView的手势

    private int type; //2:我的发布详情

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e(TAG, "-------handleMessage------ arg1 = " + msg.arg1 + ", msg.what = " + msg.what);
            switch (msg.what) {
                case Consts.MESSAGE_HOT_ACTIVITY_DETAIL:
                    if (msg.arg1 == 0) {
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    } else if (msg.arg1 == 1) {
                        ActivityHotDetailInfo detailHot = (ActivityHotDetailInfo) msg.obj;
                        if (detailHot == null) {
                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取热门详情数据失败，请重试");
                        } else {
                            detail = detailHot;
                            refreshView();
                        }
                    } else if (msg.arg1 == -1) {

                    }
                    break;
            }
            LoadingIndicator.cancel();
        }
    };

//    private TextView tvTitleTop;
//    private ImageView ivTitleLeft;
//    private ImageView ivTitleRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getData();
        setContentView(R.layout.activity_hot_activity_detail);
        super.onCreate(savedInstanceState);
        getInternetData();
//        setupView();
//        addListener();
    }

    /**
     * 热门活动详情
     */
    private void getInternetData() {
//        InnerTravelDetailBiz biz = new InnerTravelDetailBiz(getApplicationContext(), handler);
//        biz.getInnerTravelDetail(id);
        LoadingIndicator.show(HotActivityDetailActivity.this, getString(R.string.http_notice));

        ActivityActionBiz activityBiz = new ActivityActionBiz();
        HomePageCustonDetail detailC = new HomePageCustonDetail(id);
        activityBiz.activitiesHotGetDetailInfo(detailC, new BizGenericCallback<ActivityHotDetailInfo>() {
            @Override
            public void onCompletion(GenericResponseModel<ActivityHotDetailInfo> model) {
                LogUtil.e(TAG, "--------------- onCompletion ---------------");
                if ("0001".equals(model.headModel.res_code)) {
                    ToastUtil.show(getApplicationContext(), "获取热门线路详情失败，请返回重试");
                } else if ("0000".equals(model.headModel.res_code)) {
                    ActivityHotDetailInfo info = model.body;
                    LogUtil.e(TAG, "activitiesHotGetDetailInfo " + info.toString());
                    detail = info;
                    refreshView();
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null) {
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                } else {
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取热门线路详情失败，请重试");
                }
                LogUtil.e(TAG, "activitiesHotGetDetailInfo: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
//        HotActivityBiz biz = new HotActivityBiz(getApplicationContext(), handler);
//        biz.getHotActivityDetail(id);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        type = bundle.getInt("type");
    }

    @Override
    protected void setupView() {
        super.setupView();
        imageUrls.add("drawable://" + R.drawable.ic_empty);
//        layoutTitle = findViewById(R.id.layout_title_inner_travel);
//        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("热门活动详情");
//        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);
//        ivTitleRight = (ImageView) findViewById(R.id.title_main_iv_right_telephone);
        ivTitleRight.setImageResource(R.mipmap.icon_telephone_hollow);
        ivTitleRight.setVisibility(View.VISIBLE);

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

        btnProductTop.setText(titles[0]);
        btnPriceTop.setText(titles[1]);
        btnDescribeTop.setText(titles[2]);
        btnNoticeTop.setText(titles[3]);

        viewProductTop = findViewById(R.id.view_inner_travel_detail_indicator_top_product);
        viewPriceTop = findViewById(R.id.view_inner_travel_detail_indicator_top_price);
        viewDescribeTop = findViewById(R.id.view_inner_travel_detail_indicator_top_describe);
        viewNoticeTop = findViewById(R.id.view_inner_travel_detail_indicator_top_notice);


        btnReserve = (Button) findViewById(R.id.btn_inner_travel_reserve);

        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_hot_activity_detail_content, null);
        if (null != content) {
            layoutIndicatorBottom = (LinearLayout) content.findViewById(R.id.layout_inner_travel_main_detail_bottom); //底部GridView的导航栏
            tvProduct = (Button) content.findViewById(R.id.tv_inner_travel_detail_content_product_bottom);
            btnPrice = (Button) content.findViewById(R.id.tv_inner_travel_detail_content_price_bottom);
            tvDescribe = (Button) content.findViewById(R.id.tv_inner_travel_detail_content_describe_bottom);
            tvNotice = (Button) content.findViewById(R.id.tv_inner_travel_detail_content_notice_bottom);

            tvProduct.setText(titles[0]);
            btnPrice.setText(titles[1]);
            tvDescribe.setText(titles[2]);
            tvNotice.setText(titles[3]);

            viewProduct = content.findViewById(R.id.view_product_bottom);
            viewPrice = content.findViewById(R.id.view_price_bottom);
            viewDescribe = content.findViewById(R.id.view_describe_bottom);
            viewNotice = content.findViewById(R.id.view_notice_bottom);

            tvTitle = (TextView) content.findViewById(R.id.tv_travel_detail_title);
            layoutPrice = content.findViewById(R.id.layout_price_hot_activity);
            tvPrice = (TextView) content.findViewById(R.id.tv_travel_price);
            layoutComment = content.findViewById(R.id.layout_comment);
            tvCommentCount = (TextView) content.findViewById(R.id.tv_travel_comment_count);
            civIcon = (CircleImageView) content.findViewById(R.id.inner_travel_detail_comment_user_icon);
            tvNickName = (TextView) content.findViewById(R.id.inner_travel_detail_comment_username);
            tvAddTime = (TextView) content.findViewById(R.id.tv_travel_comment_add_time);
            tvCommentContent = (TextView) content.findViewById(R.id.tv_comment_content);
            gvImages = (MyGridView) content.findViewById(R.id.inner_travel_detail_gridview);

            mWebViewProduct = (WebView) content.findViewById(R.id.webview_inner_travel_detail_content_product);
            tvPriceInclude = (TextView) content.findViewById(R.id.tv_travel_price_include);
            tvPriceNotInclude = (TextView) content.findViewById(R.id.tv_travel_price_not_include);
            layoutTravelDescribe = (LinearLayout) content.findViewById(R.id.layout_travel_describe);
            tvTravelDescribe = (TextView) content.findViewById(R.id.tv_hot_activity_trip_describe);
            layoutNotice = content.findViewById(R.id.layout_reserve_notice_hot_activity);
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

        /**
         * 此处是为了顶部导航栏的显示和隐藏
         */
        mScrollView.setOnXScrollChangedI(new XScrollView.onXScrollChangedI() {
            @Override
            public void onXScrollChangedImpl(int l, int t, int oldl, int oldt) {
                int[] s = new int[2];
                layoutIndicatorBottom.getLocationOnScreen(s);
                int statusHeight = Utils.getStatusBarHeight(getApplicationContext());
                int titleHeight = getSupportActionBar().getHeight();
                int indicatorHeightTop = layoutIndicatorTop.getHeight();
//                LogUtil.e(TAG, "statusHeight = " + statusHeight + ", titleHeight = " + titleHeight + ", s[1] = " + s[1]);
                if (statusHeight + titleHeight >= s[1]) { // + titleHeight
                    layoutIndicatorTop.setVisibility(View.VISIBLE);
                } else {
                    layoutIndicatorTop.setVisibility(View.GONE);
                }
                //商品详情——>线路特写
                int[] detailAry = new int[2];
                mWebViewProduct.getLocationOnScreen(detailAry);
                if (detailAry[1] <= statusHeight + titleHeight) {
                    changeIndicator(1);
                }

                //费用说明——>行程安排
                int[] priceAry = new int[2];
                layoutPrice.getLocationOnScreen(priceAry);
                if (priceAry[1] <= statusHeight + indicatorHeightTop + titleHeight) {
                    changeIndicator(2);
                }

                //行程描述——>标准
                int[] describe = new int[2];
                layoutTravelDescribe.getLocationOnScreen(describe);
                if (describe[1] <= statusHeight + indicatorHeightTop + titleHeight) {
                    changeIndicator(3);
                }

                //预订须知
                int[] reserve = new int[2];
                layoutNotice.getLocationOnScreen(reserve);
                if (reserve[1] <= statusHeight + indicatorHeightTop + titleHeight) {
                    changeIndicator(4);
                }
            }
        });
        if (type == 2){ //立即预定隐藏
            btnReserve.setVisibility(View.GONE);
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

    private void refreshView() {
        String start = "<html><style>body img{width:100%;}</style><body>";
        String end = "</body></html>";
        //TODO 顶部图片
        List<String> picAddr = detail.getPiclist();
        List<ADInfo> newADInfos = new ArrayList<>();
        if (picAddr != null) {
            imageUrls.clear();
            for (int i = 0; i < picAddr.size(); i++) {
                imageUrls.add(i, picAddr.get(i));
                ADInfo adInfo = new ADInfo();
                adInfo.setUrl(picAddr.get(i));
                newADInfos.add(adInfo);
            }
        }
        updateBinner(newADInfos);
        //Title
        String title = detail.getTitle();
        tvTitle.setText(title);
        //Price
        String price = detail.getPrice();
        tvPrice.setText(price);
        //comment count
        String count = detail.getNum();
        tvCommentCount.setText("累计评价（" + count + "）");
        //TODO comment, 头像
        ActivityComment comment = detail.getComment();
        if (comment != null) {
            setIconData(comment.getFace());
            tvNickName.setText(comment.getNickname());
            String time = comment.getAddtime();
            if (time != null && !"null".equals(time)) {
                tvAddTime.setText(Utils.getTimeStrYMD(Long.parseLong(time) * 1000));
            }
            tvCommentContent.setText(comment.getContent());
        } else {
            layoutComment.setVisibility(View.GONE);
        }
        //商品详情
        String tripDetail = detail.getXlxq();
        mWebViewProduct.loadDataWithBaseURL(null, start + tripDetail + end, "text/html", "utf-8", null);
        //费用说明
        String priceContain = detail.getFeeinclude();
//        String priceNotContain = detail.getPriceNotContain();
        tvPriceInclude.setText(priceContain);
        tvPriceNotInclude.setVisibility(View.GONE);
//        tvPriceNotInclude.setText(priceNotContain);
        //行程描述
        String tripDescribe = detail.getXcinfo();
        tvTravelDescribe.setText(tripDescribe);
        //预订须知
        tvReserveNotice.setText(detail.getYdxz());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.title_main_iv_right_telephone:
                showTelephoneNumber();
                break;
            case R.id.tv_inner_travel_detail_content_product_bottom:
            case R.id.btn_inner_travel_detail_indicator_top_product:
                changeIndicator(1);
                break;
            case R.id.tv_inner_travel_detail_content_price_bottom:
            case R.id.btn_inner_travel_detail_indicator_top_price:
                changeIndicator(2);
                break;
            case R.id.tv_inner_travel_detail_content_describe_bottom:
            case R.id.btn_inner_travel_detail_indicator_top_describe:
                changeIndicator(3);
                break;
            case R.id.tv_inner_travel_detail_content_notice_bottom:
            case R.id.btn_inner_travel_detail_indicator_top_notice:
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
            case R.id.btn_inner_travel_reserve: //立即预定
                //TODO 选择人数
                showSelectNumber();
                break;
            case R.id.tv_price_calendar_number_reduce: //人数减少
                if (number <= 1){
                    return;
                }
                number --;
                popShow();
                break;
            case R.id.tv_price_calendar_number_add: //人数增加
                number ++;
                popShow();
                break;
            case R.id.btn_hot_activity_reserve: //下一步填写订单
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putSerializable("hotActivityDetail", detail);
                bundle.putInt("type", 11);
                bundle.putInt("count", number);
                Intent intent = new Intent(getApplicationContext(), HotActivityEditOrderActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Consts.HOT_ACTIVITY_EDIT_ORDER); //热门活动，生成订单
                break;
        }
    }

    private void showTelephoneNumber() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("400-900-5665");
        builder.setTitle(getString(R.string.title_telephone));
        builder.setPositiveButton(R.string.tab1_inner_travel_pop_commit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private PopupWindow popupWindow;

    private TextView tvNumber;
    private int number = 1;

    private void showSelectNumber() {
        if (popupWindow == null) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_hot_select_number, null);
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            // 允许点击外部消失
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            setupPopView(view);
        }
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            popupWindow.showAtLocation(btnReserve, Gravity.NO_GRAVITY,0, 0);
        }
        popShow();
    }

    private void popShow() {
        tvNumber.setText(String.valueOf(number));
    }

    private void setupPopView(View view) {
        ImageView ivReduce = (ImageView) view.findViewById(R.id.tv_price_calendar_number_reduce);
        ImageView ivAdd = (ImageView) view.findViewById(R.id.tv_price_calendar_number_add);
        tvNumber = (TextView) view.findViewById(R.id.tv_price_calendar_number);
        Button btnReserveOrder = (Button) view.findViewById(R.id.btn_hot_activity_reserve);
        ivReduce.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        btnReserveOrder.setOnClickListener(this);
    }


    private void changeIndicator(int indicator) {
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
        switch (indicator) {
            case 1:
                tvProduct.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                viewProduct.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                btnProductTop.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                viewProductTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                break;
            case 2:
                btnPrice.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                viewPrice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                btnPriceTop.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                viewPriceTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                break;
            case 3:
                tvDescribe.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                viewDescribe.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                btnDescribeTop.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                viewDescribeTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                break;
            case 4:
                tvNotice.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                viewNotice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                btnNoticeTop.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                viewNoticeTop.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTab1RecommendForYouArgument));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO 进入查看大图页面
    }

//    private void addListener() {

    @Override
    protected void addListener() {
        super.addListener();

        ivTitleLeft.setOnClickListener(this);
        ivTitleRight.setOnClickListener(this);

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

        gvImages.setOnItemClickListener(this);
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
     * @param motionEvent
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    /**
     * GestureDetector.OnGestureListener 回调
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
        Intent intent = new Intent(context, HotActivityDetailActivity.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAG = null;
        mScrollView = null;
        content = null;
        btnReserve = null;
        layoutIndicatorTop = null;
        btnProductTop = null;
        btnPriceTop = null;
        btnDescribeTop = null;
        btnNoticeTop = null;
        viewProductTop = null;
        viewPriceTop = null;
        viewDescribeTop = null;
        viewNoticeTop = null;

        layoutIndicatorBottom = null;
        tvProduct = null;
        btnPrice = null;
        tvDescribe = null;
        tvNotice = null;
        viewProduct = null;
        viewPrice = null;
        viewDescribe = null;
        viewNotice = null;
        mWebViewProduct = null;
        layoutPrice = null;
        tvPriceInclude = null;
        tvPriceNotInclude = null;
        layoutTravelDescribe = null;
        tvTravelDescribe = null;
        layoutNotice = null;
        tvReserveNotice = null;
        tvTitle = null;
        tvPrice = null;
        layoutComment = null;
        tvCommentCount = null;
        civIcon = null;
        tvNickName = null;
        tvAddTime = null;
        tvCommentContent = null;
        gvImages = null;
        titles = null;
        id = null;
        detail = null;
        infos.clear();
        infos = null;
        flipper = null;
        layoutPoint = null;
        imageUrls.clear();
        imageUrls = null;
        indicators = null;
        currentPosition = 0;
        mGestureDetector = null;
    }
}
