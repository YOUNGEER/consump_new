package com.youyou.xiaofeibao.version2.response.citylist;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/23 09:23
 */

public class CityListResponseObject extends BaseResponseObject {
    private CityListResponseData data;

    public CityListResponseData getData() {
        return data;
    }

    public void setData(CityListResponseData data) {
        this.data = data;
    }
}
