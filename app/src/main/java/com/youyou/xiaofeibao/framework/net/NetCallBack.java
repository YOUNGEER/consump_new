package com.youyou.xiaofeibao.framework.net;

/**
 * Created by yun on 16/3/23.
 */
public interface NetCallBack<Response> {
    void onSuccess(Response reponse);

    void onNetFailure(String str);

    void onServerFailure(String str);

    void onFailure(Response str);

}
