package com.youyou.xiaofeibao.version2;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.MainActivity;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.request.bind.BindRequestObject;
import com.youyou.xiaofeibao.version2.request.bind.BindRequestParam;
import com.youyou.xiaofeibao.version2.request.msg_register.MsgRegRequestObject;
import com.youyou.xiaofeibao.version2.request.msg_register.MsgRegRequestParam;
import com.youyou.xiaofeibao.version2.response.authlogin.AuthLoginResponseData;
import com.youyou.xiaofeibao.version2.response.bind.BindResponseObject;
import com.youyou.xiaofeibao.version2.response.msgreg.MsgRegResponseObject;
import com.youyou.xiaofeibao.version2.tool.NumUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 作者：young on 2017/2/24 14:12
 */

public class BindActivity extends BaseTitleActivity implements View.OnClickListener {

    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.tv_yan)
    TextView tv_yan;
    @ViewInject(R.id.et_yan)
    EditText et_yan;

    @ViewInject(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;

    private BindActivity.TimeCount time;


    private String phone="";
    private String validate="";
    private String openid="";
    private String nickname="";
    private String img="";
    private String type="";

    public static void sendParam(Context context,String openid, String nickname, String img,String type){
        Log.i("vvvvvvvv222getInfo","bindactivity");
        Intent intent=new Intent(context,BindActivity.class);
        intent.putExtra("openid",openid);
        intent.putExtra("nickname",nickname);
        intent.putExtra("img",img);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected int getTitleText() {
        return R.string.bindtitle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_v2;
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_yan.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        et_phone.setText(PreferencesConfig.v2_phone.get());
    }

    @Override
    protected void initData() {
        super.initData();
        openid=getIntent().getStringExtra("openid");
        img=getIntent().getStringExtra("img");
        nickname=getIntent().getStringExtra("nickname");
        type=getIntent().getStringExtra("type");

    }

    @Override
    public void onClick(View v) {
        int code = v.getId();
        switch (code) {
            case R.id.tv_yan:
                getYanClick();
                break;
            case R.id.tv_forget_pwd:
                registerMethod();
                break;
        }
    }

    private void registerMethod() {
        if (!checkInfoFormat()) {
            return;
        }
        BindRequestObject requestObject = new BindRequestObject();
        BindRequestParam param = new BindRequestParam();
        param.setType(type);
        param.setPhone(et_phone.getText().toString().trim());
        param.setValidatecode(et_yan.getText().toString().trim());
        param.setNickName(nickname);
        param.setOpenid(openid);
        param.setHeadimgurl(img);
        requestObject.setParam(param);

        ResponseBuilder<BindRequestObject, BindResponseObject> builder = new ResponseBuilder<>(requestObject, Config.BIND,BindResponseObject.class);
        builder.setCallBack(new NetCallBack<BindResponseObject>() {
            @Override
            public void onSuccess(BindResponseObject reponse) {
                savaTokenInfo(reponse.getData());
                startActivity(new Intent(BindActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onNetFailure(String str) {
            }

            @Override
            public void onServerFailure(String str) {
            }

            @Override
            public void onFailure(BindResponseObject Object) {
                Toast.makeText(mActivity, Object.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }).send();

    }

    private void savaTokenInfo(AuthLoginResponseData data) {
        PreferencesConfig.v2_token.set(data.getToken());
        PreferencesConfig.v2_loginName.set(data.getLoginName());
        PreferencesConfig.v2_phone.set(data.getPhone());
        PreferencesConfig.v2_nickname.set(data.getNickname());
        PreferencesConfig.v2_url.set(data.getImgUrl());
        PreferencesConfig.v2_uesrid.set(data.getUserId());
        PreferencesConfig.v2_iswx.set(data.getIsBindWx());
        PreferencesConfig.v2_iszfb.set(data.getIsBindZfb());

        JPushInterface.setAlias(BindActivity.this, PreferencesConfig.v2_phone.get(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
        finish();
    }

    private boolean checkInfoFormat() {
        if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
            return false;
        }

        if (TextUtils.isEmpty(et_yan.getText().toString().trim())) {
            return false;
        }
        return true;
    }

    private void getYanClick() {
        String phone = et_phone.getText().toString().trim();
        if (NumUtils.checkPhoneNum(phone)) {
            if (time == null) {
                time = new TimeCount(60000, 1000);
            }
            time.start();
            MsgRegRequestObject regRequestObject = new MsgRegRequestObject();
            MsgRegRequestParam regRequestParam = new MsgRegRequestParam();
            regRequestParam.setPhone(phone);
            regRequestParam.setType("0");
            regRequestObject.setParam(regRequestParam);
            ResponseBuilder<MsgRegRequestObject, MsgRegResponseObject> builder = new ResponseBuilder<>(regRequestObject, Config.BINDMGS,MsgRegResponseObject.class);
            builder.setCallBack(new NetCallBack<MsgRegResponseObject>() {
                @Override
                public void onSuccess(MsgRegResponseObject reponse) {
                    Toast.makeText(mActivity, "发送成功", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNetFailure(String str) {
                }

                @Override
                public void onServerFailure(String str) {
                }

                @Override
                public void onFailure(MsgRegResponseObject Object) {

                }
            }).send();

        } else {
            Toast.makeText(mActivity, "手机格式不正确", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 用来计时  显示一定时间内不可重复点击
     *
     * @author Administrator
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tv_yan.setClickable(true);
            tv_yan.setActivated(false);
            tv_yan.setText("重新验证");
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tv_yan.setText(millisUntilFinished / 1000 + "s");
        }
    }
}
