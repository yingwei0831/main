package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.view.MyListView;

/**
 * Created by jiahe008 on 2016/12/8.
 */
public class PlaneInquiryEditOrderActivity extends BaseActionBarActivity {

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
    private EditText etLinkEmail; //

    private Button btnCommit; //提交订单

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
        etLinkEmail = (EditText) findViewById(R.id.et_plane_link_email);

        btnCommit = (Button) findViewById(R.id.btn_edit_order_commit);
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

                break;
            case R.id.btn_edit_order_commit: //提交订单

                break;

        }
    }

    private void getData() {

    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PlaneInquiryEditOrderActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
