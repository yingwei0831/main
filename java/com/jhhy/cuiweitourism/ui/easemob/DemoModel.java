package com.jhhy.cuiweitourism.ui.easemob;

import android.content.Context;



public class DemoModel {
	protected Context context = null;

	public DemoModel(Context ctx) {
		context = ctx;
		EPreferenceManager.init(context);
	}

	/**
	 * 设置当前用户的环信id
	 * 
	 * @param username
	 */
//	public void setCurrentUserName(String username) {
//		EPreferenceManager.getInstance().setCurrentUserName(username);
//	}

	/**
	 * 设置当前用户的环信密码
	 */
//	public void setCurrentUserPwd(String password){
//		EPreferenceManager.getInstance().setCurrentUserPwd(password);
//	}
	
	
	/**
	 * 获取当前用户的环信id
	 */
	public String getCurrentUsernName() {
		return EPreferenceManager.getInstance().getCurrentUsername();
	}

}
