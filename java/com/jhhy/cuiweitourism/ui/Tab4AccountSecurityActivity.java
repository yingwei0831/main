package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;


public class Tab4AccountSecurityActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private TextView tvModifyPassword;
    private TextView tvModifyMobile;
    private TextView tvGoCertification;

    private TextView tvMobileCertification; //手机已认证
    private TextView tvTrueCertification; //实名制已认证

    private Button btnLoginOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_account_security);
        setupView();

        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.fragment_mine_account_security));
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        tvModifyPassword = (TextView) findViewById(R.id.tv_modify_password);
        tvModifyMobile = (TextView) findViewById(R.id.tv_modify_mobile);
        tvGoCertification = (TextView) findViewById(R.id.tv_certification_go);

        tvMobileCertification = (TextView) findViewById(R.id.tv_certification_mobile);
        tvTrueCertification = (TextView) findViewById(R.id.tv_certification_true);

        btnLoginOut = (Button) findViewById(R.id.btn_login_out);

        tvTrueCertification.setText(MainActivity.user.getStatus());
        if ("已认证".equals(MainActivity.user.getStatus())){
            tvTrueCertification.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorActionBar));
        }
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        tvModifyPassword.setOnClickListener(this);
        tvModifyMobile.setOnClickListener(this);
        tvGoCertification.setOnClickListener(this);

        btnLoginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_out:
                logOut();
                break;
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_modify_password:
                Intent intentPwd = new Intent(getApplicationContext(), ModifyPasswordActivity.class);
                startActivityForResult(intentPwd, MODIFY_PASSWORD);
                break;
            case R.id.tv_modify_mobile:
                Intent intentTel = new Intent(getApplicationContext(), ModifyTelephoneNumberActivity.class);
                startActivityForResult(intentTel, MODIFY_MOBILE);
                break;
            case R.id.tv_certification_go:
                Intent intentGo = new Intent( getApplicationContext(), Tab4AccountCertificationActivity.class);

                startActivityForResult(intentGo, TRUE_CERTIVICATION);
                break;
        }
    }

    private int MODIFY_PASSWORD = 1181; //修改登录密码
    private int MODIFY_MOBILE = 1182; //修改手机号
    private int TRUE_CERTIVICATION = 1183; //实名认证


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_PASSWORD){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                String pwd = bundle.getString("password");
                MainActivity.user.setUserPassword(pwd);
                SharedPreferencesUtils.getInstance(getApplicationContext()).savePassword(pwd);
            }
        }else if(requestCode == MODIFY_MOBILE){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                String mobile = bundle.getString("mobile");
                MainActivity.user.setUserPhoneNumber(mobile);
                SharedPreferencesUtils.getInstance(getApplicationContext()).saveTelephoneNumber(mobile);
            }
        }else if(requestCode == TRUE_CERTIVICATION){
            if (resultCode == RESULT_OK){

            }
        }
    }

    private void logOut() {
        SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
        sp.savePassword(null);
        Bundle bundle = new Bundle();
        bundle.putInt("logout", 1);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
