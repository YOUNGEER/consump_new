package com.youyou.xiaofeibao.version2.response.proxy;

/**
 * 作者：young on 2016/12/24 11:04
 */

public class ProxyResponseParam {

    /**
     * type :
     * balance : 8576
     * month_money : 0
     * total_money : 0
     */

    private String type;
    private String proxyname;
    private Double balance;
    private Double month_money;
    private Double total_money;
    private Double day_money;
    private Double history_withdrawal;
    private Double today_withdrawal;
    /**
     * balance : 0
     * settlementing_money : 0
     * shop_money : 0
     * day_money : 0
     * history_withdrawal : 0
     * month_money : 0
     * today_withdrawal : 0
     * total_money : 0
     */

    private Double settlementing_money;
    private Double shop_money;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDay_money() {
        return day_money;
    }

    public void setDay_money(Double day_money) {
        this.day_money = day_money;
    }

    public Double getMonth_money() {
        return month_money;
    }

    public void setMonth_money(Double month_money) {
        this.month_money = month_money;
    }

    public Double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Double total_money) {
        this.total_money = total_money;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getProxyname() {
        return proxyname;
    }

    public void setProxyname(String proxyname) {
        this.proxyname = proxyname;
    }

    public Double getHistory_withdrawal() {
        return history_withdrawal;
    }

    public void setHistory_withdrawal(Double history_withdrawal) {
        this.history_withdrawal = history_withdrawal;
    }

    public Double getToday_withdrawal() {
        return today_withdrawal;
    }

    public void setToday_withdrawal(Double today_withdrawal) {
        this.today_withdrawal = today_withdrawal;
    }

    public Double getSettlementing_money() {
        return settlementing_money;
    }

    public void setSettlementing_money(Double settlementing_money) {
        this.settlementing_money = settlementing_money;
    }

    public Double getShop_money() {
        return shop_money;
    }

    public void setShop_money(Double shop_money) {
        this.shop_money = shop_money;
    }
}
