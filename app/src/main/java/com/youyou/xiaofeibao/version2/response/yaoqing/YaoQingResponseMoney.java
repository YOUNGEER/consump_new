package com.youyou.xiaofeibao.version2.response.yaoqing;

import java.util.List;

/**
 * 作者：young on 2017/4/12 17:56
 */

public class YaoQingResponseMoney {
    private List<YQResponseParam> moneyList;
    private String num;
    private String total;
    private String adimg;
    private String url;

    public String getAdimg() {
        return adimg;
    }

    public void setAdimg(String adimg) {
        this.adimg = adimg;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<YQResponseParam> getMoneyList() {
        return moneyList;
    }

    public void setMoneyList(List<YQResponseParam> moneyList) {
        this.moneyList = moneyList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
