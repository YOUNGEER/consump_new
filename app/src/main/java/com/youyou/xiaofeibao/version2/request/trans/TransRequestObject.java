package com.youyou.xiaofeibao.version2.request.trans;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/27 21:21
 */

public class TransRequestObject extends BaseRequestObject {
    private TransRequestParam param;

    public TransRequestParam getParam() {
        return param;
    }

    public void setParam(TransRequestParam param) {
        this.param = param;
    }
}
