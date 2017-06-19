package com.youyou.xiaofeibao.version2.response.special;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/1 16:49
 */

public class SpecialResponseObject extends BaseResponseObject {

    private SpecialResponseData data;

    public SpecialResponseData getData() {
        return data;
    }

    public void setData(SpecialResponseData data) {
        this.data = data;
    }
}
