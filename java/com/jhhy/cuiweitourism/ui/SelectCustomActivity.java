package com.jhhy.cuiweitourism.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.ContactsListAdapter;
import com.jhhy.cuiweitourism.biz.ContactsBiz;
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class SelectCustomActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private String TAG = getClass().getSimpleName();

    private TextView tvTitle;
    private TextView tvRight;
    private TextView tvLeft;

    private TextView tvSelectNumber;
    private int selectNumber = 0; //已选择人数
    private TextView tvTotalNumber;
    private int totalNumber; //最多可选择的联系人个数
    private List<Integer> listSelectionCont; //已选择的联系人位置

    private int type; // 13:火车票选择联系人

    private ListView listViewContact;
    private ContactsListAdapter adapter;
    private List<UserContacts> listContacts = new ArrayList<>();

    private RelativeLayout layoutAdd;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Consts.MESSAGE_GET_CONTACTS:
                    if (msg.arg1 == 1) {
                        listContacts = (List<UserContacts>) msg.obj;
                        if (listContacts == null || listContacts.size() == 0) {
                            ToastCommon.toastShortShow(getApplicationContext(), null, "联系人列表为空，请添加联系人");
                        } else {
                            adapter.setData(listContacts);
                        }
                    } else {
                        ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
                    }
                    break;
                case Consts.MESSAGE_DELETE_CONTACTS:
                    getInternetData();
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    break;
            }

            LoadingIndicator.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_custom);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");
        totalNumber = bundle.getInt("number");
        if (type == 13) {
            if (totalNumber > 10) {
                totalNumber = 10;
            }
        }
        getInternetData();
    }

    private void getInternetData() {
        LoadingIndicator.show(SelectCustomActivity.this, getString(R.string.http_notice));
        ContactsBiz biz = new ContactsBiz(getApplicationContext(), handler);
        biz.getContacts(MainActivity.user.getUserId());
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitle.setText(getString(R.string.select_custom_title));
        tvRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvRight.setText(getString(R.string.tab1_inner_travel_pop_commit));
        tvRight.setTextColor(getResources().getColor(R.color.colorActionBar));
        tvLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);

        tvSelectNumber = (TextView) findViewById(R.id.tv_select_custom_number);
        tvSelectNumber.setText(String.valueOf(selectNumber));

        tvTotalNumber = (TextView) findViewById(R.id.tv_select_custom_total);
        tvTotalNumber.setText("/" + totalNumber + "游客");

        listViewContact = (ListView) findViewById(R.id.lv_select_custom_contact);
        adapter = new ContactsListAdapter(getApplicationContext(), listContacts) {
            /**
             * @param position 列表中位置
             */
            @Override
            public void viewContact(int position) { //单击左侧图片，进入编辑联系人页面
                Intent intent = new Intent(getApplicationContext(), ContactAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contacts", listContacts.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, Consts.REQUEST_CODE_EDIT_CONTACT); //编辑联系人信息
            }

            /**
             * @param position 列表中位置
             */
            @Override
            public void refreshView(int position) {
                listSelectionCont = adapter.getSelection();
                if (listSelectionCont.contains(position)) { //是否包含
                    adapter.removeSelection(position); //如果包含，则移除它所在位置的对象
                } else {
                    if (listSelectionCont.size() < totalNumber) { //已选择的总数 < 可选择的总数
                        adapter.addSelection(position);
                    } else { //已选择的总数 >= 可选择的总数
                        return;
                    }
                }
                listSelectionCont = adapter.getSelection();
                tvSelectNumber.setText(String.valueOf(listSelectionCont.size()));
            }

        };
        listViewContact.setAdapter(adapter);

        layoutAdd = (RelativeLayout) findViewById(R.id.layout_add_contacts);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {

        } else {
            if (requestCode == Consts.REQUEST_CODE_EDIT_CONTACT) { //编辑联系人信息
                getData();
            } else if (requestCode == Consts.REQUEST_CODE_ADD_CONTACT) { //添加联系人信息
                getData();
            }
        }
    }

    private void addListener() {
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        layoutAdd.setOnClickListener(this);

        listViewContact.setOnItemLongClickListener(this);
        listViewContact.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_simple_title_right:
                selectFinish();
                break;
            case R.id.tv_title_simple_title_left:
                finish();
                break;
            case R.id.layout_add_contacts:
                Intent intent = new Intent(getApplicationContext(), ContactAddActivity.class);
                startActivityForResult(intent, Consts.REQUEST_CODE_ADD_CONTACT); //添加联系人信息
                break;
        }
    }

    /**
     * 选择联系人完成
     */
    private void selectFinish() {
//        ArrayList<Integer> selectionList = (ArrayList<Integer>) adapter.getSelection();
        if (listSelectionCont.size() == 0) {
            setResult(RESULT_CANCELED);
        } else {
            ArrayList<UserContacts> listContacts = new ArrayList<>();
            for (int i = 0; i < listSelectionCont.size(); i++) {
                listContacts.add(this.listContacts.get(listSelectionCont.get(i))); //在某些位置上的联系人
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("selection", listContacts);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private int deletePosition;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectCustomActivity.this);
        builder.setMessage("确认删除当前联系人吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deletePosition = i;
                deleteContact();
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

    private void deleteContact() {
        LoadingIndicator.show(SelectCustomActivity.this, getString(R.string.http_notice));
        ContactsBiz biz = new ContactsBiz(getApplicationContext(), handler);
        biz.deleteContacts(listContacts.get(deletePosition).getContactsId());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //联系人信息预览
//        ContactPreviewActivity
    }
}
