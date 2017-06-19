package com.youyou.xiaofeibao.version2.alliance.adapter;

/**
 * 作者：young on 2016/9/7 14:08
 */
public class CustomSelectBean {

    private String name;
    private String categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomSelectBean() {
    }

    public CustomSelectBean(String memid, String name) {
        this.categoryId = memid;
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
