package com.youyou.xiaofeibao.version2.request.comment;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/2 09:30
 */

public class CommentRequestObject extends BaseRequestObject {
    private CommentRequestParam param;

    public CommentRequestParam getParam() {
        return param;
    }

    public void setParam(CommentRequestParam param) {
        this.param = param;
    }
}
