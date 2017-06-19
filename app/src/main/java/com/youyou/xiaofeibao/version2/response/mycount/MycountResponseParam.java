package com.youyou.xiaofeibao.version2.response.mycount;

import java.io.Serializable;

/**
 * 作者：young on 2016/12/6 16:58
 */

public class MycountResponseParam implements Serializable {


    /**
     * account_description : 在商家【Hey juice 茶桔便】消费总金额：30.0元。其中：【微信支付】支付现金：10.0元，智惠币支付：20.0元；此次消费获得智惠币：1.0元。
     * createdate : 12-21
     * createtime : 9:30
     * goldnum : 19
     * title : 消费宝单次支出-转账
     * shopName : Hey juice 茶桔便
     * type : 0
     */

    private String account_description;
    private String createdate;
    private String createyear;
    private String createtime;
    private Double goldnum;
    private String title;
    private String shopName;
    private int type;

    public String getAccount_description() {
        return account_description;
    }

    public void setAccount_description(String account_description) {
        this.account_description = account_description;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCreateyear() {
        return createyear;
    }

    public void setCreateyear(String createyear) {
        this.createyear = createyear;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Double getGoldnum() {
        return goldnum;
    }

    public void setGoldnum(Double goldnum) {
        this.goldnum = goldnum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
