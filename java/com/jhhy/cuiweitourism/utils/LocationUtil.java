package com.jhhy.cuiweitourism.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

/**
 * Created by jiahe008 on 2016/11/1.
 */
public class LocationUtil extends Thread implements AMapLocationListener {

    private String TAG = LocationUtil.class.getSimpleName();

    private Context context;
    private Handler handler;
    private int count = 0;

    //    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    public LocationUtil(Context context, Handler handler) {
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(context);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                LogUtil.e(TAG, "定位成功：" + aMapLocation.toString());
                Message msg = new Message();
                msg.what = Consts.MESSAGE_LOCATION;
                msg.obj = aMapLocation;
                handler.sendMessage(msg);
                stopMap();
            } else {
                if (aMapLocation.getErrorCode() == 4) { //网络连接异常
                    Message msg = new Message();
                    msg.what = Consts.MESSAGE_LOCATION_FAILED;
                    msg.obj = aMapLocation;
                    handler.sendMessage(msg);
                    stopMap();
                }
                count++;
                if (count >= 5) {
                    Message msg = new Message();
                    msg.what = Consts.MESSAGE_LOCATION_FAILED;
                    msg.obj = aMapLocation;
                    handler.sendMessage(msg);
                    stopMap();
                }
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                LogUtil.e(TAG, errText);
            }
        }
    }

    private void stopMap() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        LogUtil.e(TAG, "locationThread Stop!");
    }
}
