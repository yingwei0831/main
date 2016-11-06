package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.ContactsBiz;
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.ToastCommon;

public class ContactAddActivity extends BaseActionBarActivity {

    private Button btnAdd;

    private EditText etPassport;
    private EditText etMobile;
    private EditText etCardId;
    private EditText etName;

    private boolean commit;

    private UserContacts contacts;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingIndicator.cancel();
            commit = false;
                switch (msg.what) {
                    case Consts.MESSAGE_ADD_CONTACTS: //添加联系人成功
                        if (msg.arg1 == 1) {
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
                        }
                        break;
                    case Consts.MESSAGE_MODIFY_CONTACTS: //修改联系人成功
                        if (msg.arg1 == 1) {
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
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
        setContentView(R.layout.activity_add_contact);
        getData();
        super.onCreate(savedInstanceState);
//        setupView();
//        addListener();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                contacts = (UserContacts) bundle.getSerializable("contacts");
            }
        }
    }

    @Override
    protected void addListener() {
        super.addListener();
        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.contact_add_title));
        btnAdd = (Button) findViewById(R.id.btn_add_save_contacts);

        etPassport = (EditText) findViewById(R.id.et_contacts_passport);
        etMobile = (EditText) findViewById(R.id.et_contacts_mobile);
        etCardId = (EditText) findViewById(R.id.et_contacts_card_id);
        etName = (EditText) findViewById(R.id.et_contacts_name);
        if (contacts != null) {
            refreshView();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_add_save_contacts:
                saveContacts();
                break;
        }
    }

    private void refreshView() {
        etName.setText(contacts.getContactsName());
        etCardId.setText(contacts.getContactsIdCard());
        etMobile.setText(contacts.getContactsMobile());
        if (contacts.getContactsPassport() != null) {
            etPassport.setText(contacts.getContactsPassport());
        }
    }

    private void saveContacts() {
        if (commit) return;
        String name = etName.getText().toString();
        String cardId = etCardId.getText().toString();
        String mobile = etMobile.getText().toString();
        String passport = etPassport.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cardId) || TextUtils.isEmpty(mobile)) {
            ToastCommon.toastShortShow(getApplicationContext(), null, "必填项不能为空");
            return;
        }
        if (commit) return;
        commit = true;
        LoadingIndicator.show(ContactAddActivity.this, getString(R.string.http_notice));
        ContactsBiz biz = new ContactsBiz(getApplicationContext(), handler);
        if (contacts == null) {
            UserContacts contacts = new UserContacts();
            contacts.setContactsMemberId(MainActivity.user.getUserId());
            contacts.setContactsName(name.trim());
            contacts.setContactsIdCard(cardId.trim());
            contacts.setContactsMobile(mobile.trim());
            if (passport != null) {
                contacts.setContactsPassport(passport.trim());
            }
            biz.saveContacts(contacts); //添加联系人，保存
        } else {
            contacts.setContactsName(name.trim());
            contacts.setContactsIdCard(cardId.trim());
            contacts.setContactsMobile(mobile.trim());
            if (passport != null) {
                contacts.setContactsPassport(passport.trim());
            }
            biz.modifyContacts(contacts); //修改联系人，保存
        }
    }
}
