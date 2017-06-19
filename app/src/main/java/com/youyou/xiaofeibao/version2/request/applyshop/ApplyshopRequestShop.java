package com.youyou.xiaofeibao.version2.request.applyshop;

/**
 * 作者：young on 2016/12/13 16:41
 */

public class ApplyshopRequestShop {


    private String shopname;//必填
    private String categoryid;//必填
    private String addr;//必填
    private String latitude;//必填
    private String longitude;//必填
    private String doorimg;
    private String businessimg;
    private String licenseimg;
    private String idcardnofrontimg;
    private String idcardnobackimg;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getBusinessimg() {
        return businessimg;
    }

    public void setBusinessimg(String businessimg) {
        this.businessimg = businessimg;
    }

    public String getLicenseimg() {
        return licenseimg;
    }

    public void setLicenseimg(String licenseimg) {
        this.licenseimg = licenseimg;
    }

    public String getIdcardnofrontimg() {
        return idcardnofrontimg;
    }

    public void setIdcardnofrontimg(String idcardnofrontimg) {
        this.idcardnofrontimg = idcardnofrontimg;
    }

    public String getIdcardnobackimg() {
        return idcardnobackimg;
    }

    public void setIdcardnobackimg(String idcardnobackimg) {
        this.idcardnobackimg = idcardnobackimg;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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

    public String getDoorimg() {
        return doorimg;
    }

    public void setDoorimg(String doorimg) {
        this.doorimg = doorimg;
    }
}
