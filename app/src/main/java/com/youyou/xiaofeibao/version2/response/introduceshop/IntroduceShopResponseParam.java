package com.youyou.xiaofeibao.version2.response.introduceshop;

import java.io.Serializable;

/**
 * 作者：young on 2016/12/1 09:42
 */

public class IntroduceShopResponseParam implements Serializable {

    private String iconurl;
    private String categoryId;
    private String name;

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
