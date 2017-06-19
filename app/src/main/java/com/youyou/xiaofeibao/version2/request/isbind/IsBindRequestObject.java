package com.youyou.xiaofeibao.version2.request.isbind;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2017/3/21 17:01
 */

public class IsBindRequestObject extends BaseRequestObject {
    private IsBindRequestParam param;

    public IsBindRequestParam getParam() {
        return param;
    }

    public void setParam(IsBindRequestParam param) {
        this.param = param;
    }
}
