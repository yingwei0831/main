package cn.sharesdk.onekeyshare;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;

/**
 * Created by jiahe008 on 2016/10/31.
 */
public class ShareSDKLogoAdapter extends AuthorizeAdapter {
    @Override
    public void onCreate() {
        super.onCreate();
        hideShareSDKLogo();
    }
}
