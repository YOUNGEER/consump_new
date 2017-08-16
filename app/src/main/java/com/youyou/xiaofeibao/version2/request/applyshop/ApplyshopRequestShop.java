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
    private String shopRefreePhone;//选填
    private String doorimg;
    private String businessimg;
    private String licenseimg;
    private String idcardnofrontimg;
    private String idcardnobackimg;
    /**
     * aliasName : 商户简称
     * addressType : 地址类型
     * shopphone : 联系人电话
     * contactType : 联系人类型
     * servicePhone : 客服电话
     * shopreturnrate : 消费返币比例（选填）
     * posrate : POS费率(选填)
     * businessLicense : 营业执照编号(选填)
     * businessLicenseType : 营业执照类型(选填)
     * cardNo : 银行卡号(选填)
     * cardName : 银行卡持卡人姓名(选填)
     */

    private String aliasName;
    private String addressType;
    private String shopphone;
    private String contactType;
    private String servicePhone;
    private String shopreturnrate;

    private String posrate;
    private String businessLicense;
    private String businessLicenseType;
    private String cardNo;
    private String cardName;
    /**
     * endbusinesstime : 结束时间
     * startbusinesstime : 开始时间
     * introduction : 商家介绍
     */

    private String endbusinesstime;
    private String startbusinesstime;
    private String introduction;

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

    public String getShopRefreePhone() {
        return shopRefreePhone;
    }

    public void setShopRefreePhone(String shopRefreePhone) {
        this.shopRefreePhone = shopRefreePhone;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getShopphone() {
        return shopphone;
    }

    public void setShopphone(String shopphone) {
        this.shopphone = shopphone;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getShopreturnrate() {
        return shopreturnrate;
    }

    public void setShopreturnrate(String shopreturnrate) {
        this.shopreturnrate = shopreturnrate;
    }

    public String getPosrate() {
        return posrate;
    }

    public void setPosrate(String posrate) {
        this.posrate = posrate;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicenseType() {
        return businessLicenseType;
    }

    public void setBusinessLicenseType(String businessLicenseType) {
        this.businessLicenseType = businessLicenseType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }


    public String getEndbusinesstime() {
        return endbusinesstime;
    }

    public void setEndbusinesstime(String endbusinesstime) {
        this.endbusinesstime = endbusinesstime;
    }

    public String getStartbusinesstime() {
        return startbusinesstime;
    }

    public void setStartbusinesstime(String startbusinesstime) {
        this.startbusinesstime = startbusinesstime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
