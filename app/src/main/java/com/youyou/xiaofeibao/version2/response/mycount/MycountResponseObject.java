package com.youyou.xiaofeibao.version2.response.mycount;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/6 16:57
 */

public class MycountResponseObject extends BaseResponseObject {
    private MycountResponseData data;

    public MycountResponseData getData() {
        return data;
    }

    public void setData(MycountResponseData data) {
        this.data = data;
    }
}
