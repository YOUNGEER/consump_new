package com.youyou.xiaofeibao.version2.request.mycount;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/6 16:37
 */

public class MycountRequestObject extends BaseRequestObject {
    private MyCountRequsetParam param;

    public MyCountRequsetParam getParam() {
        return param;
    }

    public void setParam(MyCountRequsetParam param) {
        this.param = param;
    }
}
