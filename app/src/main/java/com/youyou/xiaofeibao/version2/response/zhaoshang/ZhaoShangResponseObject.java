package com.youyou.xiaofeibao.version2.response.zhaoshang;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2017/5/25 14:25
 */

public class ZhaoShangResponseObject extends BaseResponseObject {
    private ZhaoShangResponseData data;

    public ZhaoShangResponseData getData() {
        return data;
    }

    public void setData(ZhaoShangResponseData data) {
        this.data = data;
    }
}
