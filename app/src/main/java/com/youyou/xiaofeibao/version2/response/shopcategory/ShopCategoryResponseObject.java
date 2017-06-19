package com.youyou.xiaofeibao.version2.response.shopcategory;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/10/18 15:41
 */

public class ShopCategoryResponseObject extends BaseResponseObject {

    private ShopCategoryResponseData data;

    public ShopCategoryResponseData getData() {
        return data;
    }

    public void setData(ShopCategoryResponseData data) {
        this.data = data;
    }
}
