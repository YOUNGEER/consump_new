package com.youyou.xiaofeibao.version2.response.appversion;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/23 14:58
 */

public class AppResponseObject extends BaseResponseObject {
    private AppResponseData data;

    public AppResponseData getData() {
        return data;
    }

    public void setData(AppResponseData data) {
        this.data = data;
    }
}
