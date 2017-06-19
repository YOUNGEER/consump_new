package com.youyou.xiaofeibao.wxapi;

public class WechatPayEntity {

    private String appid;
    private String appSign;

    private String status;
    private String message;
    private String prepayid;
    private String nonceStr;
    private String timeStamp;
    private String packageSign;


    private String partnerid;
    private String openId;


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public String getPrepayid() {
        return prepayid;
    }


    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }


    public String getNonceStr() {
        return nonceStr;
    }


    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }


    public String getTimeStamp() {
        return timeStamp;
    }


    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


    public String getPackageSign() {
        return packageSign;
    }


    public void setPackageSign(String packageSign) {
        this.packageSign = packageSign;
    }


    public String getAppSign() {
        return appSign;
    }


    public void setAppSign(String appSign) {
        this.appSign = appSign;
    }


    public String getAppid() {
        return appid;
    }


    public void setAppid(String appid) {
        this.appid = appid;
    }


    public String getPartnerid() {
        return partnerid;
    }


    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }


    @Override
    public String toString() {
        return "WechatPayEntity [status=" + status + ", message=" + message
                + ", prepayid=" + prepayid + ", nonceStr=" + nonceStr
                + ", timeStamp=" + timeStamp + ", packageSign=" + packageSign
                + ", appSign=" + appSign + ", appid=" + appid + ", partnerid="
                + partnerid + "]";
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
