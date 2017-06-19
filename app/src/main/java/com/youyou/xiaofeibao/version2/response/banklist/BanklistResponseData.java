package com.youyou.xiaofeibao.version2.response.banklist;

import java.util.List;

/**
 * 作者：young on 2016/12/27 14:55
 */

public class BanklistResponseData {
    private List<BanklistResponseList> bankList;

    public List<BanklistResponseList> getBankList() {
        return bankList;
    }

    public void setBankList(List<BanklistResponseList> bankList) {
        this.bankList = bankList;
    }
}
