package com.jhhy.cuiweitourism.ui.easemob;

import android.content.Intent;
import android.os.Bundle;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.ui.BaseActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.ui.MainActivity;


/**
 * 聊天页面，需要fragment的使用{@link EaseChatFragment}
 */
public class ChatActivity extends BaseActivity {

    public static ChatActivity activityInstance;
    private ChatFragment chatFragment;
    String toChatUsername;
    String sjmc;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        toChatUsername = getIntent().getStringExtra("im");
        sjmc = getIntent().getStringExtra("sjmc");

        activityInstance = this;
        // 聊天人或群id
//        toChatUsername = MainActivity.user.getHxname();//15210656919/MainActivity.user.getUserPhoneNumber(); //HelpDeskPreferenceUtils.getInstance(this).getSettingCustomerAccount();
        // 可以直接new EaseChatFratFragment使用
        chatFragment = new ChatFragment();
        Intent intent = getIntent();
        intent.putExtra(Consts.EXTRA_USER_ID, toChatUsername);
        intent.putExtra("sjmc", sjmc);
        intent.putExtra(Consts.EXTRA_SHOW_USERNICK, false); //此处是设置不显示客服昵称
        // 传入参数
        chatFragment.setArguments(intent.getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public void sendRobotMessage(String txtContent, String menuId) {
        chatFragment.sendRobotMessage(txtContent, menuId);
    }



}
