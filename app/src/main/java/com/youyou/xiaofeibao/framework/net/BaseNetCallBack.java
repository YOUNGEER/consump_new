package com.youyou.xiaofeibao.framework.net;

import android.widget.Toast;

import com.youyou.xiaofeibao.ConsumApplication;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * @author zhaoyunhai on 16/3/28.
 */
public abstract class BaseNetCallBack<Response> implements NetCallBack<Response> {
    @Override
    abstract public void onSuccess(Response response);

    @Override
    public void onNetFailure(String str) {
        Toast.makeText(ConsumApplication.getInstance().getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServerFailure(String str) {
        Toast.makeText(ConsumApplication.getInstance().getApplicationContext(), str, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(Response responseObject) {
        if (responseObject instanceof BaseResponseObject) {
            BaseResponseObject object = (BaseResponseObject) responseObject;
            if (object != null && object.getMsg() != null && !object.getMsg().contains("请求")) {
                Toast.makeText(ConsumApplication.getInstance().getApplicationContext(), ((BaseResponseObject) responseObject).getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
