package com.youyou.xiaofeibao.version2.request.pay;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/11/1 15:logo_pic
 */

public class PayRequestObject extends BaseRequestObject {


    private PayRequestParam param;


    public PayRequestParam getParam() {
        return param;
    }

    public void setParam(PayRequestParam param) {
        this.param = param;
    }
}
