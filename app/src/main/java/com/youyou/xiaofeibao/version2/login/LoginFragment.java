package com.youyou.xiaofeibao.version2.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseFragment;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.login.LoginRequestObject;
import com.youyou.xiaofeibao.version2.request.login.LoginRequestParam;
import com.youyou.xiaofeibao.version2.response.login.LoginResponseObject;
import com.youyou.xiaofeibao.version2.response.login.LoginResponseParam;
import com.youyou.xiaofeibao.version2.tool.MD5;
import com.youyou.xiaofeibao.version2.tool.NumUtils;
import com.youyou.xiaofeibao.version2.zfb.ZfbLogin;
import com.youyou.xiaofeibao.wxapi.WechatLogin;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * 作者：young on 2016/10/4 11:15
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    @ViewInject(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;
    @ViewInject(R.id.tv_login)
    TextView tv_login;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_pwd)
    EditText et_pwd;
    @ViewInject(R.id.tv_weixin)
    TextView tv_weixin;
    @ViewInject(R.id.tv_zfb)
    TextView tv_zfb;



    @Override
    protected void initData() {

    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login_v2;
    }

    @Override
    public void onResume() {
        super.onResume();
//        new WechatLogin(getActivity()).dismissDialog();
    }

    @Override
    protected void setListener() {
        tv_login.setOnClickListener(this);
        tv_weixin.setOnClickListener(this);
        tv_zfb.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ForgetPwdActivity.class));
            }
        });


    }

    @Override
    public void onClick(View v) {
        int code = v.getId();

        switch (code) {
            case R.id.tv_login:
                loginCount();
                break;
            case R.id.tv_weixin:
                ((LoginOrRegisterActivity)getActivity()).showDialog();
                new WechatLogin(getActivity()).bindwx();
                break;
            case R.id.tv_zfb:
                ZfbLogin.getInstrance(getActivity()).authV2();
                break;
        }
    }

    private void loginCount() {
        String str = et_phone.getText().toString().trim();
        if (NumUtils.checkPhoneNum(str)) {
            LoginRequestObject requestObject = new LoginRequestObject();
            LoginRequestParam requestParam = new LoginRequestParam();
            requestParam.setPhone(str);
            requestParam.setPassword(MD5.getCode(et_pwd.getText().toString().trim()));
            requestObject.setParam(requestParam);
            ResponseBuilder<LoginRequestObject, LoginResponseObject> builder = new ResponseBuilder<>(requestObject, Config.LOGIN,LoginResponseObject.class);
            builder.setCallBack(new BaseNetCallBack<LoginResponseObject>() {
                @Override
                public void onSuccess(LoginResponseObject indexPageResponseObject) {
                    savaTokenInfo(indexPageResponseObject.getData());
                    showShortText("登录成功");
                    getActivity().finish();
                }

                @Override
                public void onFailure(LoginResponseObject Object) {
                    showShortText(Object.getMsg());
                }
            }).send();
        }
    }

    private void savaTokenInfo(LoginResponseParam data) {
        PreferencesConfig.v2_token.set(data.getToken());
        PreferencesConfig.v2_loginName.set(data.getLoginName());
        PreferencesConfig.v2_phone.set(data.getPhone());
        PreferencesConfig.v2_nickname.set(data.getNickname());
        PreferencesConfig.v2_url.set(data.getImgUrl());
        PreferencesConfig.v2_uesrid.set(data.getUserId());
        PreferencesConfig.v2_iswx.set(data.getIsBindWx());
        PreferencesConfig.v2_iszfb.set(data.getIsBindZfb());

        JPushInterface.setAlias(getActivity(), PreferencesConfig.v2_phone.get(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });

    }

    private void showShortText(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
