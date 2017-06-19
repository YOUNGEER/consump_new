package com.youyou.xiaofeibao.version2.tool;

import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.youyou.xiaofeibao.ConsumApplication;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.msg_register.MsgRegRequestObject;
import com.youyou.xiaofeibao.version2.request.msg_register.MsgRegRequestParam;
import com.youyou.xiaofeibao.version2.response.msgreg.MsgRegResponseObject;

/**
 * 作者：young on 2017/4/18 17:24
 */

public class GetCertificCodeUtils {
    private TextView tv_yan;
    private TimeCount time;

    public void getCertificVoice( String phone, String type){
        if(!NumUtils.checkPhoneNum(phone)){
            Toast.makeText(ConsumApplication.getInstance(), "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        MsgRegRequestObject regRequestObject = new MsgRegRequestObject();
        MsgRegRequestParam regRequestParam = new MsgRegRequestParam();
        regRequestParam.setPhone(phone);
        regRequestParam.setType(type);
        regRequestObject.setParam(regRequestParam);
        ResponseBuilder<MsgRegRequestObject, MsgRegResponseObject> builder = new ResponseBuilder<>(regRequestObject, Config.GETMSG,MsgRegResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<MsgRegResponseObject>() {
            @Override
            public void onSuccess(MsgRegResponseObject reponse) {
                Toast.makeText(ConsumApplication.getInstance(), "发送成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(MsgRegResponseObject Object) {
                Toast.makeText(ConsumApplication.getInstance(), Object.getMsg(), Toast.LENGTH_SHORT).show();

            }
        }).send();
    }

    public void getCertificMsgToast(String type){
        MsgRegRequestObject regRequestObject = new MsgRegRequestObject();
        MsgRegRequestParam regRequestParam = new MsgRegRequestParam();
        regRequestParam.setPhone(PreferencesConfig.v2_phone.get());
        regRequestParam.setType(type);
        regRequestObject.setParam(regRequestParam);
        ResponseBuilder<MsgRegRequestObject, MsgRegResponseObject> builder = new ResponseBuilder<>(regRequestObject, Config.GETMSG,MsgRegResponseObject.class);
        builder.setCallBack(new NetCallBack<MsgRegResponseObject>() {
            @Override
            public void onSuccess(MsgRegResponseObject reponse) {
                Toast.makeText(ConsumApplication.getInstance(), "短信发送成功，注意查收", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNetFailure(String str) {
            }

            @Override
            public void onServerFailure(String str) {
            }

            @Override
            public void onFailure(MsgRegResponseObject Object) {
                Toast.makeText(ConsumApplication.getInstance(), Object.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }).send();
    }

    public void getCettificMsg(TextView textView, String phone,String type){
                tv_yan=textView;
        if (NumUtils.checkPhoneNum(phone)) {
            if (time == null) {
                time = new TimeCount(60000, 1000);
            }
            time.start();
            MsgRegRequestObject regRequestObject = new MsgRegRequestObject();
            MsgRegRequestParam regRequestParam = new MsgRegRequestParam();
            regRequestParam.setPhone(phone);
            regRequestParam.setType(type);
            regRequestObject.setParam(regRequestParam);
            ResponseBuilder<MsgRegRequestObject, MsgRegResponseObject> builder = new ResponseBuilder<>(regRequestObject, Config.GETMSG,MsgRegResponseObject.class);
            builder.setCallBack(new NetCallBack<MsgRegResponseObject>() {
                @Override
                public void onSuccess(MsgRegResponseObject reponse) {
                    Toast.makeText(ConsumApplication.getInstance(), "发送成功", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNetFailure(String str) {
                }

                @Override
                public void onServerFailure(String str) {
                }

                @Override
                public void onFailure(MsgRegResponseObject Object) {
                    Toast.makeText(ConsumApplication.getInstance(), Object.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }).send();
        } else {
            Toast.makeText(ConsumApplication.getInstance(), "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
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
