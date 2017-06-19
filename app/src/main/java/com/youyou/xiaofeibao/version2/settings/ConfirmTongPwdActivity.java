package com.youyou.xiaofeibao.version2.settings;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.pay.pwd.GridPasswordView;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.tongpwd.TongpwdRequestObject;
import com.youyou.xiaofeibao.version2.request.tongpwd.TongpwdRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.tong.TongResponseObject;
import com.youyou.xiaofeibao.version2.tool.MD5;

/**
 * 作者：young on 2016/12/21 15:19
 */

public class ConfirmTongPwdActivity extends BaseTitleActivity {
    @ViewInject(R.id.pv_set)
    GridPasswordView pv_set;
    @ViewInject(R.id.tv_next)
    TextView tv_next;

    private boolean isFirst=false;

    private String firstStr;

    @Override
    protected int getTitleText() {
        return R.string.confirm_tong;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_tongpwdconfirm_activity;
    }

    @Override
    protected void initData() {
        super.initData();
        firstStr = getIntent().getAction();
        getTongNum();


        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != pv_set.getPassWord().trim() && pv_set.getPassWord().length() == 6) {

                    if (!firstStr.equals(pv_set.getPassWord())) {
                        Toast.makeText(mActivity, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!isFirst){
                        Intent intent = new Intent(mActivity, GetTongYanActivity.class);
                        intent.setAction(pv_set.getPassWord());
                        startActivityForResult(intent, 2);
                    }else {
                        sendData();
                    }

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == 2) {
            setResult(1);
            finish();
        }
    }

    /**
     * 是否设置过通宝币密码，ture为是第一次设置
     */
    private void getTongNum() {
        ResponseBuilder<EmptyRequestObject, TongResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.TONGNUM,TongResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<TongResponseObject>() {
            @Override
            public void onSuccess(TongResponseObject Object) {

                if ("0".equals(Object.getData().getHasPayPwd())) {
                    isFirst=true;
                }else {
                    isFirst=false;
                }
            }
        }).send();
    }

    private void sendData() {
        TongpwdRequestObject requestObject = new TongpwdRequestObject();
        TongpwdRequestParam param = new TongpwdRequestParam();
        param.setPhone(PreferencesConfig.v2_phone.get());
        param.setRepassword(MD5.getCode(getIntent().getAction()));
        param.setType("2");
        param.setValidatecode("");
        requestObject.setParam(param);
        ResponseBuilder<TongpwdRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.TONGPWD,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject Object) {
                Toast.makeText(mActivity, "设置成功", Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();
            }
        }).send();

    }

}
