package com.youyou.xiaofeibao.version2.request.authlogin;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2017/2/21 14:17
 */


public class AuthLoginRequestObject extends BaseRequestObject {
  private AuthLoginRequestParam param;

    public AuthLoginRequestParam getParam() {
        return param;
    }

    public void setParam(AuthLoginRequestParam param) {
        this.param = param;
    }
}
