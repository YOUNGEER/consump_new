package com.youyou.xiaofeibao.version2.request.bankadd;

/**
 * 作者：young on 2016/12/27 11:36
 */

public class BankaddRequestParam {

    private String payPwd;
    private String bankaddr;
    private String bankname;
    private String bankno;

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getBankaddr() {
        return bankaddr;
    }

    public void setBankaddr(String bankaddr) {
        this.bankaddr = bankaddr;
    }

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
}
