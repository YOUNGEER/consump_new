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
    private int total;
    private Double turnover;
    private String memid;
    private Double history_withdrawal;
    private Double today_withdrawal;



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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
}
