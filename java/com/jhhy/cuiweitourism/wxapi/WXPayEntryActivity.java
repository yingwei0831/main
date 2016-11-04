package com.jhhy.cuiweitourism.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.BaseActivity;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by jiahe008 on 2016/11/4.
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private static final String APP_ID = "your app id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp baseResp) {
        //        0 支付成功
//        -1 发生错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//        -2 用户取消 发生场景：用户不支付了，点击取消，返回APP。
        LogUtil.e(TAG, "onPayFinish, errCode = " + baseResp.errCode);// 支付结果码
    }

}
