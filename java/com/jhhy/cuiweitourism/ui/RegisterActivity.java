package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.CheckCodeBiz;
import com.jhhy.cuiweitourism.biz.RegisterBiz;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvDeal;
    private TextView tvTitle;
    private TextView tvGetCheckCode;

    private EditText etMobile;
    private EditText etCheckCode; //验证码
    private EditText etRegisterCode; //注册码

    private EditText etPassword;
    private EditText etConfirmPassword;

    private CheckBox cbRegisterDeal;

    private Button btnRegister;
    private TextView tvToLogin;

    private TimerTask task;
    private Timer timer;
    int i = 60;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingIndicator.cancel();
            switch (msg.what){
                case Consts.MESSAGE_REGISTER:
                    if(0 == msg.arg1){
                        ToastUtil.show(getApplicationContext(), (String)msg.obj);
                    }else if(1 == msg.arg1){
                        ToastUtil.show(getApplicationContext(), "注册成功");
                        User user = (User) msg.obj;
                        Intent data = new Intent();
                        data.putExtra(Consts.KEY_RESULT_DATA, user);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                    break;
                case Consts.MESSAGE_CHECK_CODE:
                    ToastUtil.show(getApplicationContext(), (String)msg.obj);
                    break;
                case 1: //验证码倒计时
                    if( msg.arg1 < 1){
                        i = 60;
                        tvGetCheckCode.setText("获取验证码");
                    }else {
                        tvGetCheckCode.setText(msg.arg1 + "秒");
                        startTime();
                    }
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setupView();
        addListener();
    }

    private void setupView() {
        tvDeal = (TextView) findViewById(R.id.tv_register_deal);
        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitle.setText(getString(R.string.activity_register_title));
        tvGetCheckCode = (TextView) findViewById(R.id.tv_activity_register_get_check_code);

        etMobile = (EditText) findViewById(R.id.et_activity_regist_phone_number);
        etCheckCode = (EditText) findViewById(R.id.et_activity_regist_check_code);
        etRegisterCode = (EditText) findViewById(R.id.et_activity_regist_register_code);

        etPassword = (EditText) findViewById(R.id.et_activity_regist_set_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_activity_regist_confirm_password);

        etMobile.setText("15210656918");
        etCheckCode.setText("cwly");
        etRegisterCode.setText("CWuhvle");
        etPassword.setText("admin123");
        etConfirmPassword.setText("admin123");


        btnRegister = (Button) findViewById(R.id.btn_register_register);
        tvToLogin = (TextView) findViewById(R.id.tv_register_to_login);

        cbRegisterDeal = (CheckBox) findViewById(R.id.cb_register_deal);

        String dealTitle = tvDeal.getText().toString();
        SpannableStringBuilder builder = new SpannableStringBuilder(dealTitle);
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
//        ForegroundColorSpan normalSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorRegistDealText));
//        builder.setSpan(normalSpan, 0, dealTitle.indexOf("《"), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
        builder.setSpan(blueSpan, dealTitle.indexOf("《"), dealTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvDeal.setText(builder);
    }

    private void addListener() {
        btnRegister.setOnClickListener(this);
        tvGetCheckCode.setOnClickListener(this);
        tvToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register_register:
                register();
                break;
            case R.id.tv_activity_register_get_check_code:
                getCheckCode();
                break;
            case R.id.tv_register_to_login:
                finish();
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

    private void register() {
        String mobile = etMobile.getText().toString().trim();
        String checkCode = etCheckCode.getText().toString().trim();
        String registerCode = etRegisterCode.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(mobile) || TextUtils.isEmpty(checkCode) || TextUtils.isEmpty(password)){
            ToastUtil.show(getApplicationContext(), getString(R.string.empty_input));
            return;
        }
//        {"head":{"code":"User_register"},"field":{"mobile":"15210656911","password":"admin123","verify":"cwly","codes":"CWuhvle"}}
        LoadingIndicator.show(RegisterActivity.this, getString(R.string.http_notice));
        RegisterBiz biz = new RegisterBiz(getApplicationContext(), handler);
        biz.register(mobile, "admin123", "cwly", "CWuhvle");
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

    public static void actionStart(Context context, Bundle data) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(data != null){
            intent.putExtras(data);
        }
        context.startActivity(intent);
    }
}
