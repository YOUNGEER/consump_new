package com.youyou.xiaofeibao.version2.request;

import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;

/**
 * 作者：young on 2016/10/13 09:47
 */

public class BaseRequestObject {

    private String tokenid = PreferencesConfig.v2_token.get();

    private String platform = "0";


    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
