package com.youyou.xiaofeibao.version2.request.pay;

/**
 * 作者：young on 2016/11/1 15:50
 */

public class PayRequestParam {

    private String tradetype;
    private String title;
    private String ordertype;
    private String tomemid;
    private String price;
    private String price_tbb;
    private String sign;
    private String paytype;
    private String zfpass;

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getTomemid() {
        return tomemid;
    }

    public void setTomemid(String tomemid) {
        this.tomemid = tomemid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_tbb() {
        return price_tbb;
    }

    public void setPrice_tbb(String price_tbb) {
        this.price_tbb = price_tbb;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getZfpass() {
        return zfpass;
    }

    public void setZfpass(String zfpass) {
        this.zfpass = zfpass;
    }
}
