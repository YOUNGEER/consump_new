package com.youyou.xiaofeibao.version2.response.proxyhistory;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2017/5/26 16:56
 */

public class ProxyHistoryResponseObject extends BaseResponseObject {
    private ProxyHistoryResponseData data;

    public ProxyHistoryResponseData getData() {
        return data;
    }

    public void setData(ProxyHistoryResponseData data) {
        this.data = data;
    }
}
