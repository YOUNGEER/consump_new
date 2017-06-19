package com.youyou.xiaofeibao.common;

/**
 * Created by liuyy on 2016/2/2.
 */
public class SDCardUtils {

    public static boolean existSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
