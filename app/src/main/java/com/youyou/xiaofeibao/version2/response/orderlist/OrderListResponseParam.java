package com.youyou.xiaofeibao.version2.response.orderlist;

import java.io.Serializable;

/**
 * 作者：young on 2016/12/22 14:04
 */

public class OrderListResponseParam implements Serializable {

    private String createDate;
    private String doorImg;
    private String pay_status;
    private String shopId;
    private String shopName;
    private int total_money;
    private String pay_number;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDoorImg() {
        return doorImg;
    }

    public void setDoorImg(String doorImg) {
        this.doorImg = doorImg;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getTotal_money() {
        return total_money;
    }

    public void setTotal_money(int total_money) {
        this.total_money = total_money;
    }

    public String getPay_number() {
        return pay_number;
    }

    public void setPay_number(String pay_number) {
        this.pay_number = pay_number;
    }
}
