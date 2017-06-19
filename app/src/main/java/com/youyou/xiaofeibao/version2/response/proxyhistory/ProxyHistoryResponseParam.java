package com.youyou.xiaofeibao.version2.response.proxyhistory;

/**
 * 作者：young on 2017/5/26 16:59
 */

public class ProxyHistoryResponseParam {


    /**
     * bankname : 中国农业银行
     * bankno : 6228480669252118973
     * bankusername : 宋自钢
     * createtime : 2017-05-25 12:02:57
     * money : 100.000
     * ratemoney :
     * realPaymoney :
     * status : 0
     */

    private String bankname;
    private String bankno;
    private String bankusername;
    private String createtime;
    private String money;
    private String ratemoney;
    private String realPaymoney;
    private String status;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public String getBankusername() {
        return bankusername;
    }

    public void setBankusername(String bankusername) {
        this.bankusername = bankusername;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRatemoney() {
        return ratemoney;
    }

    public void setRatemoney(String ratemoney) {
        this.ratemoney = ratemoney;
    }

    public String getRealPaymoney() {
        return realPaymoney;
    }

    public void setRealPaymoney(String realPaymoney) {
        this.realPaymoney = realPaymoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
