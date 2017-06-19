package com.youyou.xiaofeibao.version2.response.tong;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/21 10:38
 */

public class TongResponseObject extends BaseResponseObject {
    private GoldResponseData data;

    public GoldResponseData getData() {
        return data;
    }

    public void setData(GoldResponseData data) {
        this.data = data;
    }
}
