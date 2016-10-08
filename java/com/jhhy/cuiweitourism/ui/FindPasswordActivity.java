package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.CheckCodeBiz;
import com.jhhy.cuiweitourism.biz.RegisterBiz;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class FindPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvGetCheckCode;

    private EditText etMobile;
    private EditText etCheckCode; //验证码

    private EditText etPassword;
    private EditText etConfirmPassword;

    private Button btnFinish;

    private TimerTask task;
    private Timer timer;
    int i = 60;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1: //验证码倒计时
                    if( msg.arg1 < 1){
                        i = 60;
                        tvGetCheckCode.setText("获取验证码");
                    }else {
                        tvGetCheckCode.setText(msg.arg1 + "秒");
                        startTime();
                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        setupView();
        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitle.setText(getString(R.string.activity_find_password_title));
        tvGetCheckCode = (TextView) findViewById(R.id.tv_activity_find_password_get_check_code);

        etMobile = (EditText) findViewById(R.id.et_activity_find_password_phone_number);
        etCheckCode = (EditText) findViewById(R.id.et_activity_find_password_check_code);

        etPassword = (EditText) findViewById(R.id.et_activity_find_password_set_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_activity_find_password_confirm_password);

        etMobile.setText("15210656918");
        etCheckCode.setText("cwly");
        etPassword.setText("admin123");
        etConfirmPassword.setText("admin123");

        btnFinish = (Button) findViewById(R.id.btn_find_password);
    }

    private void addListener() {
        btnFinish.setOnClickListener(this);
        tvGetCheckCode.setOnClickListener(this);
    }

    public static void actionStart(Context context, Bundle data) {
        Intent intent = new Intent(context, FindPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(data != null){
            intent.putExtras(data);
        }
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_find_password:
                findPassword();
                break;
            case R.id.tv_activity_find_password_get_check_code:
                getCheckCode();
                break;
        }
    }

    private void getCheckCode() {
        if(i < 60)  return;
        String mobile = etMobile.getText().toString();
        if(TextUtils.isEmpty(mobile)){
            ToastUtil.show(getApplicationContext(), getString(R.string.empty_input));
            return;
        }
        if(mobile.length() != 11){
            ToastUtil.show(getApplicationContext(), "手机号位数不正确");
            return;
        }
        if(i == 60) {
            startTime();
            CheckCodeBiz biz = new CheckCodeBiz(getApplicationContext(), handler);
            biz.getCheckCode(mobile);
        }else{
            return;
        }
    }
    private void startTime() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = --i;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(task, 1000);
    }

    private void findPassword() {
        String mobile = etMobile.getText().toString().trim();
        String checkCode = etCheckCode.getText().toString().trim();

        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(mobile) || TextUtils.isEmpty(checkCode) || TextUtils.isEmpty(password)){
            ToastUtil.show(getApplicationContext(), getString(R.string.empty_input));
            return;
        }
//        {"head":{"code":"User_register"},"field":{"mobile":"15210656911","password":"admin123","verify":"cwly","codes":"CWuhvle"}}
        LoadingIndicator.show(FindPasswordActivity.this, getString(R.string.http_notice));
        RegisterBiz biz = new RegisterBiz(getApplicationContext(), handler);
        biz.register(mobile, "admin123", "cwly", "CWuhvle");
    }

}
