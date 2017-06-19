package com.youyou.xiaofeibao.version2.response.proxy;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/24 11:00
 */

public class ProxyResponseObject extends BaseResponseObject {
    private ProxyResponseData data;

    public ProxyResponseData getData() {
        return data;
    }

    public void setData(ProxyResponseData data) {
        this.data = data;
    }
}
