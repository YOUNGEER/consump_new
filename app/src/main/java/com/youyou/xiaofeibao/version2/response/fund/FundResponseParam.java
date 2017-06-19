package com.youyou.xiaofeibao.version2.response.fund;

/**
 * 作者：young on 2017/3/10 14:59
 */

public class FundResponseParam {

    /**
     * createdate : 01-06
     * createyear : 2017
     * createtime : 9:32:
     * money : 0
     * shop_description : 在商家【便利超市】消费总金额：100.0元。其中：【消费宝支付】支付现金：0.0元，通宝币支付：100.0元；
     * title : 用户消费
     * type : 0
     */

    private String createdate;
    private String createyear;
    private String createtime;
    private double money;
    private String shop_description;
    private String title;
    private int type;

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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getShop_description() {
        return shop_description;
    }

    public void setShop_description(String shop_description) {
        this.shop_description = shop_description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
