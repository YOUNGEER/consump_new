package com.youyou.xiaofeibao.version2.request.recommand;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/9 10:50
 */

public class RecommandRequestObject extends BaseRequestObject {
    private RecommandRequstParam param;

    public RecommandRequstParam getParam() {
        return param;
    }

    public void setParam(RecommandRequstParam param) {
        this.param = param;
    }
}
