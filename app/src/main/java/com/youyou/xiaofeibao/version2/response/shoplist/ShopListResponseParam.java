package com.youyou.xiaofeibao.version2.response.shoplist;

/**
 * 作者：young on 2016/12/3 14:27
 */

public class ShopListResponseParam {


    /**
     * addr : 望江路与石台路交口向北5米
     * avgScore : 5.0
     * doorImg : http://192.168.1.12:8080/xfb/img/20160727/d7733602-ecb5-4752-92a0-c6c8cbb2e102.jpg
     * memid : 0041a20b-4dfc-4f69-b843-221237dabf87
     * shopName : 心缘出租公寓
     */

    private String addr;
    private String avgScore;
    private String doorImg;
    private String memid;
    private String shopName;
    private Double distance;
    private String discount;


    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getDoorImg() {
        return doorImg;
    }

    public void setDoorImg(String doorImg) {
        this.doorImg = doorImg;
    }

    public String getMemid() {
        return memid;
    }

    public void setMemid(String memid) {
        this.memid = memid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
