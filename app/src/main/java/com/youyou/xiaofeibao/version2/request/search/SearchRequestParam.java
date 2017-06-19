package com.youyou.xiaofeibao.version2.request.search;/**
 * 作者：young on 2017/2/14 14:39
 */


public class SearchRequestParam {


    /**
     * shopname : 龙虾
     * pageNum : 1
     * pageOffset : 10
     */

    private String shopname;
    private String pageNum;
    private String pageOffset;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
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
