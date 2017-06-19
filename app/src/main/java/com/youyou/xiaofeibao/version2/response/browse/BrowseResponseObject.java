package com.youyou.xiaofeibao.version2.response.browse;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/11/12 11:27
 */

public class BrowseResponseObject extends BaseResponseObject {

    private BrowseResponseDate1 data;

    public BrowseResponseDate1 getData() {
        return data;
    }

    public void setData(BrowseResponseDate1 data) {
        this.data = data;
    }
}
