package com.jhhy.cuiweitourism.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.UserContactsListAdapter;
import com.jhhy.cuiweitourism.biz.ContactsBiz;
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;


public class UserContactsListActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    private ListView listView;
    private TextView imageViewRight;;
    private Drawable drawableRight;
    private TextView imageViewLeft;
    private TextView tvTitle;

    private List<UserContacts> lists = new ArrayList<>();
    private UserContactsListAdapter adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingIndicator.cancel();
            switch (msg.what){
                case Consts.MESSAGE_GET_CONTACTS:
                    lists = (List<UserContacts>) msg.obj;
                    if (lists == null || lists.size() == 0) {
                        ToastCommon.toastShortShow(getApplicationContext(), null, "联系人列表为空，请添加联系人");
                    } else {
                        adapter.setData(lists);
                    }
                    break;
                case Consts.MESSAGE_DELETE_CONTACTS:
                    getData();
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contacts_list);

        setupView();
        addListener();
        getData();
    }

    private void getData() {
        ContactsBiz biz = new ContactsBiz(getApplicationContext(), handler);
        biz.getContacts("1"); //MainActivity.user.getUserId()
    }

    private void addListener() {
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        imageViewLeft.setOnClickListener(this);
        imageViewRight.setOnClickListener(this);
    }

    private void setupView() {
        imageViewRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        imageViewRight.setText("");
        imageViewLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);

        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        drawableRight = ContextCompat.getDrawable(UserContactsListActivity.this, R.mipmap.arrow_right_green);
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());

        imageViewRight.setCompoundDrawables(null, null, drawableRight, null);

        tvTitle.setText("常用联系人");

        listView = (ListView) findViewById(R.id.listview);
        adapter = new UserContactsListAdapter(getApplicationContext(), lists);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), ContactPreviewActivity.class);
//        Intent intent = new Intent(getApplicationContext(), ContactAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contacts", lists.get(i));
        intent.putExtras(bundle);
        startActivityForResult(intent, Consts.REQUEST_CODE_EDIT_CONTACT); //预览、编辑联系人信息
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Consts.REQUEST_CODE_EDIT_CONTACT){ //编辑、查看联系人信息
            if (resultCode == RESULT_OK){
                getData();
            }
        } else if (requestCode == ADD_CONTACTS){
            if (resultCode == RESULT_OK){
                getData();
            }
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        //TODO 长摁删除
        AlertDialog.Builder builder = new AlertDialog.Builder(UserContactsListActivity.this);
        builder.setMessage("确认删除当前联系人吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                deleteContact(i);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
        return true;
    }

    private void deleteContact(int position) {
        LoadingIndicator.show(UserContactsListActivity.this, getString(R.string.http_notice));
        ContactsBiz biz = new ContactsBiz(getApplicationContext(), handler);
        biz.deleteContacts(lists.get(position).getContactsId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_left: //返回
                finish();
                break;
            case R.id.tv_title_simple_title_right: //添加联系人
                Intent intent = new Intent(getApplicationContext(), ContactAddActivity.class);
                startActivityForResult(intent, ADD_CONTACTS);
                break;
        }
    }

    private int ADD_CONTACTS = 1601; //添加联系人


}
