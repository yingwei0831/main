package com.jhhy.cuiweitourism.ui;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.ContactsBiz;
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.just.sun.pricecalendar.ToastCommon;

public class ContactPreviewActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvRight;
    private TextView tvLeft;

    private UserContacts contacts;

    private EditText etName;
    private EditText etID;
    private EditText etMobile;
    private EditText etPassport;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_MODIFY_CONTACTS: //修改联系人成功
                    preview = true;
                    etName.setCursorVisible(false);
                    etName.setFocusable(false);
                    etName.setFocusableInTouchMode(false);

                    etID.setCursorVisible(false);
                    etID.setFocusable(false);
                    etID.setFocusableInTouchMode(false);

                    etMobile.setCursorVisible(false);
                    etMobile.setFocusable(false);
                    etMobile.setFocusableInTouchMode(false);

                    etPassport.setCursorVisible(false);
                    etPassport.setFocusable(false);
                    etPassport.setFocusableInTouchMode(false);
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_preview);
        getData();
        setupView();
        addListener();
    }

    private void addListener() {
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);

        tvRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvRight.setText("编辑");

        etName = (EditText) findViewById(R.id.et_contacts_name);
        etID = (EditText) findViewById(R.id.et_contacts_id);
        etMobile = (EditText) findViewById(R.id.et_contacts_mobile);
        etPassport = (EditText) findViewById(R.id.et_contacts_passport);

        etName.setEnabled(false);
        etID.setEnabled(false);
        etMobile.setEnabled(false);
        etPassport.setEnabled(false);

        etName.setText(contacts.getContactsName());
        etID.setText(contacts.getContactsIdCard());
        etMobile.setText(contacts.getContactsMobile());
        if (contacts.getContactsPassport() != null && !"null".equals(contacts.getContactsPassport())) {
            etPassport.setText(contacts.getContactsPassport());
        }else{
            etPassport.setText("");
        }
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        contacts = (UserContacts) bundle.getSerializable("contacts");
    }

    private boolean preview = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_left:
                finish();
                break;
            case R.id.tv_title_simple_title_right:
                if (preview){ //预览状态
                    tvRight.setText("保存");
                    preview = false; //变成编辑状态

                    etName.setEnabled(true);
                    etID.setEnabled(true);
                    etMobile.setEnabled(true);
                    etPassport.setEnabled(true);

                } else { //编辑状态
                    String name = etName.getText().toString();
                    String ID = etID.getText().toString();
                    String mobile = etMobile.getText().toString();
                    String passport = etPassport.getText().toString();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ID) || TextUtils.isEmpty(mobile)){
                        ToastCommon.toastShortShow(getApplicationContext(), null, "必输项不能为空");
                        return;
                    }
                    contacts.setContactsName(name);
                    contacts.setContactsIdCard(ID);
                    contacts.setContactsMobile(mobile);
                    contacts.setContactsPassport(passport);
                    ContactsBiz biz = new ContactsBiz(getApplicationContext(), handler);
                    biz.modifyContacts(contacts);

                }
                break;
        }
    }
}
