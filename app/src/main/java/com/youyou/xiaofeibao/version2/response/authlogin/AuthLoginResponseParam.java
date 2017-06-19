package com.youyou.xiaofeibao.version2.response.authlogin;/**
 * 作者：young on 2017/2/24 16:09
 */


public class AuthLoginResponseParam {
    private String status;
    private AuthLoginResponseData user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AuthLoginResponseData getUser() {
        return user;
    }

    public void setUser(AuthLoginResponseData user) {
        this.user = user;
    }
}
