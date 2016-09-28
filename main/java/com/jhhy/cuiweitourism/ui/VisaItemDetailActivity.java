package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserCollectionBiz;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.moudle.VisaDetail;
import com.jhhy.cuiweitourism.utils.Consts;
import com.just.sun.pricecalendar.ToastCommon;

public class VisaItemDetailActivity extends BaseActivity implements View.OnClickListener {

    private String id;

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

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_item_detail);

        setupView();
        getData();
        addListener();
    }


    private void setupView() {
        tvVisaTitle = (TextView) findViewById(R.id.tv_visa_title);
        tvVisaPrice = (TextView) findViewById(R.id.tv_travel_price);
        tvVisaPeriod = (TextView) findViewById(R.id.tv_visa_period);
        tvVisaType = (TextView) findViewById(R.id.tv_visa_type);
        tvVisaStayTime = (TextView) findViewById(R.id.tv_visa_stay_time);
        tvVisaEffectiveDate = (TextView) findViewById(R.id.tv_visa_effective_date);
        tvVisaHandle = (TextView) findViewById(R.id.tv_visa_apply_to);

        btnIntroduceTop     = (Button) findViewById(R.id.btn_visa_item_detail_indicator_top_product);
        btnMaterialTop      = (Button)  findViewById(R.id.btn_visa_item_detail_indicator_top_material);
        btnReserveNoticeTop = (Button)  findViewById(R.id.btn_visa_item_detail_indicator_top_notice);
        btnIntroduceBottom  = (Button)  findViewById(R.id.btn_visa_item_detail_indicator_bottom_product);
        btnMaterialBottom   = (Button)  findViewById(R.id.btn_visa_item_detail_indicator_bottom_material);
        btnReserveNoticeBottom = (Button) findViewById(R.id.btn_visa_item_detail_indicator_bottom_notice);

        viewIntroduceTop = findViewById(R.id.view_visa_item_detail_indicator_top_product);
        viewMaterialTop = findViewById(R.id.view_visa_item_detail_indicator_top_material);
        viewReserveNoticeTop = findViewById(R.id.view_visa_item_detail_indicator_top_notice);
        viewIntroduceBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_product);
        viewMaterialBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_material);
        viewReserveNoticeBottom = findViewById(R.id.view_visa_item_detail_indicator_bottom_notice);

        tvIntroduce = (TextView) findViewById(R.id.tv_visa_introduce);
        tvNeedMaterial = (TextView) findViewById(R.id.tv_material);
        tvReserveNotice = (TextView) findViewById(R.id.tv_reserve_notice);

        tvCollection = (TextView) findViewById(R.id.tv_visa_detail_collection);
        tvShare = (TextView) findViewById(R.id.tv_visa_detail_share);
        btnReserve = (Button) findViewById(R.id.btn_visa_detail_reserve);
    }

    private void addListener() {
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

    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        getInternetData();
    }

    private void getInternetData() {
        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
        biz.getVisaDetail(id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_material:
                //所需材料
                startActivityForResult(new Intent(getApplicationContext(), VisaNeedMaterialActivity.class), REQUEST_NEED_MATERIAL);
                break;
            case R.id.btn_visa_item_detail_indicator_top_product:
            case R.id.btn_visa_item_detail_indicator_bottom_product:

                break;
            case R.id.btn_visa_item_detail_indicator_top_material:
            case R.id.btn_visa_item_detail_indicator_bottom_material:

                break;
            case R.id.btn_visa_item_detail_indicator_top_notice:
            case R.id.btn_visa_item_detail_indicator_bottom_notice:

                break;
            case R.id.tv_visa_detail_collection: //收藏
                doCollection();
                break;
            case R.id.tv_visa_detail_share: //分享

                break;
            case R.id.btn_visa_detail_reserve: //立即预定

                break;
        }
    }

    private void doCollection() {
        UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
        biz.doCollection(MainActivity.user.getUserId(), "8", mVisaDetail.getId());
    }

    private int REQUEST_NEED_MATERIAL = 1812;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEED_MATERIAL){
            if (resultCode == RESULT_OK){

            }
        }
    }

    private void refreshView(){
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

}
