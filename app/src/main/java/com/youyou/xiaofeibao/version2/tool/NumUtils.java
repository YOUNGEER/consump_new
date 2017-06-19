package com.youyou.xiaofeibao.version2.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：young on 2016/10/20 10:13
 */

public class NumUtils {
    /**
     * 验证手机号是否合法
     */

    public static boolean checkPhoneNum(String phone) {
        if("".equals(phone)){
            return false;
        }
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        if (!m.matches()) {
            return false;
        }
        return true;
    }
}
