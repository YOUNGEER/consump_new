package com.youyou.xiaofeibao.version2.response.shopinfo;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/1 14:04
 */

public class ShopInfoResponseObject extends BaseResponseObject {
    private ShopinfoResponseData data;

    public ShopinfoResponseData getData() {
        return data;
    }

    public void setData(ShopinfoResponseData data) {
        this.data = data;
    }
}
