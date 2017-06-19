package com.youyou.xiaofeibao.version2.request.searchshop;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2017/2/14 14:38
 */

public class SearchShopRequstObject extends BaseRequestObject {
    private SearchShopRequestParam param;

    public SearchShopRequestParam getParam() {
        return param;
    }

    public void setParam(SearchShopRequestParam param) {
        this.param = param;
    }
}
