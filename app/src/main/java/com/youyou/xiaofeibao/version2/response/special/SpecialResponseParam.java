package com.youyou.xiaofeibao.version2.response.special;

/**
 * 作者：young on 2016/12/1 16:50
 */

public class SpecialResponseParam {


    /**
     * imgurl : http://192.168.1.3:8080/xfb/img/20160518/8e42fd72-3cdf-4ab6-be68-8dc14396ea7c.png
     * name : 柴火大院 五常长粒香米5kg
     * price : 55
     */

    private String imgurl;
    private String name;
    private int price;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
