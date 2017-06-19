package com.youyou.xiaofeibao.version2.request.toshop;

/**
 * 作者：young on 2016/12/26 13:52
 */

public class ToShopRequestData {

    private String type;
    private ToShopRequestMember member;

    private ToShopRequestShop shop;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ToShopRequestShop getShop() {
        return shop;
    }

    public void setShop(ToShopRequestShop shop) {
        this.shop = shop;
    }

    public ToShopRequestMember getMember() {
        return member;
    }

    public void setMember(ToShopRequestMember member) {
        this.member = member;
    }
}
