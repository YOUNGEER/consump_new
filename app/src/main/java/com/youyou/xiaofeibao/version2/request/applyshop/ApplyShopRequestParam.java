package com.youyou.xiaofeibao.version2.request.applyshop;

/**
 * 作者：young on 2016/12/13 16:40
 */

public class ApplyShopRequestParam {
    private String type;

    private ApplyShopRequsetMember member;

    private ApplyshopRequestShop shop;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ApplyShopRequsetMember getMember() {
        return member;
    }

    public void setMember(ApplyShopRequsetMember member) {
        this.member = member;
    }

    public ApplyshopRequestShop getShop() {
        return shop;
    }

    public void setShop(ApplyshopRequestShop shop) {
        this.shop = shop;
    }
}
