package com.youyou.xiaofeibao.version2.request.shopcomment;

/**
 * 作者：young on 2016/12/22 16:10
 */

public class ShopCommentRequestData {


    /**
     * goodsOrderId : 201611150943010416674668
     * shopId : 00471daf-9297-4f1a-87ce-6888d9fcc351
     * content : 这屎不错，剩下的谁来接
     * totalScore : 5
     */

    private String goodsOrderId;
    private String shopId;
    private String content;
    private int totalScore;

    public String getGoodsOrderId() {
        return goodsOrderId;
    }

    public void setGoodsOrderId(String goodsOrderId) {
        this.goodsOrderId = goodsOrderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
