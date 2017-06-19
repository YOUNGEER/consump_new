package com.youyou.xiaofeibao.version2.request.page;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/22 13:57
 */

public class PageRequestObject extends BaseRequestObject {
    private PageRequestParam param;

    public PageRequestParam getParam() {
        return param;
    }

    public void setParam(PageRequestParam param) {
        this.param = param;
    }
}
