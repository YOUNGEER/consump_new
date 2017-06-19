package com.youyou.xiaofeibao.version2.response.benefit;


import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2017/1/4 16:45
 */

public class BenefitResponseObject extends BaseResponseObject {
    private BenefitResponseData data;

    public BenefitResponseData getData() {
        return data;
    }

    public void setData(BenefitResponseData data) {
        this.data = data;
    }
}
