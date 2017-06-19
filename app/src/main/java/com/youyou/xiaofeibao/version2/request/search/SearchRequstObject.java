package com.youyou.xiaofeibao.version2.request.search;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2017/2/14 14:38
 */

public class SearchRequstObject extends BaseRequestObject {
    private SearchRequestParam param;


    public SearchRequestParam getParam() {
        return param;
    }

    public void setParam(SearchRequestParam param) {
        this.param = param;
    }
}
