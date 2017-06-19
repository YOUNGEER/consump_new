package com.youyou.xiaofeibao.version2.response.detail;

/**
 * 作者：young on 2016/12/24 11:28
 */

public class DetailResponseParam {


    /**
     * bill_description : 客户在店消费：10.0元。其中优惠反币：0.1元，提现手续费：0.1元，最终收益：9.8元。
     * createdate : 01-03
     * createtime : 12:04
     * money : 9.8
     * title : 商家收入
     * type : 1
     */

    private String bill_description;
    private String createyear;
    private String createdate;
    private String createtime;
    private double money;
    private String title;
    private int type;

    public String getCreateyear() {
        return createyear;
    }

    public void setCreateyear(String createyear) {
        this.createyear = createyear;
    }

    public String getBill_description() {
        return bill_description;
    }

    public void setBill_description(String bill_description) {
        this.bill_description = bill_description;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
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
