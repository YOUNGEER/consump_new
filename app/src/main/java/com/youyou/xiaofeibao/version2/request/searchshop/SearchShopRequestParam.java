package com.youyou.xiaofeibao.version2.request.searchshop;

/**
 * 作者：young on 2017/2/14 14:39
 */


public class SearchShopRequestParam {


    /**
     * shopname : 龙虾
     * pageNum : 1
     * pageOffset : 10
     */

    private String shopName;
    private String pageNum;
    private String pageOffset;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(String pageOffset) {
        this.pageOffset = pageOffset;
    }
}
