package com.youyou.xiaofeibao.version2.response.shoporders;

import java.util.List;

/**
 * 作者：young on 2016/12/22 20:04
 */

public class ShopOrderResponseData {
    private List<ShopOrderResponseParam> payorderList;

    public List<ShopOrderResponseParam> getPayorderList() {
        return payorderList;
    }

    public void setPayorderList(List<ShopOrderResponseParam> payorderList) {
        this.payorderList = payorderList;
    }
}
