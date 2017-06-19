package com.youyou.xiaofeibao.version2.home.benefit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 作者：young on 2016/12/21 19:43
 */

public class AgentPreActivity extends BaseTitleActivity {

    @ViewInject(R.id.iv_img)
    ImageView miv_img;
    @ViewInject(R.id.tv_supply)
    TextView tv_supply;

    @Override
    protected int getTitleText() {
        return R.string.pre_dai;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_agentpre;
    }

    @Override
    protected void setListener() {
        super.setListener();

        tv_supply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void sendData() {
        ResponseBuilder<EmptyRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.PROXY,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject baseResponseObject) {
                Toast.makeText(mActivity,"申请成功，稍后会有客服联系您！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).send();

    }
}
