package com.youyou.xiaofeibao.version2.response.mymember;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/22 20:32
 */

public class MyMemberResponseObjecet extends BaseResponseObject {
    private MyMemberResponseData data;

    public MyMemberResponseData getData() {
        return data;
    }

    public void setData(MyMemberResponseData data) {
        this.data = data;
    }
}
