package com.youyou.xiaofeibao.version2.request.bankadd;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/27 11:35
 */

public class BankaddRequestObject extends BaseRequestObject {
    private BankaddRequestParam param;

    public BankaddRequestParam getParam() {
        return param;
    }

    public void setParam(BankaddRequestParam param) {
        this.param = param;
    }
}
