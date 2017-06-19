package com.youyou.xiaofeibao.version2.response.alliancemarket;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/10/21 15:30
 */

public class AllianceMarketResponseObject extends BaseResponseObject {
    private AllianceMarketResponseData data;

    public AllianceMarketResponseData getData() {
        return data;
    }

    public void setData(AllianceMarketResponseData data) {
        this.data = data;
    }
}
