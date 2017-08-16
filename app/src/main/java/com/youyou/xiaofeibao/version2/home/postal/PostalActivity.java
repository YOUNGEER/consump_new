package com.youyou.xiaofeibao.version2.home.postal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.LayoutUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;


/**
 * 作者：young on 2016/12/24 14:14
 */

public class PostalActivity extends BaseTitleActivity {
    @ViewInject(R.id.tv_yingshou)
    TextView tv_yingshou;
    @ViewInject(R.id.tv_tongbaobi)
    TextView tv_tongbaobi;
    @ViewInject(R.id.tv_yingshou_money)
    TextView tv_yingshou_money;
    @ViewInject(R.id.tv_tongbaobi_money)
    TextView tv_tongbaobi_money;

    @Override
    protected int getTitleText() {
        return R.string.postal_count;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_postal;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        super.initView();

        /**rightView*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        int rightMargin = LayoutUtils.dip2px(8);
        layoutParams.setMargins(0, 0, rightMargin, 0);
        TextView tv = new TextView(this);
        tv.setClickable(true);
        tv.setText(R.string.postal_history);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(Color.WHITE);
        setRightTitleView(tv, layoutParams);
        tv.setOnClickListener(v -> startActivity(new Intent(mActivity, PostalHistoryActivity.class)));

        tv_yingshou_money.setText(getIntent().getStringExtra("yingshou"));
        tv_tongbaobi_money.setText(getIntent().getStringExtra("tongbaobi"));

    }

    @Override
    protected void initData() {
        super.initData();

        tv_yingshou.setText("立即提现 >");
        tv_tongbaobi.setText("立即提现 >");

        //营收
        tv_yingshou.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, Postal2Activity.class);
            intent.setAction(tv_yingshou_money.getText().toString().trim());
            intent.putExtra("from", getIntent().getIntExtra("from", 0));
            intent.putExtra("type", "1");
            startActivityForResult(intent, 1);
        });

        //智惠币
        tv_tongbaobi.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, Postal2Activity.class);
            intent.setAction(tv_tongbaobi_money.getText().toString().trim());
            intent.putExtra("type", "2");
            startActivityForResult(intent, 1);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            setResult(1);
            finish();
        }
    }
}
