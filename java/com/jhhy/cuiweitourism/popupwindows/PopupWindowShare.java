package com.jhhy.cuiweitourism.popupwindows;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.picture.TestPicActivity;
import com.jhhy.cuiweitourism.ui.Tab4AccountCertificationActivity;
import com.jhhy.cuiweitourism.ui.WelcomeActivity;

import java.io.File;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowShare extends PopupWindow implements View.OnClickListener {

    private Activity activity;

    public PopupWindowShare(final Activity activity, View parent) {
        this.activity = activity;

        View view = View.inflate(activity, R.layout.item_popupwindow_share, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();


        Button bt3 = (Button) view.findViewById(R.id.btn_share_cancel);
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        RadioButton rbWechat = (RadioButton) view.findViewById(R.id.rb_share_wechat);
        RadioButton rbWechatMoments = (RadioButton) view.findViewById(R.id.rb_share_wechat_moments);
        RadioButton rbSina = (RadioButton) view.findViewById(R.id.rb_share_sina);
        RadioButton rbQQ = (RadioButton) view.findViewById(R.id.rb_share_qq);

        rbWechat.setOnClickListener(this);
        rbWechatMoments.setOnClickListener(this);
        rbSina.setOnClickListener(this);
        rbQQ.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_share_wechat:
                showShare(activity);
                break;
            case R.id.rb_share_wechat_moments:
                showShare(activity);
                break;
            case R.id.rb_share_sina:
                shareSina();
                break;
            case R.id.rb_share_qq:
                showShare(activity);
                break;
        }
        dismiss();
    }

    private void shareSina() {

    }

    private void showShare(Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("翠微旅游");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(context.getString(R.string.share_text));
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(WelcomeActivity.TEST_IMAGE);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://cwly1118.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }

}
