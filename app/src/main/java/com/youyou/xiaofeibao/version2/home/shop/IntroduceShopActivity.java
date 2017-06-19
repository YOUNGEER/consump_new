package com.youyou.xiaofeibao.version2.home.shop;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 作者：young on 2016/11/30 11:40
 */

public class IntroduceShopActivity extends BaseTitleActivity {
    @ViewInject(R.id.tv_supply)
    TextView tv_supply;

    @Override
    protected int getTitleText() {
        return R.string.produceshop;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_produceshop;
    }


    @Override
    protected void initData() {
        super.initData();

        tv_supply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mActivity, BeShopActivity.class),1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            finish();
        }
    }
}
