package com.youyou.xiaofeibao.version2.request.bind;/**
 * 作者：young on 2017/2/24 14:42
 */

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;



public class BindRequestObject extends BaseRequestObject {
    private BindRequestParam param;

    public BindRequestParam getParam() {
        return param;
    }

    public void setParam(BindRequestParam param) {
        this.param = param;
    }
}
