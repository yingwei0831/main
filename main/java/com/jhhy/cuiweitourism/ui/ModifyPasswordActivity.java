package com.jhhy.cuiweitourism.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;


public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private EditText etCurrentPwd;
    private EditText etNewPwd;
    private EditText etConfirmPwd;

    private Button btnCommit;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_MODIFY_USER_PASSWORD:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        setResult(RESULT_OK);
                        finish();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        setupView();

        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("修改登录密码");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        etCurrentPwd = (EditText) findViewById(R.id.et_current_pwd);
        etNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        etConfirmPwd = (EditText) findViewById(R.id.et_new_pwd_again);

        btnCommit = (Button) findViewById(R.id.btn_commit);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.btn_commit:
                modifyPwd();
                break;
        }
    }

    private void modifyPwd() {
        String currentPwd = etCurrentPwd.getText().toString();
        String newPwd = etNewPwd.getText().toString();
        String confirmPwd = etConfirmPwd.getText().toString();
        if (TextUtils.isEmpty(currentPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(confirmPwd) ){
            ToastCommon.toastShortShow(getApplicationContext(), null, "输入不能为空");
            return;
        }
        if (!newPwd.trim().equals(confirmPwd.trim())){
            ToastCommon.toastShortShow(getApplicationContext(), null, "新密码与确认密码不一致");
            return;
        }
        LoadingIndicator.show(ModifyPasswordActivity.this, getString(R.string.http_notice));
        UserInformationBiz biz = new UserInformationBiz(getApplicationContext(), handler);
        biz.modifyUserPassword(MainActivity.user.getUserId(), newPwd.trim(), currentPwd.trim());
    }
}
