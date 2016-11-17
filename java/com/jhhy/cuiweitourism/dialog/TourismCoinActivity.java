package com.jhhy.cuiweitourism.dialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.ui.BaseActivity;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.just.sun.pricecalendar.ToastCommon;

public class TourismCoinActivity extends BaseActivity implements View.OnClickListener {

    private int needScore;

    private TextView tvNumberCoin;
    private TextView tvNeedCoin;
    private EditText etSetCoinNumber;
    private Button btnCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_coin);
        getData();
        setupView();
        addListener();
    }

    private void setupView() {
        tvNumberCoin = (TextView) findViewById(R.id.tv_number_coin);
        tvNumberCoin.setText("账户共" + MainActivity.user.getUserScore()+"个旅游币");
        tvNeedCoin = (TextView) findViewById(R.id.tv_need_coin);
        tvNeedCoin.setText("最多折扣￥" + needScore+ "哦~");
        etSetCoinNumber = (EditText) findViewById(R.id.et_input_coin);
        btnCommit = (Button) findViewById(R.id.btn_coin_confirm);
    }

    private void addListener() {
        btnCommit.setOnClickListener(this);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        String scroe = bundle.getString("needScore");
        if (scroe == null || scroe.length() == 0){
            needScore = 0;
        }else {
            needScore = Integer.parseInt(scroe);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_coin_confirm:
                confirmCoin();
                break;
        }
    }

    private void confirmCoin() {
        int scroe = Integer.parseInt(MainActivity.user.getUserScore());
        String num = etSetCoinNumber.getText().toString();
        if (TextUtils.isEmpty(num)){ //输入内容为空
            if (scroe > 0){ //本人持有积分 > 0
                ToastCommon.toastShortShow(getApplicationContext(), null, "输入不能为空");
                return;
            } else{ //本人持有积分为0
                setResult(RESULT_CANCELED);
                finish();
            }
        }else{ //输入内容不为空
            if (Integer.parseInt(num.trim()) > scroe){ //输入的积分 > 本人持有积分
                ToastCommon.toastShortShow(getApplicationContext(), null, "您的积分不足，请重试");
                return;
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("score", Integer.parseInt(num.trim()));
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }


    }
}
