package com.youyou.xiaofeibao.version2.response.comment;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/2 09:31
 */

public class CommentResponseObject extends BaseResponseObject {
    private CommentResponseData data;

    public CommentResponseData getData() {
        return data;
    }

    public void setData(CommentResponseData data) {
        this.data = data;
    }
}
