package com.youyou.xiaofeibao.version2.request.msg_register;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/10/20 10:01
 */

public class MsgRegRequestObject extends BaseRequestObject {

    private MsgRegRequestParam param;

    public MsgRegRequestParam getParam() {
        return param;
    }

    public void setParam(MsgRegRequestParam param) {
        this.param = param;
    }
}
