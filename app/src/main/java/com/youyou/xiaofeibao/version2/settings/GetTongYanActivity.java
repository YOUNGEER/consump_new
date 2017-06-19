package com.youyou.xiaofeibao.version2.settings;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.tongpwd.TongpwdRequestObject;
import com.youyou.xiaofeibao.version2.request.tongpwd.TongpwdRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.tool.GetCertificCodeUtils;
import com.youyou.xiaofeibao.version2.tool.MD5;

/**
 * 作者：young on 2016/12/21 15:29
 */

public class GetTongYanActivity extends BaseTitleActivity {
    @ViewInject(R.id.et_yan)
    EditText et_yan;
    @ViewInject(R.id.tv_next)
    TextView tv_next;

    @Override
    protected int getTitleText() {
        return R.string.input_yan;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_tongyan_activity;
    }

    @Override
    protected void initData() {
        super.initData();
        new GetCertificCodeUtils().getCertificMsgToast("1");
        
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_yan.getText().toString().trim()))
                    sendData();
            }
        });

    }

    private void sendData() {
        TongpwdRequestObject requestObject = new TongpwdRequestObject();
        TongpwdRequestParam param = new TongpwdRequestParam();
        param.setPhone(PreferencesConfig.v2_phone.get());
        param.setRepassword(MD5.getCode(getIntent().getAction()));
        param.setType("2");
        param.setValidatecode(et_yan.getText().toString().trim());
        requestObject.setParam(param);
        ResponseBuilder<TongpwdRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.TONGPWD,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject Object) {
                Toast.makeText(mActivity, "设置成功", Toast.LENGTH_SHORT).show();
                setResult(2);
                finish();
            }
        }).send();
    }
}
