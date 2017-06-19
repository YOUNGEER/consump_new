package com.youyou.xiaofeibao.version2.zfb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.youyou.xiaofeibao.MainActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.BindActivity;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.pay.PayResult;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.authlogin.AuthLoginRequestObject;
import com.youyou.xiaofeibao.version2.request.authlogin.AuthLoginRequestParam;
import com.youyou.xiaofeibao.version2.request.isbind.IsBindRequestObject;
import com.youyou.xiaofeibao.version2.request.isbind.IsBindRequestParam;
import com.youyou.xiaofeibao.version2.response.ZfbResonseObject;
import com.youyou.xiaofeibao.version2.response.authlogin.AuthLoginResponseData;
import com.youyou.xiaofeibao.version2.response.authlogin.AuthLoginResponseObject;
import com.youyou.xiaofeibao.version2.response.isbind.IsBindResponseObject;

import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 作者：young on 2017/2/28 17:17
 */


public class ZfbLogin {

    private static final int SDK_AUTH_FLAG=12;
    private static ZfbLogin zfbLogin;
    private Context mContext;
    public ZfbLogin(Context context) {
        mContext = context;
    }
    synchronized  public static  ZfbLogin getInstrance(Context context) {
        if (zfbLogin == null) {
            zfbLogin =new ZfbLogin(context);
        }
        return zfbLogin;
    }

    /**
     * 支付宝账户授权业务
     *
     * @param
     */
    public void authV2() {

        ResponseBuilder<EmptyRequestObject, ZfbResonseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.ZFBAUTHINFO,ZfbResonseObject.class);
        builder.setCallBack(new BaseNetCallBack<ZfbResonseObject>() {
            @Override
            public void onSuccess(ZfbResonseObject object) {
                authsdk(object.getData());

            }

            @Override
            public void onFailure(ZfbResonseObject responseObject) {
                super.onFailure(responseObject);

            }
        }).send();

    }

    private void authsdk(final String authInfo){
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {

                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask((Activity)mContext);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);
                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    private String authcode="";

    private Handler mHandler = new Handler() {//支付结果的返回处理
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SDK_AUTH_FLAG:

                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.i("resultInfo",resultInfo);
                    String[] result=resultInfo.split("&");
                    for(int i=0;i<result.length;i++){
                        if(result[i].contains("auth_code")){
                            String[] id=result[i].split("=");
                            authcode=id[1];
                        }
                    }
                    if("".equals(authcode)){
                        Toast.makeText(mContext,"当前手机未安装支付宝客户端或授权失败",Toast.LENGTH_SHORT).show();
                    }else {
                        if(PreferencesConfig.v2_token.get().equals("")){
                            getInfo("zfb",authcode);
                        }else {
                            isBind("zfb",authcode);
                        }

                    }
                    break;
            }
        }
    };
    //判断是够已经绑定过
    private void isBind(String type,String openid) {

        IsBindRequestObject requestObject=new IsBindRequestObject();
        IsBindRequestParam param=new IsBindRequestParam();
        param.setType(type);
        param.setOpenid(openid);
        requestObject.setParam(param);
        ResponseBuilder<IsBindRequestObject, IsBindResponseObject> builder = new ResponseBuilder<>(requestObject, Config.ISBIND,IsBindResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<IsBindResponseObject>() {
            @Override
            public void onSuccess(IsBindResponseObject object) {
                String code=object.getCode();

                //1 时需要去绑定
//                BindActivity.sendParam((Activity)mContext,authcode,"","","zfb");
                PreferencesConfig.v2_iszfb.set("1");//表示绑定成功
                Toast.makeText(mContext, "绑定成功", Toast.LENGTH_SHORT).show();
                mListener.Success();
//                mContext;
            }

            @Override
            public void onFailure(IsBindResponseObject authLoginResponseObject) {

                if(authLoginResponseObject.getCode().equals("-2")) {//-2 表示改微信号已经被别的绑定了
                    Toast.makeText(mContext,authLoginResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }).send();
    }


    /**
     *
     * @param type
     * @param code
     */
    private void getInfo(String type,String code) {
        AuthLoginRequestObject requestObject=new AuthLoginRequestObject();
        AuthLoginRequestParam param=new AuthLoginRequestParam();
        param.setType(type);
        param.setOpenid(code);
        requestObject.setParam(param);
        ResponseBuilder<AuthLoginRequestObject, AuthLoginResponseObject> builder = new ResponseBuilder<>(requestObject, Config.AUTHLOGIN,AuthLoginResponseObject.class);
        builder.setCallBack(new NetCallBack<AuthLoginResponseObject>() {
            @Override
            public void onSuccess(AuthLoginResponseObject object) {
                String status=object.getData().getStatus();
                Log.i("vvvvvvvv222getInfo",status);
                if(status.equals("0")){//0 时需要去绑定
                    BindActivity.sendParam((Activity)mContext,authcode,"","","zfb");
                }else {//1 就直接去登录即可
                    savaTokenInfo(object.getData().getUser());
                }
            }

            @Override
            public void onNetFailure(String str) {

            }

            @Override
            public void onServerFailure(String str) {

            }

            @Override
            public void onFailure(AuthLoginResponseObject authLoginResponseObject) {

            }
        }).send();
    }
    private void savaTokenInfo(AuthLoginResponseData data) {
        PreferencesConfig.v2_token.set(data.getToken());
        PreferencesConfig.v2_loginName.set(data.getLoginName());
        PreferencesConfig.v2_nickname.set(data.getNickname());
        PreferencesConfig.v2_phone.set(data.getPhone());
        PreferencesConfig.v2_url.set(data.getImgUrl());
        PreferencesConfig.v2_uesrid.set(data.getUserId());
        PreferencesConfig.v2_iswx.set(data.getIsBindWx());
        PreferencesConfig.v2_iszfb.set(data.getIsBindZfb());

        JPushInterface.setAlias(mContext, PreferencesConfig.v2_phone.get(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i("ssssssssssss",i+"   "+s);
            }
        });

        mContext.startActivity(new Intent(mContext, MainActivity.class));
        ((Activity)mContext).finish();
    }

    private ZfbBandListener mListener;

    public ZfbBandListener getListener() {
        return mListener;
    }

    public void setListener(ZfbBandListener listener) {
        mListener = listener;
    }
}
