package com.youyou.xiaofeibao.version2.pay;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.util.LogUtils;
import com.youyou.xiaofeibao.version2.home.hot.SubscribeActivity;

/**
 * 作者：young on 2017/3/31 11:01
 */

public class PaySuccessActivity extends BaseTitleActivity {

    @ViewInject(R.id.iv_success)
    de.hdodenhof.circleimageview.CircleImageView iv_success;
    @ViewInject(R.id.tv_done)
    TextView tv_done;

    private boolean isDone=false;


    @Override
    protected int getTitleText() {
        return R.string.title_paysuccess;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_paysuccess;
    }

    @Override
    protected void initData() {
        super.initData();
        Glide.with(mActivity).load(R.drawable.success).into(iv_success);

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDone = true;
                startActivity(new Intent(mActivity, SubscribeActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isDone){
            startActivity(new Intent(mActivity, SubscribeActivity.class));
        }
    }
}
