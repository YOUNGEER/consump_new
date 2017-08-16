package com.youyou.xiaofeibao.version2.response.mycount;

import java.util.List;

/**
 * 作者：young on 2016/12/6 16:57
 */

public class MycountResponseData {

    private String balance;
    private Double settlementing_money;
    private String isBindWx;
    private String isBindZfb;
    private List<MycountResponseParam> list;

    public String getBalance() {
        return balance;
    }


    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIsBindWx() {
        return isBindWx;
    }

    public void setIsBindWx(String isBindWx) {
        this.isBindWx = isBindWx;
    }

    public String getIsBindZfb() {
        return isBindZfb;
    }

    public void setIsBindZfb(String isBindZfb) {
        this.isBindZfb = isBindZfb;
    }

    public List<MycountResponseParam> getList() {
        return list;
    }

    public void setList(List<MycountResponseParam> list) {
        this.list = list;
    }

    public Double getSettlementing_money() {
        return settlementing_money;
    }

    public void setSettlementing_money(Double settlementing_money) {
        this.settlementing_money = settlementing_money;
    }
}
