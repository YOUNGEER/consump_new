package com.youyou.xiaofeibao.version2.request.trans;

/**
 * 作者：young on 2016/12/27 21:22
 */

public class TransRequestParam {
    private String total_fee;
    private String trade_type;
    private String bankid;
    private String zfpass;


    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getZfpass() {
        return zfpass;
    }

    public void setZfpass(String zfpass) {
        this.zfpass = zfpass;
    }
}
