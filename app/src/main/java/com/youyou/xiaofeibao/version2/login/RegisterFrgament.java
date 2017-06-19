package com.youyou.xiaofeibao.version2.login;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseFragment;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.register.RegisterRequestObject;
import com.youyou.xiaofeibao.version2.request.register.RegisterRequestParam;
import com.youyou.xiaofeibao.version2.response.login.LoginResponseObject;
import com.youyou.xiaofeibao.version2.response.login.LoginResponseParam;
import com.youyou.xiaofeibao.version2.tool.GetCertificCodeUtils;
import com.youyou.xiaofeibao.version2.tool.NumUtils;
import com.youyou.xiaofeibao.version2.zfb.ZfbLogin;
import com.youyou.xiaofeibao.wxapi.WechatLogin;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.youyou.xiaofeibao.R.id.et_yan;

/**
 * 作者：young on 2016/10/4 11:15
 */

public class RegisterFrgament extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.tv_yan)
    TextView tv_yan;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.tv_register)
    TextView tv_register;
    @ViewInject(et_yan)
    EditText ed_yan;
    @ViewInject(R.id.et_pwd)
    EditText et_pwd;
    @ViewInject(R.id.et_yaoqing)
    EditText et_yaoqing;
    @ViewInject(R.id.cb_protocal)
    CheckBox cb_protocal;
    @ViewInject(R.id.tv_zfb)
    TextView tv_zfb;
    @ViewInject(R.id.tv_voice)
    TextView tv_voice;
    @ViewInject(R.id.tv_weixin)
    TextView tv_weixin;
    private IWXAPI wechatapi;

    @Override
    protected void initData() {
        wechatapi= WXAPIFactory.createWXAPI(getContext(), Config.WX_SHARE);
        wechatapi.registerApp(Config.WX_SHARE);
    }

    @Override
    protected void updateUI() {}

    @Override
    protected void setListener() {
        tv_yan.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_weixin.setOnClickListener(this);
        tv_zfb.setOnClickListener(this);
        tv_voice.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register_v2;
    }

    @Override
    public void onClick(View v) {
        int code = v.getId();
        switch (code) {
            case R.id.tv_yan:
                new GetCertificCodeUtils().getCettificMsg(tv_yan,et_phone.getText().toString().trim(),"0");
                break;
            case R.id.tv_voice:
                new GetCertificCodeUtils().getCertificVoice(et_phone.getText().toString().trim(),"-1");
                break;
            case R.id.tv_register:
                registerMethod();
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

    private void registerMethod() {
        if (!checkInfoFormat()) {
            return;
        }
        RegisterRequestObject requestObject = new RegisterRequestObject();
        RegisterRequestParam param = new RegisterRequestParam();
        param.setInvitecode(et_yaoqing.getText().toString().trim());
        param.setPhone(et_phone.getText().toString().trim());
        param.setValidatecode(ed_yan.getText().toString().trim());
        requestObject.setParam(param);
        ResponseBuilder<RegisterRequestObject, LoginResponseObject> builder = new ResponseBuilder<>(requestObject, Config.REGISTER,LoginResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<LoginResponseObject>() {
            @Override
            public void onSuccess(LoginResponseObject reponse) {
                savaTokenInfo(reponse.getData());
                showShortText("注册成功,密码会以短信的形式发送过来");
                getActivity().finish();
            }
            @Override
            public void onFailure(LoginResponseObject Object) {
                showShortText(Object.getMsg());
            }
        }).send();
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

    /**
     * 注册参数进行检查
     */
    private boolean checkInfoFormat() {
        if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
            showShortText("请填写手机号码");
            return false;
        }

        NumUtils.checkPhoneNum(et_phone.getText().toString().trim());

        if (TextUtils.isEmpty(ed_yan.getText().toString().trim())) {
            showShortText("验证码不能为空");
            return false;
        }

        if (!cb_protocal.isChecked()) {
            showShortText("请同意注册协议");
            return false;
        }

        return true;

    }

    private void showShortText(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    public void setRegisterSuccess(Context context){

    }

}
