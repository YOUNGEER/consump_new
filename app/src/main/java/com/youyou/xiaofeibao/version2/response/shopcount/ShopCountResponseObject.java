package com.youyou.xiaofeibao.version2.response.shopcount;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/13 14:58
 */

public class ShopCountResponseObject extends BaseResponseObject {
    private ShopCountResponseData data;

    public ShopCountResponseData getData() {
        return data;
    }

    public void setData(ShopCountResponseData data) {
        this.data = data;
    }
}
