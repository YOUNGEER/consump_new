package com.youyou.xiaofeibao.version2.response.bind;/**
 * 作者：young on 2017/2/24 16:42
 */

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.authlogin.AuthLoginResponseData;


public class BindResponseObject extends BaseResponseObject{

    private AuthLoginResponseData data;

    public AuthLoginResponseData getData() {
        return data;
    }

    public void setData(AuthLoginResponseData data) {
        this.data = data;
    }
}
