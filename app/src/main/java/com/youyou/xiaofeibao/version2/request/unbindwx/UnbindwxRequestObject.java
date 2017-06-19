package com.youyou.xiaofeibao.version2.request.unbindwx;/**
 * 作者：young on 2017/2/27 16:05
 */

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;


public class UnbindwxRequestObject extends BaseRequestObject {
    private UnbindwxRequestParam param;

    public UnbindwxRequestParam getParam() {
        return param;
    }

    public void setParam(UnbindwxRequestParam param) {
        this.param = param;
    }
}
