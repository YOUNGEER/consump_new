package com.youyou.xiaofeibao.version2.pay.pwd;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 作者：young on 2016/12/21 14:19
 */

public class Util {
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }
}
