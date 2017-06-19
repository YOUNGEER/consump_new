package com.youyou.xiaofeibao.version2.response.proxydetail;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2017/5/25 16:21
 */

public class ProxyDetailResponseObject extends BaseResponseObject{
    private ProxyDetailResponseData data;

    public ProxyDetailResponseData getData() {
        return data;
    }

    public void setData(ProxyDetailResponseData data) {
        this.data = data;
    }
}
