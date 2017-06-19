package com.youyou.xiaofeibao.version2.home.count;

import android.os.Bundle;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.version2.response.mycount.MycountResponseParam;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 作者：young on 2016/12/19 14:57
 */

public class BenefitInfoActivity extends BaseTitleActivity {
    @ViewInject(R.id.tv_shopname)
    TextView tv_shopname;
    @ViewInject(R.id.tv_num)
    TextView tv_num;
    @ViewInject(R.id.tv_duifang)
    TextView tv_duifang;
    @ViewInject(R.id.tv_time)
    TextView tv_time;
    @ViewInject(R.id.tv_des)
    TextView tv_des;
    @ViewInject(R.id.tv_dui)
    TextView tv_dui;


    @Override
    protected int getTitleText() {
        return R.string.benefit_info;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_benefitinfo;
    }

    @Override
    protected void initData() {
        super.initData();

        Bundle bundle = getIntent().getExtras();
        MycountResponseParam param = (MycountResponseParam) bundle.getSerializable("data");

        tv_shopname.setText(param.getShopName());
        tv_time.setText(param.getCreateyear()+" "+param.getCreatedate()+" "+param.getCreatetime());
        tv_des.setText(param.getAccount_description());
        if (0==param.getType()) {//0 是支出
            tv_num.setText("-" + param.getGoldnum());
        } else {//1 是收入
            tv_num.setText("+" + param.getGoldnum());
        }
        tv_duifang.setText(param.getShopName());


        if("1".equals(getIntent().getAction())){
            tv_dui.setText("类型");
        }
    }
}
