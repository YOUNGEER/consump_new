package com.youyou.xiaofeibao.version2.request;

/**
 * 作者：young on 2016/12/13 14:55
 */

public class EmptyRequestObject extends BaseRequestObject {
    private EmptyRequestParam param = new EmptyRequestParam();

    public EmptyRequestParam getParam() {
        return param;
    }

    public void setParam(EmptyRequestParam param) {
        this.param = param;
    }

    class EmptyRequestParam {

    }
}
