package com.youyou.xiaofeibao.version2.response.search;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.shoplist.ShopListResponseParam;

import java.util.List;

/**
 * 作者：young on 2017/2/14 15:27
 */

public class SearchResponseObject extends BaseResponseObject {

    private List<ShopListResponseParam> data;

    public List<ShopListResponseParam> getData() {
        return data;
    }

    public void setData(List<ShopListResponseParam> data) {
        this.data = data;
    }
}
