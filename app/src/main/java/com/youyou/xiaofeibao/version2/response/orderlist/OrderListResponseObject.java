package com.youyou.xiaofeibao.version2.response.orderlist;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/22 14:03
 */

public class OrderListResponseObject extends BaseResponseObject {
    private OrderListResponseData data;

    public OrderListResponseData getData() {
        return data;
    }

    public void setData(OrderListResponseData data) {
        this.data = data;
    }
}
