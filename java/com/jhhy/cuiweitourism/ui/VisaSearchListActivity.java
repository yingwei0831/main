package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaHotAnyCountryGridAdapter;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

public class VisaSearchListActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "VisaSearchListActivity";

    private GridView gridView;
    private VisaHotAnyCountryGridAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_visa_search_list);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getIntent().getStringExtra("name"));
        gridView = (GridView) findViewById(R.id.gridview_visa_list);
        mAdapter = new VisaHotAnyCountryGridAdapter(getApplicationContext(), VisaMainActivity.searchList);
        gridView.setAdapter(mAdapter);
    }

    @Override
    protected void addListener() {
        super.addListener();
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogUtil.e(TAG, "position = " + i +", l = " + l); //进入详情
        String id =  VisaMainActivity.searchList.get(i).getVisaId();
        Intent intent = new Intent(getApplicationContext(), VisaItemDetailActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_VISA_DETAIL);
    }

    private final int VIEW_VISA_DETAIL = 1801; //热门签证详情

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == VIEW_VISA_DETAIL) {

            }
        }
    }
}
