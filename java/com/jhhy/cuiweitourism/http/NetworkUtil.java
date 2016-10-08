package com.jhhy.cuiweitourism.http;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

public class NetworkUtil {
    public static boolean checkNetwork(final Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // 显示dialog,让用户打开网络
            /*AlertDialog.Builder dialog = new Builder(context);
//            dialog.setTitle("无网络链接");
            dialog.setMessage("未连接网络，请检查WiFi或数据是否开启");
            dialog.setPositiveButton("打开网络", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        //ExceptionUtil.handle(e);
                        e.printStackTrace();
                    }
                }
            });
            dialog.setNegativeButton("取消", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();*/
            return true;
        }
        return false;
    }

    public static void checkNetwork3(final Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {

        }else{
            // 显示dialog,让用户打开网络
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//            dialog.setTitle("无网络链接");
            dialog.setMessage("未连接网络，请检查WiFi或数据是否开启");
            dialog.setPositiveButton("打开网络", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        //ExceptionUtil.handle(e);
                        e.printStackTrace();
                    }
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
