package com.youyou.xiaofeibao.version2.response.fund;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2017/3/10 14:58
 */

public class FundResponseObject extends BaseResponseObject {
    private FundResponseData data;

    public FundResponseData getData() {
        return data;
    }

    public void setData(FundResponseData data) {
        this.data = data;
    }
}
