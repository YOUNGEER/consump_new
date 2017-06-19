package com.youyou.xiaofeibao.version2.request.login;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/10/20 09:16
 */

public class LoginRequestObject extends BaseRequestObject {

    private LoginRequestParam param;

    public LoginRequestParam getParam() {
        return param;
    }

    public void setParam(LoginRequestParam param) {
        this.param = param;
    }
}
