package com.youyou.xiaofeibao.version2.response.appversion;

/**
 * 作者：young on 2016/12/23 14:59
 */

public class AppResponseData {

    /**
     * app_version : 2.0
     * new_apk : http://192.168.1.15:8080/xfb/xiaofeibao.apk
     */

    private String app_version;
    private String new_apk;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getNew_apk() {
        return new_apk;
    }

    public void setNew_apk(String new_apk) {
        this.new_apk = new_apk;
    }
}
