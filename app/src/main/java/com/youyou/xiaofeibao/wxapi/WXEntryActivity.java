/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.youyou.xiaofeibao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youyou.xiaofeibao.MainActivity;
import com.youyou.xiaofeibao.common.MainThreadPostUtils;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.net.CommonOkHttpClient;
import com.youyou.xiaofeibao.net.CommonRequest;
import com.youyou.xiaofeibao.version2.BindActivity;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.request.authlogin.AuthLoginRequestObject;
import com.youyou.xiaofeibao.version2.request.authlogin.AuthLoginRequestParam;
import com.youyou.xiaofeibao.version2.request.isbind.IsBindRequestObject;
import com.youyou.xiaofeibao.version2.request.isbind.IsBindRequestParam;
import com.youyou.xiaofeibao.version2.response.authlogin.AuthLoginResponseData;
import com.youyou.xiaofeibao.version2.response.authlogin.AuthLoginResponseObject;
import com.youyou.xiaofeibao.version2.response.isbind.IsBindResponseObject;

import org.json.JSONException;

import java.io.IOException;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 微信客户端回调activity示例
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private String img = "";
    private String nickname = "";
    private String token;
    private String openid = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果分享的时候，该界面没有开启，那么微信开始这个activity时，会调用onCreate，所以这里要处理微信的返回结果
        api = WXAPIFactory.createWXAPI(this, Config.WX_SHARE);
        api.registerApp(Config.WX_SHARE);
        api.handleIntent(getIntent(), this);
        Log.i("WXEntryActivity111", "000000000");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 如果分享的时候，该已经开启，那么微信开始这个activity时，会调用onNewIntent，所以这里要处理微信的返回结果
        setIntent(intent);
        api.handleIntent(intent, this);

    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {

        String result = "";
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                try {
                    String code = ((SendAuth.Resp) resp).code; //即为所需的code，登录
                    getOpenId(code);

                } catch (Exception e) {//分享
                    result = "操作成功!";
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    // 关闭页面
                    this.finish();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "操作取消";
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                // 关闭页面
                this.finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "操作拒绝";
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                // 关闭页面
                this.finish();
                break;
            default:
                result = "操作返回";
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                // 关闭页面
                this.finish();
                break;
        }
    }


    /**
     * 获取openid，调用微信的接口
     *
     * @param code
     */
    private void getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Config.WX_SHARE + "&secret=" + Config.SECRET + "&code=" + code + "&grant_type=authorization_code";
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, null), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                MainThreadPostUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        String str = null;
                        try {
                            str = response.body().string();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (str != null && !"".equals(str)) {
                            try {
                                org.json.JSONObject object = new org.json.JSONObject(str);
                                openid = object.getString("unionid").toString().trim();
                                token = object.getString("access_token").toString().trim();
                                if (PreferencesConfig.v2_token.get().equals("")) {//未登录状态进行绑定
                                    getInfo("wx", openid);
                                } else {//登录状态，绑定
                                    isBind();
                                }
                            } catch (JSONException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }

    private void isBind() {

        IsBindRequestObject requestObject = new IsBindRequestObject();
        IsBindRequestParam param = new IsBindRequestParam();
        param.setType("wx");
        param.setOpenid(openid);
        requestObject.setParam(param);
        ResponseBuilder<IsBindRequestObject, IsBindResponseObject> builder = new ResponseBuilder<>(requestObject, Config.ISBIND, IsBindResponseObject.class);
        builder.setCallBack(new NetCallBack<IsBindResponseObject>() {
            @Override
            public void onSuccess(IsBindResponseObject object) {
                String code = object.getCode();
                Log.i("vvvvvvvv222getInfocode", code);
                //1 时需要去绑定，success的方法判断条件就是状态为 1.通过token获取手机号等信息
//                getUserMesg(token, openid);
                PreferencesConfig.v2_iswx.set("1");//表示绑定成功
                Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onNetFailure(String str) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onServerFailure(String str) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(IsBindResponseObject object) {
                String code = object.getCode();
                if (code.equals("-2")) {
                    Toast.makeText(getApplicationContext(), object.getMsg(), Toast.LENGTH_SHORT).show();
                } else if (code.equals("-1")) {
                    startActivity(new Intent(WXEntryActivity.this, LoginOrRegisterActivity.class));
                }
                finish();
            }
        }).send();
    }

    /**
     * 判断是够已经绑定过
     *
     * @param type
     * @param code
     */
    private void getInfo(String type, String code) {
        AuthLoginRequestObject requestObject = new AuthLoginRequestObject();
        AuthLoginRequestParam param = new AuthLoginRequestParam();
        param.setType(type);
        param.setOpenid(code);
        requestObject.setParam(param);
        ResponseBuilder<AuthLoginRequestObject, AuthLoginResponseObject> builder = new ResponseBuilder<>(requestObject, Config.AUTHLOGIN, AuthLoginResponseObject.class);
        builder.setCallBack(new NetCallBack<AuthLoginResponseObject>() {
            @Override
            public void onSuccess(AuthLoginResponseObject object) {
                String status = object.getData().getStatus();

                if (status.equals("0")) {//0 时需要去绑定
                    getUserMesg(token, openid);
                } else {//1 就直接去登录即可
                    savaTokenInfo(object.getData().getUser());
                }
            }

            @Override
            public void onNetFailure(String str) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("vvvvvvvv222getInfocache", "33333333");
                        Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onServerFailure(String str) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("vvvvvvvv222getInfocache", "222222");
                        Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(AuthLoginResponseObject authLoginResponseObject) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("vvvvvvvv222getInfocache", "1111");
                        Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
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

        JPushInterface.setAlias(WXEntryActivity.this, PreferencesConfig.v2_phone.get(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });

        startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
        finish();

    }

    /**
     * 获取微信的个人信息，调用微信的接口
     *
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;

        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, null), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                MainThreadPostUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        String str = null;
                        try {
                            str = response.body().string();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (str != null && !"".equals(str)) {
                            try {
                                org.json.JSONObject object = new org.json.JSONObject(str);
                                nickname = object.getString("nickname");
                                img = object.getString("headimgurl");
                                BindActivity.sendParam(WXEntryActivity.this, openid, nickname, img, "wx");
                                finish();
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "获取数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                });
            }
        });

    }
}
