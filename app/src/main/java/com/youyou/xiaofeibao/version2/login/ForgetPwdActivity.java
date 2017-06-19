package com.youyou.xiaofeibao.version2.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.KeyBoardAutoDownActivity;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.changepwd.ChangePwdRequestParam;
import com.youyou.xiaofeibao.version2.request.changepwd.ChangePwdrequestObjecet;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.tool.GetCertificCodeUtils;
import com.youyou.xiaofeibao.version2.tool.MD5;

/**
 * 作者：young on 2016/10/4 14:05
 */

public class ForgetPwdActivity extends KeyBoardAutoDownActivity implements View.OnClickListener {
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.tv_yan)
    TextView tv_yan;
    @ViewInject(R.id.et_yan)
    EditText et_yan;
    @ViewInject(R.id.et_pwd)
    EditText et_pwd;
    @ViewInject(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;
    @ViewInject(R.id.tv_voice)
    TextView tv_voice;


    @Override
    protected int getTitleText() {
        return R.string.titlt_repwd;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repwd_v2;
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_yan.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        tv_voice.setOnClickListener(this);
        et_phone.setText(PreferencesConfig.v2_phone.get());
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        int code = v.getId();
        switch (code) {
            case R.id.tv_yan:
                new GetCertificCodeUtils().getCettificMsg(tv_yan,et_phone.getText().toString().trim(),"1");
                break;
            case R.id.tv_forget_pwd:
                registerMethod();
                break;
            case R.id.tv_voice:
                new GetCertificCodeUtils().getCertificVoice(et_phone.getText().toString().trim(),"-2");
                break;
        }
    }

    private void registerMethod() {
        if (!checkInfoFormat()) {
            return;
        }
        ChangePwdrequestObjecet requestObject = new ChangePwdrequestObjecet();
        ChangePwdRequestParam param = new ChangePwdRequestParam();
        param.setType("1");
        param.setRepassword(MD5.getCode(et_pwd.getText().toString().trim()));
        param.setPhone(et_phone.getText().toString().trim());
        param.setValidatecode(et_yan.getText().toString().trim());
        requestObject.setParam(param);
        ResponseBuilder<ChangePwdrequestObjecet, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.RESETPWD,BaseResponseObject.class);
        builder.setCallBack(new NetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject reponse) {
                Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show();
                if(null!=getIntent()&&null!=getIntent().getAction()&&getIntent().getAction().equals("1")){//表示是修改密码而不是忘记密码
                    PreferencesConfig.v2_token.set("");
                    PreferencesConfig.v2_loginName.set("");
                    PreferencesConfig.v2_url.set("");
                    PreferencesConfig.v2_phone.set("");
                    setResult(1);
                }
                finish();
            }

            @Override
            public void onNetFailure(String str) {

            }

            @Override
            public void onServerFailure(String str) {
            }

            @Override
            public void onFailure(BaseResponseObject Object) {
                Toast.makeText(mActivity, Object.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }).send();

    }

    private boolean checkInfoFormat() {
        if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
            return false;
        }
        if (TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
            return false;
        }
        if (TextUtils.isEmpty(et_yan.getText().toString().trim())) {
            return false;
        }
        return true;
    }


}
