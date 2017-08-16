package com.youyou.xiaofeibao;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
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

        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(this, "da13efb886", false);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);


        // 安装tinker
        Beta.installTinker();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static ConsumApplication getInstance() {
        return instance;
    }
}
