package com.youyou.xiaofeibao.version2.response.authlogin;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2017/2/21 14:19
 */


public class AuthLoginResponseObject  extends BaseResponseObject{
    private AuthLoginResponseParam data;

    public AuthLoginResponseParam getData() {
        return data;
    }

    public void setData(AuthLoginResponseParam data) {
        this.data = data;
    }
}
