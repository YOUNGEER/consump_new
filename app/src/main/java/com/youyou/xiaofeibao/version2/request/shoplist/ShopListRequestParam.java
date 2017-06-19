package com.youyou.xiaofeibao.version2.request.shoplist;

/**
 * 作者：young on 2016/12/3 15:29
 */

public class ShopListRequestParam {

    private String shopDistrictId;
    private String categoryId;
    private String sorting;
    private String latitude;
    private String longitude;
    private String pageNum;
    private String pageOffest;

    public String getShopDistrictId() {
        return shopDistrictId;
    }

    public void setShopDistrictId(String shopDistrictId) {
        this.shopDistrictId = shopDistrictId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageOffest() {
        return pageOffest;
    }

    public void setPageOffest(String pageOffest) {
        this.pageOffest = pageOffest;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }
}
