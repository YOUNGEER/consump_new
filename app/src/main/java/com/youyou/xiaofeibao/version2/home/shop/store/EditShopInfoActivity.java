package com.youyou.xiaofeibao.version2.home.shop.store;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 作者：young on 2016/12/26 15:25
 */

public class EditShopInfoActivity extends BaseTitleActivity {
    @ViewInject(R.id.et_content)
    EditText et_content;
    @ViewInject(R.id.tv_next)
    TextView tv_next;

    @Override
    protected int getTitleText() {
        return R.string.edit_shopinfo;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_shopinfo;
    }

    @Override
    protected void setListener() {
        super.setListener();


        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", et_content.getText().toString().trim());
                setResult(1, intent);
                finish();
            }
        });

    }
}
