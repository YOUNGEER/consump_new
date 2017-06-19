package com.youyou.xiaofeibao.version2.response.shopcategory;

import com.youyou.xiaofeibao.version2.alliance.adapter.CustomSelectBean;

import java.util.List;

/**
 * 作者：young on 2016/10/18 15:42
 */

public class ShopCategoryResponseData {
    private List<CustomSelectBean> categorys;

    public List<CustomSelectBean> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<CustomSelectBean> categorys) {
        this.categorys = categorys;
    }
}
