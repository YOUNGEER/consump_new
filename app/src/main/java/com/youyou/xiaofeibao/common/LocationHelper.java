package com.youyou.xiaofeibao.common;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.youyou.xiaofeibao.ConsumApplication;
import com.youyou.xiaofeibao.framework.Log.LogDoumee;
import com.youyou.xiaofeibao.framework.Log.LogTag;

import java.util.ArrayList;

/**
 * @author zhaoyunhai on 16/3/29.
 */
public class LocationHelper {

    private final LocationClient mLocationClient;
    private final BDLocationListener mLocationListener;
    private final ArrayList<LocationSucessListener> mListener;
    private BDLocation mLocation;
    private boolean autoClose = false;

    /**
     * 获得单例
     *
     * @return
     */
    public static LocationHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void addListener(LocationSucessListener locationSucessListener) {
        mListener.add(locationSucessListener);
    }

    public void removeListener(LocationSucessListener locationSucessListener) {
        mListener.remove(locationSucessListener);
    }

    /**
     * 单例持有器
     */
    private static final class InstanceHolder {
        private static final LocationHelper INSTANCE = new LocationHelper();
    }

    /**
     * 禁止构造
     */
    private LocationHelper() {

        mListener = new ArrayList<>();
        mLocationClient = new LocationClient(ConsumApplication.getInstance().getApplicationContext());
        mLocationListener = new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mLocation = bdLocation;
                Address address = bdLocation.getAddress();
                for (LocationSucessListener listener : mListener) {
                    listener.onLocation(bdLocation);
                }
                if (autoClose) {
                    mLocationClient.stop();
                    mLocationClient.unRegisterLocationListener(mLocationListener);
                }
                if (LogDoumee.isLoggable(LogTag.LOCATION, LogDoumee.INFO)) {
                    LogDoumee.i(LogTag.LOCATION, "address");
                }
            }
        };
        mLocationClient.registerLocationListener(mLocationListener);
        initLocation();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1100;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setTimeOut(5000);
        mLocationClient.setLocOption(option);
    }

    public void startLocation() {
        mLocationClient.registerLocationListener(mLocationListener);
        autoClose = false;
        mLocationClient.start();
    }

    public void startLocationAutoClose() {
        mLocationClient.registerLocationListener(mLocationListener);
        autoClose = true;
        mLocationClient.start();
    }

    public void stopLocation() {
        mLocationClient.stop();
    }

    public BDLocation getCurrentLocation() {
        return mLocation;
    }

    public interface LocationSucessListener {
        void onLocation(BDLocation bdLocation);

    }

    public LocationClient getmLocationClient() {
        return mLocationClient;
    }
}
