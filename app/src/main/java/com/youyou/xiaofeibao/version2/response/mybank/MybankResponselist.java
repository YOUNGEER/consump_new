package com.youyou.xiaofeibao.version2.response.mybank;

import java.io.Serializable;

/**
 * 作者：young on 2016/12/27 16:59
 */

public class MybankResponselist implements Serializable {


    /**
     * bankaddr : 合肥南城支行
     * bankname : 中国银行
     * bankno : 62216611500004620544
     * bankusername : 汪洋
     * id : 18
     */

    private String bankaddr;
    private String bankname;
    private String bankno;
    private String bankusername;
    private Long id;

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

    public String getBankusername() {
        return bankusername;
    }

    public void setBankusername(String bankusername) {
        this.bankusername = bankusername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
