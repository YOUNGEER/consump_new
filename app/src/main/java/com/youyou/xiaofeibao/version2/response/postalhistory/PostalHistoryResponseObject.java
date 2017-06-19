package com.youyou.xiaofeibao.version2.response.postalhistory;/**
 * 作者：young on 2017/2/15 17:59
 */

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

import java.util.List;


public class PostalHistoryResponseObject extends BaseResponseObject{
    private List<PostalHistoryResponseParam> data;

    public List<PostalHistoryResponseParam> getData() {
        return data;
    }

    public void setData(List<PostalHistoryResponseParam> data) {
        this.data = data;
    }
}
