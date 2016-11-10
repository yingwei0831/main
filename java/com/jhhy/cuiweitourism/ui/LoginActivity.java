package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.LoginBiz;
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText etUserName;
    private EditText etPassword;

    private TextView tvToRegister;
    private TextView tvToForgetPassword;

    private Button btnLogin;

    private User registerUser;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.d(TAG, "---------handleMessage-----------");
            switch (msg.what){
                case Consts.MESSAGE_LOGIN:
                    if(0 == msg.arg1){
                        ToastUtil.show(getApplicationContext(), (String)msg.obj);
                    }else if(1 == msg.arg1) {
                        SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                        sp.saveNamePassword(etUserName.getText().toString().trim(), etPassword.getText().toString().trim());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Consts.KEY_REQUEST, (User)msg.obj);
                        MainActivity.actionStart(getApplicationContext(), bundle);
                        finish();
                    }
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    break;
                case -2:
                    ToastUtil.show(getApplicationContext(), "注册发生错误，请重试");
                    break;
            }
            LoadingIndicator.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupView();
        addListener();
    }

    private void setupView() {


        etUserName = (EditText) findViewById(R.id.et_login_user_name);
        etPassword = (EditText) findViewById(R.id.et_login_user_pwd);

        btnLogin = (Button) findViewById(R.id.btn_login_login);

        tvToRegister = (TextView) findViewById(R.id.tv_login_to_register);
        tvToForgetPassword = (TextView) findViewById(R.id.tv_login_to_forget_password);
        etUserName.setText(SharedPreferencesUtils.getInstance(getApplicationContext()).getTelephoneNumber());
//        etUserName.setText("15210656919");
//        etPassword.setText("admin123");
    }

    private void addListener() {
        btnLogin.setOnClickListener(this);
        tvToRegister.setOnClickListener(this);
        tvToForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_login:
                login();
                break;
            case R.id.tv_login_to_register:
                register();
                break;
            case R.id.tv_login_to_forget_password:
                forgetPassword();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK == resultCode){
            if(Consts.REQUEST_CODE_REGISTER == requestCode){
                registerUser = (User) data.getSerializableExtra(Consts.KEY_RESULT_DATA);
                etUserName.setText(registerUser.getUserPhoneNumber());
            }
        }
    }

    private void forgetPassword() {
        FindPasswordActivity.actionStart(getApplicationContext(), null);
    }

    private void register() {
//        RegisterActivity.actionStart(getApplicationContext(), null);
        startActivityForResult(new Intent(getApplicationContext(), RegisterActivity.class), Consts.REQUEST_CODE_REGISTER);
    }

    private void login() {
        LoadingIndicator.show(LoginActivity.this, getString(R.string.http_notice));
        String name = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        LoginBiz biz = new LoginBiz(getApplicationContext(), handler);
        biz.login(name, password );
    }

    public static void actionStart(Context context, Bundle data) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(data != null){
            intent.putExtras(data);
        }
        context.startActivity(intent);
    }
}
