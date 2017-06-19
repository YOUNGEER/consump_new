package com.youyou.xiaofeibao.version2.response.homehot;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/10/13 09:49
 */

public class HomeHotResponseObject extends BaseResponseObject {

    private HothomeData data;

    public HothomeData getData() {
        return data;
    }

    public void setData(HothomeData data) {
        this.data = data;
    }
}
