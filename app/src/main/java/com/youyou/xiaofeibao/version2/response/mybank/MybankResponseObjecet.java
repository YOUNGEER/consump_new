package com.youyou.xiaofeibao.version2.response.mybank;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/27 16:57
 */

public class MybankResponseObjecet extends BaseResponseObject {
    private MybankResponseParam data;

    public MybankResponseParam getData() {
        return data;
    }

    public void setData(MybankResponseParam data) {
        this.data = data;
    }
}
