package com.youyou.xiaofeibao.version2.response.login;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/11/10 14:logo_pic
 */

public class LoginResponseObject extends BaseResponseObject {

    private LoginResponseParam data;

    public LoginResponseParam getData() {
        return data;
    }

    public void setData(LoginResponseParam data) {
        this.data = data;
    }
}
