package com.youyou.xiaofeibao.version2.request.register;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/10/20 11:04
 */

public class RegisterRequestObject extends BaseRequestObject {

    private RegisterRequestParam param;

    public RegisterRequestParam getParam() {
        return param;
    }

    public void setParam(RegisterRequestParam param) {
        this.param = param;
    }

}
