package com.youyou.xiaofeibao.framework.net;

/**
 * @author zhaoyunhai on 16/4/1.
 */
abstract public class SimpleNetCallBack<Response> implements NetCallBack<Response> {


    @Override
    public void onNetFailure(String str) {
        onNetFail();
    }

    @Override
    public void onServerFailure(String str) {
        onNetFail();
    }

    @Override
    public void onFailure(Response response) {
        onNetFail();
    }

    abstract public void onNetFail();
}
