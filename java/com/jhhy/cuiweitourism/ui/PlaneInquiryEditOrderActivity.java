package com.jhhy.cuiweitourism.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneInquiryLegAdapter;
import com.jhhy.cuiweitourism.model.PlaneInquiry;
import com.jhhy.cuiweitourism.model.TypeBean;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInquiryRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyListView;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahe008 on 2016/12/8.
 * 询价
 */
public class PlaneInquiryEditOrderActivity extends BaseActionBarActivity {

    private String TAG = "PlaneInquiryEditOrderActivity";

    private MyListView mListView;

    private ImageView ivReduceAdult; //成人减少
    private ImageView ivPlusAdult;   //成人增加
    private ImageView ivReduceChild; //儿童减少
    private ImageView ivPlusChild;   //儿童增加

    private TextView tvCountAdult; //成人数量
    private TextView tvCountChild; //儿童数量

    private int countAdult = 1;
    private int countChild;

    private TextView tvCabinLevel; //舱位等级
    private EditText etRemark; //其它要求
    private EditText etLinkName; //name
    private EditText etLinkMobile; //
    private EditText etLinkeTel; //联系电话
    private EditText etLinkEmail; //

    private Button btnCommit; //提交订单
    private ArrayList<PlaneInquiry> listInquiry; //出发城市，目的城市，出发时间
    private PlaneInquiryLegAdapter adapter;

    private ArrayList<TypeBean> mList = new ArrayList<>();
    private String cabinLevel; //舱位等级

    private boolean commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_inquiry_edit_order);
        getData();
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getText(R.string.plane_flight_inquiry_title));

        mListView = (MyListView) findViewById(R.id.list_view_inquiry);

        ivReduceChild = (ImageView) findViewById(R.id.tv_price_calendar_number_reduce);
        ivPlusChild = (ImageView) findViewById(R.id.tv_price_calendar_number_add);
        ivReduceAdult = (ImageView) findViewById(R.id.tv_price_calendar_number_reduce_child);
        ivPlusAdult = (ImageView) findViewById(R.id.tv_price_calendar_number_add_child);

        tvCountAdult = (TextView) findViewById(R.id.tv_price_calendar_number);
        tvCountChild = (TextView) findViewById(R.id.tv_price_calendar_number_child);
        tvCountAdult.setText(String.valueOf(countAdult));
        tvCountChild.setText(String.valueOf(countChild));

        tvCabinLevel = (TextView) findViewById(R.id.tv_cabin_level);
        etRemark = (EditText) findViewById(R.id.et_inquiry_remark);
        etLinkName  = (EditText) findViewById(R.id.et_plane_order_link_name);
        etLinkMobile = (EditText) findViewById(R.id.et_plane_link_mobile);
        etLinkeTel = (EditText) findViewById(R.id.et_plane_link_tel);
        etLinkEmail = (EditText) findViewById(R.id.et_plane_link_email);

        btnCommit = (Button) findViewById(R.id.btn_edit_order_commit);
        adapter = new PlaneInquiryLegAdapter(getApplicationContext(), listInquiry);
        mListView.setAdapter(adapter);
        mList = new ArrayList<>();
        mList.add(new TypeBean(0, "不限"));
        mList.add(new TypeBean(1, "经济舱"));
        mList.add(new TypeBean(2, "商务舱"));
        mList.add(new TypeBean(3, "头等舱"));
        cabinLevel = "不限";
        tvCabinLevel.setText(cabinLevel);
    }

    @Override
    protected void addListener() {
        super.addListener();
        ivReduceChild.setOnClickListener(this);
        ivPlusChild.setOnClickListener(this);
        ivReduceAdult.setOnClickListener(this);
        ivPlusAdult.setOnClickListener(this);
        tvCabinLevel.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_price_calendar_number_reduce:
                if (countAdult > 1) {
                    countAdult -= 1;
                    tvCountAdult.setText(Integer.toString(countAdult));
                }
                break;
            case R.id.tv_price_calendar_number_add:
                countAdult += 1;
                tvCountAdult.setText(Integer.toString(countAdult));
                break;
            case R.id.tv_price_calendar_number_reduce_child:
                if (countChild > 0) {
                    countChild -= 1;
                    tvCountChild.setText(Integer.toString(countChild));
                }
                break;
            case R.id.tv_price_calendar_number_add_child:
                countChild += 1;
                tvCountChild.setText(Integer.toString(countChild));
                break;
            case R.id.tv_cabin_level: //舱位等级
                Utils.alertBottomWheelOption(this, mList, new Utils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int position) {
                        selectCabinLevel(position);
                    }
                });
                break;
            case R.id.btn_edit_order_commit: //提交订单
                commitInquiry();
                break;
        }
    }

    /**
     * 提交订单
     */
    private void commitInquiry() {
        if (commit) return;
        String name = etLinkName.getText().toString();
        String mobile = etLinkMobile.getText().toString();
        String tel = etLinkeTel.getText().toString();
        String email = etLinkEmail.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showShortToast(getApplicationContext(), "联系人不能为空");
            commit = false;
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.showShortToast(getApplicationContext(), "手机号码不能为空");
            commit = false;
            return;
        }
        if (commit) return;
        commit = true;
        LoadingIndicator.show(this, getString(R.string.http_notice));
        List<PlaneTicketInquiryRequest.HangduanBean> listCommit = new ArrayList<>();
        for (PlaneInquiry inquiry : listInquiry){
            PlaneTicketInquiryRequest.HangduanBean hangduan = new PlaneTicketInquiryRequest.HangduanBean(inquiry.fromCity.name, inquiry.arrivalCity.name, inquiry.getFromDate());
            listCommit.add(hangduan);
        }
        PlaneTicketActionBiz biz = new PlaneTicketActionBiz();
        PlaneTicketInquiryRequest fetch = new PlaneTicketInquiryRequest(String.valueOf(countAdult), String.valueOf(countChild), cabinLevel, etRemark.getText().toString(), name, mobile, tel, email);
        fetch.setHangduan(listCommit);
        biz.planeTicketInquiry(fetch, new BizGenericCallback<Object>() {
            @Override
            public void onCompletion(GenericResponseModel<Object> model) {
                commit = false;
                LoadingIndicator.cancel();
                LogUtil.e(TAG, "planeTicketInquiry: " + model.headModel.toString());
                ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                finish();
            }

            @Override
            public void onError(FetchError error) {
                commit = false;
                LogUtil.e(TAG, "planeTicketInquiry: " + error);
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "提交询价订单出错，请重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }

    /**
     *
     */
    private void selectCabinLevel(int position) {
        cabinLevel = mList.get(position).getName();
        tvCabinLevel.setText(mList.get(position).getName());
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            listInquiry = bundle.getParcelableArrayList("listInquiry");
        }else{
            listInquiry = new ArrayList<>();
        }
    }

    public static void actionStart(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, PlaneInquiryEditOrderActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListView = null;
        ivReduceAdult = null;
        ivPlusAdult = null;
        ivReduceChild = null;
        ivPlusChild = null;
        tvCountAdult = null;
        tvCountChild = null;

        tvCabinLevel = null;
        etRemark = null;
        etLinkName = null;
        etLinkMobile = null;
        etLinkeTel = null;
        etLinkEmail = null;

        btnCommit = null;
        adapter = null;
        if (listInquiry != null){
            listInquiry.clear();
            listInquiry = null;
        }
        if (mList != null){
            mList.clear();
            mList = null;
        }
    }
}
