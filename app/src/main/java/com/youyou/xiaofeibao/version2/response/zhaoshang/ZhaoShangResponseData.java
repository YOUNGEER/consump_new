package com.youyou.xiaofeibao.version2.response.zhaoshang;

import java.util.List;

/**
 * 作者：young on 2017/5/25 14:26
 */

public class ZhaoShangResponseData {

    private String balance;

    private List<MyRecommendProxyListBean> myRecommendProxyList;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<MyRecommendProxyListBean> getMyRecommendProxyList() {
        return myRecommendProxyList;
    }

    public void setMyRecommendProxyList(List<MyRecommendProxyListBean> myRecommendProxyList) {
        this.myRecommendProxyList = myRecommendProxyList;
    }

}
