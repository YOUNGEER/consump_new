package com.youyou.xiaofeibao.version2.response.shopcount;

/**
 * 作者：young on 2016/12/13 15:00
 */

public class ShopCountResponseShop {


    /**
     * day_turnover : 0
     * money : 0
     * shopName :
     * total : 0
     * turnover : 0
     */

    private Double day_turnover;
    private Double money;
    private String shopName;
    private Double total;
    private Double turnover;
    private String memid;
    private Double history_withdrawal;
    private Double today_withdrawal;
    /**
     * day_turnover : 0
     * history_withdrawal : 0
     * money : 0
     * shop_money : 0
     * total_money : 0
     * settlementing_money : 0
     * withdrawal_money : 0
     * today_withdrawal : 0
     * turnover : 0
     */

    private Double shop_money;
    private Double total_money;
    private Double settlementing_money;
    private Double withdrawal_money;


    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getDay_turnover() {
        return day_turnover;
    }

    public void setDay_turnover(Double day_turnover) {
        this.day_turnover = day_turnover;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }



    public String getMemid() {
        return memid;
    }

    public void setMemid(String memid) {
        this.memid = memid;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getShop_money() {
        return shop_money;
    }

    public void setShop_money(Double shop_money) {
        this.shop_money = shop_money;
    }

    public Double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Double total_money) {
        this.total_money = total_money;
    }

    public Double getSettlementing_money() {
        return settlementing_money;
    }

    public void setSettlementing_money(Double settlementing_money) {
        this.settlementing_money = settlementing_money;
    }

    public Double getWithdrawal_money() {
        return withdrawal_money;
    }

    public void setWithdrawal_money(Double withdrawal_money) {
        this.withdrawal_money = withdrawal_money;
    }
}
