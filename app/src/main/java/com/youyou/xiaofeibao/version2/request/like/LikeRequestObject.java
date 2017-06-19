package com.youyou.xiaofeibao.version2.request.like;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/6 15:34
 */

public class LikeRequestObject extends BaseRequestObject {
    private LikeRequestParam param;

    public LikeRequestParam getParam() {
        return param;
    }

    public void setParam(LikeRequestParam param) {
        this.param = param;
    }
}
