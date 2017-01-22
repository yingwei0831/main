/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jhhy.cuiweitourism.ui.easemob;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.easeui.ui.EaseBaseActivity;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;

public class EasemobLoginActivity extends EaseBaseActivity {

	private boolean progressShow;
	private ProgressDialog progressDialog;
	private int selectedIndex = Consts.INTENT_CODE_IMG_SELECTED_DEFAULT;
	private int messageToIndex = Consts.MESSAGE_TO_DEFAULT;

	private String im;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		selectedIndex = intent.getIntExtra(Consts.INTENT_CODE_IMG_SELECTED_KEY, Consts.INTENT_CODE_IMG_SELECTED_DEFAULT);
		messageToIndex = intent.getIntExtra(Consts.MESSAGE_TO_INTENT_EXTRA, Consts.MESSAGE_TO_DEFAULT);
		im = intent.getStringExtra("im");

		if (EMChat.getInstance().isLoggedIn()) {
			progressDialog = getProgressDialog();
			progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
			progressDialog.show();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						//加载本地数据库中的消息到内存中
						EMChatManager.getInstance().loadAllConversations();
					} catch (Exception e) {
						e.printStackTrace();
					}
					toChatActivity();
				}
			}).start();
		}else{
			progressShow = true;
			progressDialog = getProgressDialog();
			progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
			if (!progressDialog.isShowing()) {
				progressDialog.show();
			}
			SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
//			final String name = sp.getTelephoneNumber();
//			final String pwd = sp.getPassword();
//			LogUtil.e("EasemobLoginActivity", "name = " + name +", password = " + pwd);
			EMChatManager.getInstance().login(MainActivity.user.getHxname(), "admin123", new EMCallBack() {
				@Override
				public void onSuccess() {
					if (!progressShow) {
						return;
					}
					DemoHelper.getInstance().setCurrentUserName(MainActivity.user.getHxname());
					DemoHelper.getInstance().setCurrentPassword("admin123");
					try {
						EMChatManager.getInstance().loadAllConversations();
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
					toChatActivity();
				}

				@Override
				public void onProgress(int progress, String status) {
				}

				@Override
				public void onError(final int code, final String message) {
					if (!progressShow) {
						return;
					}
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							Toast.makeText(EasemobLoginActivity.this,
									getResources().getString(R.string.is_contact_customer_failure_seconed) + message,
									Toast.LENGTH_SHORT).show();
							finish();
						}
					});
				}
			});

		}
	}

	private void toChatActivity() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (!EasemobLoginActivity.this.isFinishing())
					progressDialog.dismiss();
				// 进入主页面
				startActivity(new Intent(EasemobLoginActivity.this, ChatActivity.class).putExtra(
						Consts.INTENT_CODE_IMG_SELECTED_KEY, selectedIndex).putExtra(
						Consts.MESSAGE_TO_INTENT_EXTRA, messageToIndex).putExtra("im", im));
				finish();
			}
		});
	}

	private ProgressDialog getProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(EasemobLoginActivity.this);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					progressShow = false;
				}
			});
		}
		return progressDialog;
	}


}
