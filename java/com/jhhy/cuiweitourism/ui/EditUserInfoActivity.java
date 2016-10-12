package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.just.sun.pricecalendar.ToastCommon;


public class EditUserInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvTitleLeft;
    private TextView tvTitleRight;

    private TextView tvNameTitle;
    private EditText etName;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_MODIFY_USER_INFO: //修改用户昵称
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("nickname", etName.getText().toString().trim());
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
        setContentView(R.layout.activity_edit_user_info);

        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitle.setText("个人资料");
        tvTitleRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setText("保存");
        tvTitleRight.setTextColor(getResources().getColor(R.color.colorActionBar));
        tvTitleLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);


        tvNameTitle = (TextView) findViewById(R.id.activity_edit_use_info_tv_modify_name);
        etName = (EditText) findViewById(R.id.activity_edit_use_info_et_modify_edit);

        tvNameTitle.setText("昵称");
        etName.setText(MainActivity.user.getUserNickName());


        addListener();
    }

    private void addListener() {
        tvTitleLeft.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_right:
                save();
                break;
            case R.id.tv_title_simple_title_left:
                finish();
                break;
        }
    }

    private void save() {
        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastCommon.toastShortShow(getApplicationContext(), null, getString(R.string.empty_input));
            return;
        }
       //修改昵称
            UserInformationBiz biz = new UserInformationBiz(getApplicationContext(), handler);
            biz.modifyUserInfo(MainActivity.user.getUserId(), name.trim(), MainActivity.user.getUserGender());

    }
}
