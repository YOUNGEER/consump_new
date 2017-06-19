package com.youyou.xiaofeibao.version2.request.changepwd;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/23 12:00
 */

public class ChangePwdrequestObjecet extends BaseRequestObject {
    private ChangePwdRequestParam param;

    public ChangePwdRequestParam getParam() {
        return param;
    }

    public void setParam(ChangePwdRequestParam param) {
        this.param = param;
    }
}
