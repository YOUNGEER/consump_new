package com.youyou.xiaofeibao.version2.settings;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.version2.pay.pwd.GridPasswordView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 作者：young on 2016/12/21 14:59
 */

public class ChangeTongPwdActivty extends BaseTitleActivity {
    @ViewInject(R.id.tv_next)
    TextView tv_next;
    @ViewInject(R.id.pv_set)
    GridPasswordView pv_set;

    @Override
    protected int getTitleText() {
        return R.string.change_tong_pwd;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_tongpwd_activity;
    }

    @Override
    protected void initData() {
        super.initData();

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != pv_set.getPassWord().trim() && pv_set.getPassWord().length() == 6) {
                    Intent intent = new Intent(mActivity, ConfirmTongPwdActivity.class);
                    intent.setAction(pv_set.getPassWord());
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            finish();
        }
    }
}
