package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.CheckCodeBiz;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.utils.Consts;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.Timer;
import java.util.TimerTask;


public class ModifyTelephoneNumberActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvTitleLeft;
    private TextView tvTitleRight;

    private EditText etNewMobile;
    private EditText etVerify;

    private Button btnGetCheckCode;
    private Button btnCommit;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_CHECK_CODE:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    break;
                case TIMER_TASK:
                    if( msg.arg1 < 1){
                        i = 60;
                        btnGetCheckCode.setText("获取验证码");
                    }else {
                        btnGetCheckCode.setText(msg.arg1 + "");
                        startTime();
                    }
                    break;
                case Consts.MESSAGE_MODIFY_USER_MOBILE:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile", etNewMobile.getText().toString().trim());
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_telephone_number);

        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitle.setText("修改手机");
        tvTitleRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setVisibility(View.GONE);
        tvTitleLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);

        etNewMobile = (EditText) findViewById(R.id.et_input_mobile);
        etVerify = (EditText) findViewById(R.id.et_verify);
        btnGetCheckCode = (Button) findViewById(R.id.activity_regist_btn_getnum);
        btnCommit = (Button) findViewById(R.id.btn_commit);

        addListener();
    }

    private void addListener() {
        tvTitleLeft.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        btnGetCheckCode.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_left:
                finish();
                break;
            case R.id.activity_regist_btn_getnum: //获取验证码
                getCheckCode();

                break;
            case R.id.btn_commit: //提交手机号码
                modifyMobile();
                break;
        }
    }

    private void modifyMobile() {
        String newMobile = etNewMobile.getText().toString();
        String checkCode = etVerify.getText().toString();
        if (TextUtils.isEmpty(newMobile) || TextUtils.isEmpty(checkCode)){
            ToastCommon.toastShortShow(getApplicationContext(), null, getString(R.string.empty_input));
        }
        if (newMobile.trim().length() != 11){
            ToastCommon.toastShortShow(getApplicationContext(), null, "手机号输入不正确");
        }
        UserInformationBiz biz = new UserInformationBiz(getApplicationContext(), handler);
        biz.modifyUserMobile(MainActivity.user.getUserId(), newMobile.trim(), checkCode.trim());
    }

    private void getCheckCode() {
        String newMobile = etNewMobile.getText().toString();
        if (newMobile == null || newMobile.trim().length() != 11){
            ToastCommon.toastShortShow(getApplicationContext(), null, "手机号输入不正确");
            return;
        }
        if(i == 60) {
            startTime();
        }else{
            return;
        }
        CheckCodeBiz biz = new CheckCodeBiz(getApplicationContext(), handler);
        biz.getCheckCode(newMobile.trim());

    }

    private TimerTask task;
    private Timer timer;
    int i = 60;
    private final int TIMER_TASK = 1777;

    private void startTime() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = TIMER_TASK;
                msg.arg1 = --i;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(task, 1000);
    }
}
