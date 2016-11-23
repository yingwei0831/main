package com.jhhy.cuiweitourism.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaDetailMaterialAdapter;
import com.jhhy.cuiweitourism.biz.UserCollectionBiz;
import com.jhhy.cuiweitourism.moudle.VisaDetail;
import com.jhhy.cuiweitourism.net.biz.VisaActionBiz;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaMaterial;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyListView;
import com.jhhy.cuiweitourism.view.MyScrollViewIS;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisaItemDetailActivity2 extends BaseActivity implements View.OnClickListener {

    private String TAG = VisaItemDetailActivity2.class.getSimpleName();
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

//    private TextView tvIntroduce; //签证介绍
//    private TextView tvNeedMaterial; //所需材料
//    private TextView tvReserveNotice; //预订须知
//    private LinearLayout layoutReserveNotice; //预订须知布局

    private View layoutIndicatorTop;
    private Button btnMaterialWorkerTop; //在职人员
    private Button btnMaterialFreedomTop; //自由职业者
    private Button btnMaterialRetiredTop; //退休人员
    private Button btnReserveStudentTop; //学生
    private Button btnReserveChildrenTop; //学龄前儿童
    private View viewIntroduceTop;
    private View viewMaterialTop;
    private View viewReserveNoticeTop;
    private View viewMaterialStudentTop;
    private View viewMaterialChildrenTop;

    private View layoutIndicatorBottom;
    private Button btnMaterialWorkerBottom; //在职人员
    private Button btnMaterialFreedomBottom; //自由职业者
    private Button btnReserveRetiredBottom; //退休人员
    private Button btnReserveStudentBottom; //学生
    private Button btnReserveChildrenBottom; //学龄前儿童
    private View viewIntroduceBottom;
    private View viewMaterialBottom;
    private View viewReserveNoticeBottom;
    private View viewMaterialStudentBottom;
    private View viewMaterialChildrenBottom;

    private VisaDetailInfo visaDetailInfo;
    private MyListView listMaterial;
    private VisaDetailMaterialAdapter adapterMaterial;
    private List<VisaMaterial> listMaterialData = new ArrayList<>();

    private TextView tvCollection; //收藏
    private TextView tvShare; //分享
    private Button btnReserve; //立即预定

//    private VisaDetail mVisaDetail;

    private int actionBarHeight; //ActionBar 高度
    private int indicatorTopHeight2; //IndicatorTopHeight 高度
    private boolean click = false; //是否点击indicator

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Consts.NET_ERROR:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "网络无连接，请检查网络");
                    break;
                case Consts.MESSAGE_VISA_DETAIL: //签证详情
                    if (msg.arg1 == 1) {
                        VisaDetail visaDetail = (VisaDetail) msg.obj;
//                            mVisaDetail = visaDetail;
                        refreshView();
                    } else {

                    }
                    break;
                case Consts.MESSAGE_DO_COLLECTION: //收藏
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1) {
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
        actionBar = getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        setContentView(R.layout.activity_visa_item_detail2);
        //以下代码用于去除阴影
        if (Build.VERSION.SDK_INT >= 21) {
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
        scrollView.fullScroll(ScrollView.FOCUS_UP);

        tvVisaTitle = (TextView) findViewById(R.id.tv_visa_title);
        tvVisaPrice = (TextView) findViewById(R.id.tv_travel_price);
        tvVisaPeriod = (TextView) findViewById(R.id.tv_visa_period);
        tvVisaType = (TextView) findViewById(R.id.tv_visa_type);
        tvVisaStayTime = (TextView) findViewById(R.id.tv_visa_stay_time);
        tvVisaEffectiveDate = (TextView) findViewById(R.id.tv_visa_effective_date);
        tvVisaHandle = (TextView) findViewById(R.id.tv_visa_apply_to);

        layoutIndicatorTop = findViewById(R.id.layout_visa_item_main_indicator_top);
//        layoutIndicatorTop.setVisibility(View.GONE);
        layoutIndicatorBottom = findViewById(R.id.layout_visa_item_main_indicator_bottom);

        btnMaterialWorkerTop = (Button) findViewById(R.id.btn_visa_item_detail_indicator_top_product);
        btnMaterialFreedomTop = (Button) findViewById(R.id.btn_visa_item_detail_indicator_top_material);
        btnMaterialRetiredTop = (Button) findViewById(R.id.btn_visa_item_detail_indicator_top_notice);
        btnReserveStudentTop = (Button) findViewById(R.id.btn_visa_item_detail_indicator_top_student);
        btnReserveChildrenTop = (Button) findViewById(R.id.btn_visa_item_detail_indicator_top_children);

        btnMaterialWorkerBottom = (Button) findViewById(R.id.btn_visa_item_detail_indicator_bottom_product);
        btnMaterialFreedomBottom = (Button) findViewById(R.id.btn_visa_item_detail_indicator_bottom_material);
        btnReserveRetiredBottom = (Button) findViewById(R.id.btn_visa_item_detail_indicator_bottom_notice);
        btnReserveStudentBottom = (Button) findViewById(R.id.btn_visa_item_detail_indicator_bottom_student);
        btnReserveChildrenBottom = (Button) findViewById(R.id.btn_visa_item_detail_indicator_bottom_children);

        viewIntroduceTop = findViewById(R.id.view_visa_item_detail_indicator_top_product);
        viewMaterialTop = findViewById(R.id.view_visa_item_detail_indicator_top_material);
        viewReserveNoticeTop = findViewById(R.id.view_visa_item_detail_indicator_top_notice);
        viewMaterialStudentTop = findViewById(R.id.view_visa_item_detail_indicator_top_student);
        viewMaterialChildrenTop = findViewById(R.id.view_visa_item_detail_indicator_top_children);

        viewIntroduceBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_product);
        viewMaterialBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_material);
        viewReserveNoticeBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_notice);
        viewMaterialStudentBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_student);
        viewMaterialChildrenBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_children);

//        tvIntroduce = (TextView) findViewById(R.id.tv_visa_introduce);
//        tvNeedMaterial = (TextView) findViewById(R.id.tv_material);
//        tvReserveNotice = (TextView) findViewById(R.id.tv_reserve_notice);
//        layoutReserveNotice = (LinearLayout) findViewById(R.id.layout_reserve_notice);

        tvCollection = (TextView) findViewById(R.id.tv_visa_detail_collection);
        tvShare = (TextView) findViewById(R.id.tv_visa_detail_share);
        tvCollection.setVisibility(View.INVISIBLE);
        tvShare.setVisibility(View.INVISIBLE);

        listMaterial = (MyListView) findViewById(R.id.list_material);
        adapterMaterial = new VisaDetailMaterialAdapter(this, listMaterialData){
            @Override
            public void goToArgument(View view, View viewGroup, int position, int which) {
                // TODO 这里指明是使用Intent.ACTION_VIEW,这将调用系统里面的浏览器来打开指定网页
                String model = listMaterialData.get(position).getMaterialModel();
                if (model != null) {
                    Uri uri = Uri.parse(model);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        };
        listMaterial.setAdapter(adapterMaterial);

        btnReserve = (Button) findViewById(R.id.btn_visa_detail_reserve);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
//        tvNeedMaterial.setOnClickListener(this);

        btnMaterialWorkerTop.setOnClickListener(this);
        btnMaterialFreedomTop.setOnClickListener(this);
        btnMaterialRetiredTop.setOnClickListener(this);
        btnReserveStudentTop.setOnClickListener(this);
        btnReserveChildrenTop.setOnClickListener(this);

        btnMaterialWorkerBottom.setOnClickListener(this);
        btnMaterialFreedomBottom.setOnClickListener(this);
        btnReserveRetiredBottom.setOnClickListener(this);
        btnReserveStudentBottom.setOnClickListener(this);
        btnReserveChildrenBottom.setOnClickListener(this);

//        tvCollection.setOnClickListener(this);
//        tvShare.setOnClickListener(this);
        btnReserve.setOnClickListener(this);

        final int statusHeight = Utils.getStatusBarHeight(getApplicationContext()); //状态栏高度
        scrollView.setOnScrollChangedI(new MyScrollViewIS.onScrollChangedI() {
            @Override
            public void onScrollChangedImpl(int l, int t, int oldl, int oldt) {
                actionBarHeight = actionBar.getHeight(); //ActionBar高度
                int[] s = new int[2];
                layoutIndicatorBottom.getLocationOnScreen(s);
                indicatorTopHeight2 = layoutIndicatorTop.getMeasuredHeight(); //页面中indicator高度
//                LogUtil.e(TAG, "statusHeight = " + statusHeight + ", actionBarHeight = " + actionBarHeight + ", indicatorTopHeight2 = " + indicatorTopHeight2 +", layoutIndicatorBottom = " + s[1]);
                if (statusHeight + actionBarHeight >= s[1]) {
                    layoutIndicatorTop.setVisibility(View.VISIBLE);
                } else {
                    layoutIndicatorTop.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
    }

    private void getInternetData() {
//        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
//        biz.getVisaDetail(id);
        LoadingIndicator.show(VisaItemDetailActivity2.this, getString(R.string.http_notice));
        com.jhhy.cuiweitourism.net.models.FetchModel.VisaDetail visaDetail = new com.jhhy.cuiweitourism.net.models.FetchModel.VisaDetail();
        visaDetail.setProductID(id);
        VisaActionBiz biz = new VisaActionBiz();
        biz.getVisaDetail(visaDetail, new BizGenericCallback<VisaDetailInfo>() {
            @Override
            public void onCompletion(GenericResponseModel<VisaDetailInfo> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    visaDetailInfo = model.body;
                    LogUtil.e(TAG, "visaDetailInfo: " + visaDetailInfo.toString());
                    refreshView();
                    refreshListView(1);

                } else {
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null) {
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                } else {
                    ToastCommon.toastShortShow(getApplicationContext(), null, "签证详情出错");
                }
                LogUtil.e(TAG, "visaDetailInfo: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.btn_visa_item_detail_indicator_top_product: //在职人员
            case R.id.btn_visa_item_detail_indicator_bottom_product:
                click = true;
                refreshListView(1);
                changeIndicator(0);
                break;
            case R.id.btn_visa_item_detail_indicator_top_material: //自由职业者
            case R.id.btn_visa_item_detail_indicator_bottom_material:
                click = true;
                refreshListView(2);
                changeIndicator(1);
                break;
            case R.id.btn_visa_item_detail_indicator_top_notice: //退休人员
            case R.id.btn_visa_item_detail_indicator_bottom_notice:
                click = true;
                refreshListView(3);
                changeIndicator(2);
                break;
            case R.id.btn_visa_item_detail_indicator_top_student: //学生
            case R.id.btn_visa_item_detail_indicator_bottom_student:
                refreshListView(4);
                changeIndicator(3);
                break;
            case R.id.btn_visa_item_detail_indicator_top_children: //学龄前儿童
            case R.id.btn_visa_item_detail_indicator_bottom_children:
                refreshListView(5);
                changeIndicator(4);
                break;
//            case R.id.tv_visa_detail_collection: //收藏
//                doCollection();
//                break;
//            case R.id.tv_visa_detail_share: //分享
//                break;
            case R.id.btn_visa_detail_reserve: //立即预定
                ToastUtil.show(getApplicationContext(), "赞无预定");
//                toReserve();
                break;
        }
    }

    //立即预定,去日历中添加人数
    private void toReserve() {
        Intent intent = new Intent(getApplicationContext(), VisaPriceCalendarReserveActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("visaDetail", visaDetailInfo);
        intent.putExtras(bundle);
        startActivityForResult(intent, VISA_RESERVE);
    }

    private final int VISA_RESERVE = 1821; //立即预定
    private int REQUEST_NEED_MATERIAL = 1812; //进入所需材料，里面包含立即预定

//    private void doCollection() {
//        UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
//        biz.doCollection(MainActivity.user.getUserId(), "8", visaDetailInfo.getVisaId());
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEED_MATERIAL) {
            if (resultCode == RESULT_OK) {

            }
        } else if (requestCode == VISA_RESERVE) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    private void refreshListView(int type) {
        if (type == 1) {
            listMaterialData = visaDetailInfo.getMaterialCollectionl().worker;
            adapterMaterial.setData(listMaterialData);
            adapterMaterial.notifyDataSetChanged();
        } else if (type == 2) {
            listMaterialData = visaDetailInfo.getMaterialCollectionl().freedom;
            adapterMaterial.setData(listMaterialData);
            adapterMaterial.notifyDataSetChanged();
        } else if (type == 3) {
            listMaterialData = visaDetailInfo.getMaterialCollectionl().retired;
            adapterMaterial.setData(listMaterialData);
            adapterMaterial.notifyDataSetChanged();
        } else if (type == 4) {
            listMaterialData = visaDetailInfo.getMaterialCollectionl().student;
            adapterMaterial.setData(listMaterialData);
            adapterMaterial.notifyDataSetChanged();
        } else if (type == 5) {
            listMaterialData = visaDetailInfo.getMaterialCollectionl().children;
            adapterMaterial.setData(listMaterialData);
            adapterMaterial.notifyDataSetChanged();
        }
    }

    private void refreshView() {
        ImageLoaderUtil.getInstance(getApplicationContext()).displayImage(visaDetailInfo.getCountryFlagUrl(), ivNation);
        tvVisaTitle.setText(visaDetailInfo.getVisaName());
        tvVisaPrice.setText(visaDetailInfo.getVisaPrice());
        tvVisaPeriod.setText(String.format(Locale.getDefault(), "%s天", visaDetailInfo.getVisaTime()));
        tvVisaType.setText(visaDetailInfo.getVisaType());
        tvVisaStayTime.setText(visaDetailInfo.getVisaStayPeriod());
        tvVisaEffectiveDate.setText(visaDetailInfo.getVisaPeriodOfValidate());
        tvVisaHandle.setText(visaDetailInfo.getAcceptArea());

//        tvIntroduce.setText(mVisaDetail.getContent());
//        tvReserveNotice.setText(mVisaDetail.getFriendtip());
    }

    private void changeIndicator(int type) {
        btnMaterialWorkerTop.setTextColor(getResources().getColor(android.R.color.black));
        btnMaterialFreedomTop.setTextColor(getResources().getColor(android.R.color.black));
        btnMaterialRetiredTop.setTextColor(getResources().getColor(android.R.color.black));
        btnReserveStudentTop.setTextColor(getResources().getColor(android.R.color.black));
        btnReserveChildrenTop.setTextColor(getResources().getColor(android.R.color.black));

        btnMaterialWorkerBottom.setTextColor(getResources().getColor(android.R.color.black));
        btnMaterialFreedomBottom.setTextColor(getResources().getColor(android.R.color.black));
        btnReserveRetiredBottom.setTextColor(getResources().getColor(android.R.color.black));
        btnReserveStudentBottom.setTextColor(getResources().getColor(android.R.color.black));
        btnReserveChildrenBottom.setTextColor(getResources().getColor(android.R.color.black));

        viewIntroduceTop.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewMaterialTop.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewReserveNoticeTop.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewMaterialStudentTop.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewMaterialChildrenTop.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));

        viewIntroduceBottom.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewMaterialBottom.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewReserveNoticeBottom.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewMaterialStudentBottom.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));
        viewMaterialChildrenBottom.setBackgroundColor(getResources().getColor(R.color.colorBgIndicator));


        if (type == 0) {
            btnMaterialWorkerTop.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            btnMaterialWorkerBottom.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewIntroduceTop.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewIntroduceBottom.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));

        } else if (1 == type) {
            btnMaterialFreedomTop.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            btnMaterialFreedomBottom.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewMaterialTop.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewMaterialBottom.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
        } else if (2 == type) {
            btnMaterialRetiredTop.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            btnReserveRetiredBottom.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewReserveNoticeTop.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewReserveNoticeBottom.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
        } else if (3 == type) {
            btnReserveStudentTop.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            btnReserveStudentBottom.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewMaterialStudentTop.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewMaterialStudentBottom.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
        } else if (4 == type) {
            btnReserveChildrenTop.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            btnReserveChildrenBottom.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewMaterialChildrenTop.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
            viewMaterialChildrenBottom.setBackgroundColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
        }
    }
}
