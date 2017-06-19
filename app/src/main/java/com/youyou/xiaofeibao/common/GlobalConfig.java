package com.youyou.xiaofeibao.common;

import android.content.Context;

/**
 * Created by yun on 16/1/7.
 */
public class GlobalConfig {
    private static Context appContext;

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
