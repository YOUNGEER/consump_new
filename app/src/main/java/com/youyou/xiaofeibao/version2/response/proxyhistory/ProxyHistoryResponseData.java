package com.youyou.xiaofeibao.version2.response.proxyhistory;

import java.util.List;

/**
 * 作者：young on 2017/5/26 16:57
 */

public class ProxyHistoryResponseData {
    private List<ProxyHistoryResponseParam> appliWithdrawallist;

    public List<ProxyHistoryResponseParam> getAppliWithdrawallist() {
        return appliWithdrawallist;
    }

    public void setAppliWithdrawallist(List<ProxyHistoryResponseParam> appliWithdrawallist) {
        this.appliWithdrawallist = appliWithdrawallist;
    }
}
