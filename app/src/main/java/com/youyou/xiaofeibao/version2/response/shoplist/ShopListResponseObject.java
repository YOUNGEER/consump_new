package com.youyou.xiaofeibao.version2.response.shoplist;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/3 14:26
 */

public class ShopListResponseObject extends BaseResponseObject {

    private ShopListResponseData data;

    public ShopListResponseData getData() {
        return data;
    }

    public void setData(ShopListResponseData data) {
        this.data = data;
    }
}
