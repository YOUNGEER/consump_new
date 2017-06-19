package com.youyou.xiaofeibao.version2.response.introduceshop;

import java.util.List;

/**
 * 作者：young on 2016/12/1 09:41
 */

public class IntroduceShopResponseData {

    private String clause;
    private List<IntroduceShopResponseParam> list;

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    public List<IntroduceShopResponseParam> getList() {
        return list;
    }

    public void setList(List<IntroduceShopResponseParam> list) {
        this.list = list;
    }
}
