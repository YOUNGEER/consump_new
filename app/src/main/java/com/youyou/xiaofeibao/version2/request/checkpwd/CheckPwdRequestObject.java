package com.youyou.xiaofeibao.version2.request.checkpwd;/**
 * 作者：young on 2017/2/28 14:16
 */

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;


public class CheckPwdRequestObject extends BaseRequestObject {
    private CheckPwdRequestParam param;

    public CheckPwdRequestParam getParam() {
        return param;
    }

    public void setParam(CheckPwdRequestParam param) {
        this.param = param;
    }
}
