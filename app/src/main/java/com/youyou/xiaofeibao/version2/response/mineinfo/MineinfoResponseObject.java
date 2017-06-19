package com.youyou.xiaofeibao.version2.response.mineinfo;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/17 09:37
 */

public class MineinfoResponseObject extends BaseResponseObject {
    private MineinfoResponseData data;

    public MineinfoResponseData getData() {
        return data;
    }

    public void setData(MineinfoResponseData data) {
        this.data = data;
    }
}
