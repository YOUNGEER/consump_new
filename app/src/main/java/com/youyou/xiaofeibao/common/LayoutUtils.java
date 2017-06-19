package com.youyou.xiaofeibao.common;

import android.content.Context;


/**
 * Created by liuyy on 2016/1/17.
 */
public class LayoutUtils {

    public static int getDimenPx(int id) {
        return (int) GlobalConfig.getAppContext().getResources().getDimension(id);
    }

    public static int dip2px(float dipValue) {
        final float scale = GlobalConfig.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
