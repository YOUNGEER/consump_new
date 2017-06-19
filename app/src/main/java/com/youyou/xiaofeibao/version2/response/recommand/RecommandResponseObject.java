package com.youyou.xiaofeibao.version2.response.recommand;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/8 09:35
 */

public class RecommandResponseObject extends BaseResponseObject {

    private RecommandResponseParam data;

    public RecommandResponseParam getData() {
        return data;
    }

    public void setData(RecommandResponseParam data) {
        this.data = data;
    }
}
