package com.youyou.xiaofeibao.version2.response.pay;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/11/1 15:52
 */

public class PayResponseObject extends BaseResponseObject {

    private String error_code;
    private String msg;
    private PayResponseData data;


    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }


    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PayResponseData getData() {
        return data;
    }

    public void setData(PayResponseData data) {
        this.data = data;
    }
}
