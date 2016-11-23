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
import com.jhhy.cuiweitourism.net.biz.MemberCenterActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.MemberIcon;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.MemberIconNum;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.BaseActivity;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;

public class TourismCoinActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "TourismCoinActivity";
    private int needScore;
    private int score;

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
        getInternetData();
        addListener();
    }

    private void getInternetData() {
//        LoadingIndicator.show(this, getString(R.string.http_notice));
        MemberCenterActionBiz biz = new MemberCenterActionBiz();
        biz.memberTravelIcon(new MemberIcon(MainActivity.user.getUserId()), new BizGenericCallback<MemberIconNum>() {
            @Override
            public void onCompletion(GenericResponseModel<MemberIconNum> model) {
                LogUtil.e(TAG, " memberTravelIcon :" + model.body.toString());
                if ("0000".equals(model.headModel.res_code)) {
                    score = Integer.parseInt(model.body.lvb);
                    tvNumberCoin.setText("账户共" + score +"个旅游币");
                }else if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
//                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求我的旅游币出错");
                }
                LogUtil.e(TAG, " memberTravelIcon :" + error.toString());
//                LoadingIndicator.cancel();
            }
        });
    }

    private void setupView() {
        tvNumberCoin = (TextView) findViewById(R.id.tv_number_coin);
//        tvNumberCoin.setText("账户共" + MainActivity.user.getUserScore()+"个旅游币");
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
//        int scroe = Integer.parseInt(MainActivity.user.getUserScore());
        String num = etSetCoinNumber.getText().toString();
        if (TextUtils.isEmpty(num)){ //输入内容为空
            if (score > 0){ //本人持有积分 > 0
                ToastCommon.toastShortShow(getApplicationContext(), null, "输入不能为空");
                return;
            } else{ //本人持有积分为0
                setResult(RESULT_CANCELED);
                finish();
            }
        }else{ //输入内容不为空
            if (Integer.parseInt(num.trim()) > score){ //输入的积分 > 本人持有积分
                ToastCommon.toastShortShow(getApplicationContext(), null, "您的积分不足，请重试");
                return;
            }
            if (Integer.parseInt(num.trim()) > needScore){ //输入的积分 > 该线路最高能用积分
                ToastCommon.toastShortShow(getApplicationContext(), null, "线路最高可使用积分" + needScore +"哦~");
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
