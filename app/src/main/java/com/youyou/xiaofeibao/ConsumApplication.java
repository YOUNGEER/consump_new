package com.youyou.xiaofeibao;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.pgyersdk.crash.PgyCrashManager;
import com.youyou.xiaofeibao.common.GlobalConfig;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.baidumap.LocationService;

import cn.jpush.android.api.JPushInterface;

/**
 * @author zhaoyunhai 16/3/22.
 */
public class ConsumApplication extends Application {

    private static ConsumApplication instance;

    public LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        GlobalConfig.setAppContext(this);
        JPushInterface.setDebugMode(true);// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        locationService = new LocationService(getApplicationContext());
        SDKInitializer.initialize(this.getApplicationContext());

        ImageUtils.getBitmapUtils(this.getApplicationContext());

        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=59292fb2");



       /* BMapManager bMapManager = new BMapManager();
        bMapManager.init();*/

//        if (LogManager.getInstance().isLogFile()) {
//            new LogcatThread().start();
//        }

        //蒲公因注册
        PgyCrashManager.register(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static ConsumApplication getInstance() {
        return instance;
    }
}
