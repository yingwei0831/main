package com.jhhy.cuiweitourism.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.ContactsBiz;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

public class ContactAddActivity extends BaseActionBarActivity {

    private String TAG = "ContactAddActivity";
    private Button btnAdd;

    private EditText etPassport;
    private EditText etMobile;
    private EditText etCardId;
    private EditText etName;

    private TextView tvUserGender; //性别
    private EditText etUserEnglishName; //英文名字

    private boolean commit;

    private UserContacts contacts;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            commit = false;
                switch (msg.what) {
                    case Consts.MESSAGE_ADD_CONTACTS: //添加联系人成功
                        if (msg.arg1 == 1) {
                            handler.postDelayed(runnable, 1000);
                        }else{
                            LoadingIndicator.cancel();
                            ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
                        }
                        break;
                    case Consts.MESSAGE_MODIFY_CONTACTS: //修改联系人成功
                        LoadingIndicator.cancel();
                        if (msg.arg1 == 1) {
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
                        }
                        break;
                    case Consts.NET_ERROR:
                        LoadingIndicator.cancel();
                        ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                        break;
                    case Consts.NET_ERROR_SOCKET_TIMEOUT:
                        LoadingIndicator.cancel();
                        ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                        break;
                }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            LoadingIndicator.cancel();
            setResult(RESULT_OK);
            finish();
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
        tvUserGender.setOnClickListener(this);
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
        tvUserGender = (TextView) findViewById(R.id.tv_contacts_gender);
        tvUserGender.setText("女");
        etUserEnglishName = (EditText) findViewById(R.id.et_contacts_english_name);
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
            case R.id.tv_contacts_gender: //TODO 选择性别
                selectUserGender();
                break;
        }
    }

    private void selectUserGender() {
        int selection = 0;
        if (!TextUtils.isEmpty(tvUserGender.getText().toString())){
            if ("女".equals(tvUserGender.getText().toString())){
                selection = 0;
            }else{
                selection = 1;
            }
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.app_logo);
        builder.setTitle(getString(R.string.tab4_account_certification_gender));
        builder.setSingleChoiceItems(new String[]{"女", "男"}, selection, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                LogUtil.e(TAG, "which = " + which);
                dialogInterface.dismiss();
                tvUserGender.setText(0 == which ? "女" : "男");
            }
        });
        builder.create().show();
    }

    private void refreshView() {
        etName.setText(contacts.getContactsName());
        etCardId.setText(contacts.getContactsIdCard());
        etMobile.setText(contacts.getContactsMobile());
        if (contacts.getContactsPassport() != null && contacts.getContactsPassport().length() != 0) {
            etPassport.setText(contacts.getContactsPassport());
        }
        if (contacts.getEnglishName() != null && contacts.getEnglishName().length() != 0){
            etUserEnglishName.setText(contacts.getEnglishName());
        }
        if (contacts.getContactsGender() != null && contacts.getContactsGender().length() != 0){
            tvUserGender.setText("0".equals(contacts.getContactsGender()) ? "女" : "男");
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
        if (!Utils.isMobileNO(mobile)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "输入的手机号码有误，请检查");
            return;
        }
        if (TextUtils.isEmpty(cardId) || cardId.length() != 18){ //Utils.is18ByteIdCardComplex(cardId)
            ToastCommon.toastShortShow(getApplicationContext(), null, "输入的身份证号码有误，请检查");
            return;
        }
        String gender = tvUserGender.getText().toString();
        if (TextUtils.isEmpty(gender)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择性别");
            return;
        }
        String englishName = etUserEnglishName.getText().toString();
        if (TextUtils.isEmpty(englishName)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入英文名字");
            return;
        }
//        if (){ //验证是否包含都是数字、英文字母
//        }
//        if (){ //名字中含有数字，返回
//            ToastCommon.toastShortShow(getApplicationContext(), null, "名字中不能含有无效字符");
//            return;
//        }
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
            contacts.setContactsPassport(passport.trim());
            contacts.setContactsGender("女".equals(gender) ? "0" : "1");
            contacts.setEnglishName(englishName.toUpperCase());
            biz.saveContacts(contacts); //添加联系人，保存
        } else {
            contacts.setContactsName(name.trim());
            contacts.setContactsIdCard(cardId.trim());
            contacts.setContactsMobile(mobile.trim());
            contacts.setContactsPassport(passport.trim());
            contacts.setContactsGender("女".equals(gender) ? "0" : "1");
            contacts.setEnglishName(englishName.toUpperCase());
            biz.modifyContacts(contacts); //修改联系人，保存
        }
    }
}
