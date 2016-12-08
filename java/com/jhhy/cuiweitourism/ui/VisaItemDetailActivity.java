package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserCollectionBiz;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.model.VisaDetail;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyScrollViewIS;
import com.just.sun.pricecalendar.ToastCommon;

public class VisaItemDetailActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = VisaItemDetailActivity.class.getSimpleName();
    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private String id;

    private MyScrollViewIS scrollView;
    private ImageView ivNation;
    private TextView tvVisaTitle;
    private TextView tvVisaPrice;
    private TextView tvVisaPeriod;
    private TextView tvVisaType;
    private TextView tvVisaStayTime;
    private TextView tvVisaEffectiveDate;
    private TextView tvVisaHandle; //受理范围

    private TextView tvIntroduce; //签证介绍
    private TextView tvNeedMaterial; //所需材料
    private TextView tvReserveNotice; //预订须知
    private LinearLayout layoutReserveNotice; //预订须知布局

    private LinearLayout layoutIndicatorTop;
    private LinearLayout layoutIndicatorBottom;

    private Button  btnIntroduceTop; //签证介绍
    private Button  btnMaterialTop; //所需材料
    private Button  btnReserveNoticeTop; //预订须知
    private View    viewIntroduceTop;
    private View    viewMaterialTop;
    private View    viewReserveNoticeTop;

    private Button  btnIntroduceBottom; //签证介绍
    private Button  btnMaterialBottom; //所需材料
    private Button  btnReserveNoticeBottom; //预订须知
    private View    viewIntroduceBottom;
    private View    viewMaterialBottom;
    private View    viewReserveNoticeBottom;

    private TextView tvCollection; //收藏
    private TextView tvShare; //分享
    private Button btnReserve; //立即预定

    private VisaDetail mVisaDetail;

    private int actionBarHeight; //ActionBar 高度
    private int indicatorTopHeight2; //IndicatorTopHeight 高度
    private boolean click = false; //是否点击indicator

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.NET_ERROR:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "网络无连接，请检查网络");
                    break;
                case Consts.MESSAGE_VISA_DETAIL: //签证详情
                    if (msg.arg1 == 1){
                        VisaDetail visaDetail = (VisaDetail) msg.obj;
                        if (visaDetail == null){

                        }else{
                            mVisaDetail = visaDetail;
                            refreshView();
                        }
                    }else{

                    }
                    break;
                case Consts.MESSAGE_DO_COLLECTION: //收藏
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        //TODO 改变收藏按钮颜色

                    }
                    break;
            }
            LoadingIndicator.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        setContentView(R.layout.activity_visa_item_detail);
        //以下代码用于去除阴影
        if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(0);
        }
        setupView();
        getData();
        getInternetData();
        addListener();
    }


    private void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.visa_title));
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        ivNation = (ImageView) findViewById(R.id.iv_visa_nation);
        scrollView = (MyScrollViewIS) findViewById(R.id.scrollview_visa_detail);

        tvVisaTitle = (TextView) findViewById(R.id.tv_visa_title);
        tvVisaPrice = (TextView) findViewById(R.id.tv_travel_price);
        tvVisaPeriod = (TextView) findViewById(R.id.tv_visa_period);
        tvVisaType = (TextView) findViewById(R.id.tv_visa_type);
        tvVisaStayTime = (TextView) findViewById(R.id.tv_visa_stay_time);
        tvVisaEffectiveDate = (TextView) findViewById(R.id.tv_visa_effective_date);
        tvVisaHandle = (TextView) findViewById(R.id.tv_visa_apply_to);

        layoutIndicatorTop = (LinearLayout) findViewById(R.id.layout_visa_item_main_indicator_top);
        layoutIndicatorTop.setVisibility(View.GONE);
        layoutIndicatorBottom = (LinearLayout) findViewById(R.id.layout_visa_item_main_indicator_bottom);

        btnIntroduceTop         = (Button) findViewById(R.id.btn_visa_item_detail_indicator_top_product);
        btnMaterialTop          = (Button)  findViewById(R.id.btn_visa_item_detail_indicator_top_material);
        btnReserveNoticeTop     = (Button)  findViewById(R.id.btn_visa_item_detail_indicator_top_notice);
        btnIntroduceBottom      = (Button)  findViewById(R.id.btn_visa_item_detail_indicator_bottom_product);
        btnMaterialBottom       = (Button)  findViewById(R.id.btn_visa_item_detail_indicator_bottom_material);
        btnReserveNoticeBottom  = (Button) findViewById(R.id.btn_visa_item_detail_indicator_bottom_notice);

        viewIntroduceTop        = findViewById(R.id.view_visa_item_detail_indicator_top_product);
        viewMaterialTop         = findViewById(R.id.view_visa_item_detail_indicator_top_material);
        viewReserveNoticeTop    = findViewById(R.id.view_visa_item_detail_indicator_top_notice);
        viewIntroduceBottom     = findViewById(R.id.view_visa_item_detail_indicator_bottom_product);
        viewMaterialBottom      = findViewById(R.id.view_visa_item_detail_indicator_bottom_material);
        viewReserveNoticeBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_notice);

        tvIntroduce = (TextView) findViewById(R.id.tv_visa_introduce);
        tvNeedMaterial = (TextView) findViewById(R.id.tv_material);
        tvReserveNotice = (TextView) findViewById(R.id.tv_reserve_notice);
        layoutReserveNotice = (LinearLayout) findViewById(R.id.layout_reserve_notice);

        tvCollection = (TextView) findViewById(R.id.tv_visa_detail_collection);
        tvShare = (TextView) findViewById(R.id.tv_visa_detail_share);
        btnReserve = (Button) findViewById(R.id.btn_visa_detail_reserve);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        tvNeedMaterial.setOnClickListener(this);

        btnIntroduceTop.setOnClickListener(this);
        btnMaterialTop.setOnClickListener(this);
        btnReserveNoticeTop.setOnClickListener(this);
        btnIntroduceBottom.setOnClickListener(this);
        btnMaterialBottom.setOnClickListener(this);
        btnReserveNoticeBottom.setOnClickListener(this);

        tvCollection.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        btnReserve.setOnClickListener(this);

        final int statusHeight = Utils.getStatusBarHeight(getApplicationContext()); //状态栏高度
        scrollView.setOnScrollChangedI(new MyScrollViewIS.onScrollChangedI() {
            @Override
            public void onScrollChangedImpl(int l, int t, int oldl, int oldt) {
                int[] s = new int[2];

                actionBarHeight = actionBar.getHeight(); //ActionBar高度
                layoutIndicatorBottom.getLocationOnScreen(s);
                indicatorTopHeight2 = layoutIndicatorTop.getMeasuredHeight(); //页面中indicator高度
//                LogUtil.e(TAG, "statusHeight = " + statusHeight + ", titleHeight = " + titleHeight + ", indicatorTopHeight2 = " + indicatorTopHeight2);
                if(statusHeight + indicatorTopHeight2 >= s[1]){
                    layoutIndicatorTop.setVisibility(View.VISIBLE);
                }else{
                    layoutIndicatorTop.setVisibility(View.INVISIBLE);
                }
                if (!click) {
                    //滑动过程中，监听3个tab的位置，变换相关tab的显示
                    int[] introduce = new int[2];
                    tvIntroduce.getLocationOnScreen(introduce);
                    if (introduce[1] <= statusHeight + actionBarHeight + indicatorTopHeight2) {
                        changeIndicator(0);
                    }

                    int[] material = new int[2];
                    tvNeedMaterial.getLocationOnScreen(material);
                    if (material[1] <= statusHeight + actionBarHeight + indicatorTopHeight2) {
                        changeIndicator(1);
                    }

                    int[] layoutNotice = new int[2];
                    layoutReserveNotice.getLocationOnScreen(layoutNotice);
                    if (layoutNotice[1] <= statusHeight + actionBarHeight + indicatorTopHeight2) {
                        changeIndicator(2);
                    }
                }
            }
        });
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
    }

    private void getInternetData() {
        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
        biz.getVisaDetail(id);
//        com.jhhy.cuiweitourism.net.models.FetchModel.VisaDetail visaDetail = new com.jhhy.cuiweitourism.net.models.FetchModel.VisaDetail();
//        visaDetail.setProductID(id);
//        VisaActionBiz biz = new VisaActionBiz(getApplicationContext(), handler);
//        biz.getVisaDetail(visaDetail, new BizGenericCallback<VisaDetailInfo>() {
//            @Override
//            public void onCompletion(GenericResponseModel<VisaDetailInfo> model) {
//                if ("0000".equals(model.headModel.res_code)) {
//                    VisaDetailInfo visaDetailInfo = model.body;
//                    LogUtil.e(TAG,"visaDetailInfo: " + visaDetailInfo.toString());
//                }else{
//                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
//                }
//            }
//
//            @Override
//            public void onError(FetchError error) {
//                if (error.localReason != null){
//                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
//                }else{
//                    ToastCommon.toastShortShow(getApplicationContext(), null, "签证详情出错");
//                }
//                LogUtil.e(TAG, "visaDetailInfo: " + error.toString());
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_material:
                //所需材料
                Intent intent = new Intent(getApplicationContext(), VisaNeedMaterialActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putStringArray("content", new String[]{mVisaDetail.getMaterial1(), mVisaDetail.getMaterial2(), mVisaDetail.getMaterial3(), mVisaDetail.getMaterial4(), mVisaDetail.getMaterial5()});
//                bundle.putString("id", mVisaDetail.getId());
                bundle.putSerializable("visaDetail", mVisaDetail);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_NEED_MATERIAL);
                break;
            case R.id.btn_visa_item_detail_indicator_top_product:
            case R.id.btn_visa_item_detail_indicator_bottom_product:
                click = true;
                tvIntroduce.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, tvIntroduce.getTop() - indicatorTopHeight2);
                        click = false;
                    }
                });
                changeIndicator(0);
                break;
            case R.id.btn_visa_item_detail_indicator_top_material:
            case R.id.btn_visa_item_detail_indicator_bottom_material:
                click = true;
                tvNeedMaterial.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, tvNeedMaterial.getTop() - indicatorTopHeight2);
                        click = false;
                    }
                });
                changeIndicator(1);
                break;
            case R.id.btn_visa_item_detail_indicator_top_notice:
            case R.id.btn_visa_item_detail_indicator_bottom_notice:
                click = true;
                layoutReserveNotice.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, layoutReserveNotice.getTop() - indicatorTopHeight2);
                        click = false;
                    }
                });
                changeIndicator(2);
                break;
            case R.id.tv_visa_detail_collection: //收藏
                doCollection();
                break;
            case R.id.tv_visa_detail_share: //分享

                break;
            case R.id.btn_visa_detail_reserve: //立即预定
                toReserve();
                break;
        }
    }

    //立即预定,去日历中添加人数
    private void toReserve() {
        Intent intent = new Intent(getApplicationContext(), VisaPriceCalendarReserveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("visaDetail", mVisaDetail);
        intent.putExtras(bundle);
        startActivityForResult(intent, VISA_RESERVE);
    }

    private final int VISA_RESERVE = 1821; //立即预定
    private int REQUEST_NEED_MATERIAL = 1812; //进入所需材料，里面包含立即预定

    private void doCollection() {
        UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
        biz.doCollection(MainActivity.user.getUserId(), "8", mVisaDetail.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEED_MATERIAL){
            if (resultCode == RESULT_OK){

            }
        }else if (requestCode == VISA_RESERVE){
            if (resultCode == RESULT_OK){

            }
        }
    }

    private void refreshView(){
        ImageLoaderUtil.getInstance(getApplicationContext()).displayImage(mVisaDetail.getLitPic(), ivNation);
        tvVisaTitle.setText(mVisaDetail.getTitle());
        tvVisaPrice.setText(mVisaDetail.getPrice());
        tvVisaPeriod.setText(mVisaDetail.getHandleday());
        tvVisaType.setText(mVisaDetail.getVisatype());
        tvVisaStayTime.setText(mVisaDetail.getPartday());
        tvVisaEffectiveDate.setText(mVisaDetail.getValidday());
        tvVisaHandle.setText(mVisaDetail.getHandlerange());
        tvIntroduce.setText(mVisaDetail.getContent());
        tvReserveNotice.setText(mVisaDetail.getFriendtip());
    }

    private void changeIndicator(int type){
        btnIntroduceTop         .setTextColor(getResources().getColor(android.R.color.black));
        btnMaterialTop          .setTextColor(getResources().getColor(android.R.color.black));
        btnReserveNoticeTop     .setTextColor(getResources().getColor(android.R.color.black));
        btnIntroduceBottom      .setTextColor(getResources().getColor(android.R.color.black));
        btnMaterialBottom       .setTextColor(getResources().getColor(android.R.color.black));
        btnReserveNoticeBottom  .setTextColor(getResources().getColor(android.R.color.black));
        viewIntroduceTop        .setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewMaterialTop         .setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewReserveNoticeTop    .setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewIntroduceBottom     .setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewMaterialBottom      .setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewReserveNoticeBottom .setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));

            if (type == 0){
                btnIntroduceTop     .setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                btnIntroduceBottom      .setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewIntroduceTop    .setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewIntroduceBottom     .setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));

            }else if(1 == type){
                btnMaterialTop     .setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                btnMaterialBottom      .setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewMaterialTop    .setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewMaterialBottom     .setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            }else if(2 == type){
                btnReserveNoticeTop     .setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                btnReserveNoticeBottom      .setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewReserveNoticeTop    .setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewReserveNoticeBottom     .setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            }
    }
}
