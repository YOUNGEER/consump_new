package com.youyou.xiaofeibao.version2.request.shopcomment;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/22 16:09
 */

public class ShopCommentRequestObject extends BaseRequestObject {
    private ShopCommentRequestParam param;

    public ShopCommentRequestParam getParam() {
        return param;
    }

    public void setParam(ShopCommentRequestParam param) {
        this.param = param;
    }
}
