package com.youyou.xiaofeibao.version2.response.banklist;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/27 14:53
 */

public class BanklistResponseObject extends BaseResponseObject {
    private BanklistResponseData data;

    public BanklistResponseData getData() {
        return data;
    }

    public void setData(BanklistResponseData data) {
        this.data = data;
    }
}
