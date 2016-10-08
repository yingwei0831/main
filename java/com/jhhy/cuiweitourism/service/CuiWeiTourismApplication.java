package com.jhhy.cuiweitourism.service;

import android.app.Application;


import com.jhhy.cuiweitourism.ui.easemob.DemoHelper;

import org.xutils.x;

/**
 * Created by jiahe008 on 2016/9/2.
 */
public class CuiWeiTourismApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this); // 这一步之后, 我们就可以在任何地方使用x.app()来获取Application的实例了.
        x.Ext.setDebug(true); // 是否输出debug日志

        // 初始化ImageLoader
//        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_stub) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
////                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
//                .build(); // 创建配置过得DisplayImageOption对象
//
//        //创建默认的ImageLoader配置参数
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(options)
//                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
//        //Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config);

        // init demo helper
        DemoHelper.getInstance().init(this);

        //环信
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        //初始化
//        EMClient.getInstance().init(this, options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
//        //注册一个监听连接状态的listener
//        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
//            @Override
//            public void onConnected() {
//
//            }
//
//            @Override
//            public void onDisconnected(final int error) {
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
//                        if(error == EMError.USER_REMOVED){
//                            // 显示帐号已经被移除
//                            LogUtil.e("CuiWeiTourismApplication", "环信帐号已经被移除");
//                        }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                            // 显示帐号在其他设备登录
//                            LogUtil.e("CuiWeiTourismApplication", "环信帐号在其他设备登录");
//                        } else {
//                            if (NetUtils.hasNetwork(getApplicationContext())) {
//                                //连接不到聊天服务器
//                                LogUtil.e("CuiWeiTourismApplication", "连接不到聊天服务器");
//                            }else {
//                                //当前网络不可用，请检查网络设置
//                                LogUtil.e("CuiWeiTourismApplication", "当前网络不可用，请检查网络设置");
//                            }
//                        }
////                    }
////                });
//            }
//        });
    }

}
