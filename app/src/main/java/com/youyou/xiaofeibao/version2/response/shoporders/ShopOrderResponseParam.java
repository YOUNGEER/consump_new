package com.youyou.xiaofeibao.version2.response.shoporders;

/**
 * 作者：young on 2016/12/22 20:04
 */

public class ShopOrderResponseParam {


    /**
     * createDate : 2016-11-15
     * loginName : 黄晓明
     * order_description : 支付订单
     * pay_number : 201611150943010416674668
     * pay_status : 1
     * total_money : 30
     */

    private String createDate;
    private String loginName;
    private String order_description;
    private String pay_number;
    private int pay_status;
    private double total_money;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOrder_description() {
        return order_description;
    }

    public void setOrder_description(String order_description) {
        this.order_description = order_description;
    }

    public String getPay_number() {
        return pay_number;
    }

    public void setPay_number(String pay_number) {
        this.pay_number = pay_number;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(double total_money) {
        this.total_money = total_money;
    }
}
