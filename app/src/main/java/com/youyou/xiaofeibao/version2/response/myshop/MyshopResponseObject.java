package com.youyou.xiaofeibao.version2.response.myshop;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/24 10:33
 */

public class MyshopResponseObject extends BaseResponseObject {
    private MyshopResponseData data;

    public MyshopResponseData getData() {
        return data;
    }

    public void setData(MyshopResponseData data) {
        this.data = data;
    }
}
