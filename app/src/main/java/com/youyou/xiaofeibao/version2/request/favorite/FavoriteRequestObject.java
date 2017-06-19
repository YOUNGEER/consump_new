package com.youyou.xiaofeibao.version2.request.favorite;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/5 16:25
 */

public class FavoriteRequestObject extends BaseRequestObject {

    private FavoriteRequestParam param;

    public FavoriteRequestParam getParam() {
        return param;
    }

    public void setParam(FavoriteRequestParam param) {
        this.param = param;
    }
}
