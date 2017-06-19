package com.youyou.xiaofeibao.version2.response.shoporders;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/22 20:03
 */

public class ShopOrdersResponseObject extends BaseResponseObject {
    private ShopOrderResponseData data;

    public ShopOrderResponseData getData() {
        return data;
    }

    public void setData(ShopOrderResponseData data) {
        this.data = data;
    }
}
