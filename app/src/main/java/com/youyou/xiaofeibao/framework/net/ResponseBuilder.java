package com.youyou.xiaofeibao.framework.net;

import com.alibaba.fastjson.JSON;
import com.youyou.xiaofeibao.common.MainThreadPostUtils;
import com.youyou.xiaofeibao.framework.Log.LogDoumee;
import com.youyou.xiaofeibao.framework.Log.LogTag;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.net.CommonOkHttpClient;
import com.youyou.xiaofeibao.net.CommonRequest;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;


/**
 * Created by yun on 16/3/22.
 */
public class ResponseBuilder<Request, Response> {

    private NetCallBack<Response> mCallBack;
    private String url;
    public Class<?> mClass = null;
    private Request mRequest;

    public ResponseBuilder(Request request, String url, Class<Response> aClass) {
        this.url = url;
        this.mClass = aClass;
        mRequest = request;
    }

    public ResponseBuilder setCallBack(NetCallBack<Response> callBack) {
        this.mCallBack = callBack;
        return this;

    }

    //json参数提交
    public void send() {

        if (LogDoumee.isLoggable(LogTag.HTTP_NET, LogDoumee.INFO)) {
            LogDoumee.i(LogTag.HTTP_NET, "请求参数" + JSON.toJSONString(mRequest));
        }

        CommonOkHttpClient.post(CommonRequest.createPostJsonRequest(url, JSON.toJSONString(mRequest)), new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                MainThreadPostUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        if (LogDoumee.isLoggable(LogTag.HTTP_NET, LogDoumee.INFO)) {
                            LogDoumee.i(LogTag.HTTP_NET, "onFailure" + e.toString());
                        }
                        mCallBack.onNetFailure("");
//                        Log.i("wy",e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final okhttp3.Response mResponse) throws IOException {

                final String res = mResponse.body().string();
                if (LogDoumee.isLoggable(LogTag.HTTP_NET, LogDoumee.INFO)) {
                    LogDoumee.i(LogTag.HTTP_NET, "onResponseSuccess" + url + res);
                }
                MainThreadPostUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        handleResponse(res);
                    }
                });
            }
        });
    }

    private void handleResponse(String responseObj) {
        try {

            Response obj = (Response) JSON.parseObject(responseObj.toString().trim(), mClass);

            if (LogDoumee.isLoggable(LogTag.HTTP_NET, LogDoumee.INFO)) {
                LogDoumee.i(LogTag.HTTP_NET, "onResponseSuccess" + ((BaseResponseObject)obj).getCode());
            }
            if (mCallBack != null&&obj != null) {
                if ("1".equals(((BaseResponseObject) obj).getCode())) {
                    mCallBack.onSuccess(obj);
                }else if ("-1".equals(((BaseResponseObject) obj).getCode())) {
                    PreferencesConfig.v2_token.set("");
                    PreferencesConfig.v2_loginName.set("");
                    PreferencesConfig.v2_url.set("");
                    PreferencesConfig.v2_phone.set("");
                    mCallBack.onServerFailure("登录过期，请重新登录");

                }else {
                    mCallBack.onFailure(obj);
                }
            } else {
                mCallBack.onFailure(obj);
            }

        } catch (Exception e) {
         mCallBack.onNetFailure(e.toString());
        }
    }

}
