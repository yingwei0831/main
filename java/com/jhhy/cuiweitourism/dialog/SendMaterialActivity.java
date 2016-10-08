package com.jhhy.cuiweitourism.dialog;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.ui.BaseActivity;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

public class SendMaterialActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;

    private EditText etMail;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Consts.MESSAGE_VISA_SEND_MATERIAL: //发送材料到我的邮箱
                    ToastUtil.show(getApplicationContext(), String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_visa_deliver_material);

        setupView();
    }

    private void setupView() {
        Button sendMaterial = (Button) findViewById(R.id.btn_send_material);

        sendMaterial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String mail = etMail.getText().toString();
                if (TextUtils.isEmpty(mail)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, "输入不能为空");
                    return;
                }
                if (Utils.checkEmail(mail)){
                    VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
                    biz.doSentMaterialToMyMail(MainActivity.user.getUserId());
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "邮件格式不正确");
                }
            }
        });

        tv1 = (TextView) findViewById(R.id.tv_type1);
        tv2 = (TextView) findViewById(R.id.tv_type2);
        tv3 = (TextView) findViewById(R.id.tv_type3);
        tv4 = (TextView) findViewById(R.id.tv_type4);
        tv5 = (TextView) findViewById(R.id.tv_type5);

        etMail = (EditText) findViewById(R.id.et_mail_address);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        tv1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_unselected_corner_border));
        tv2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_unselected_corner_border));
        tv3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_unselected_corner_border));
        tv4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_unselected_corner_border));
        tv5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_unselected_corner_border));
        switch (view.getId()){
            case R.id.tv_type1:
                tv1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_type2:
                tv2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_type3:
                tv3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_type4:
                tv4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_type5:
                tv5.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_et_city_selected_corner_border));
                break;
        }
    }
}
