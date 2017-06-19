package com.youyou.xiaofeibao.version2.request.deletebank;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/27 20:16
 */

public class DeleteBankRequestObject extends BaseRequestObject {
    private DeleteBankRequestParam param;

    public DeleteBankRequestParam getParam() {
        return param;
    }

    public void setParam(DeleteBankRequestParam param) {
        this.param = param;
    }
}
