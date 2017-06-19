package com.youyou.xiaofeibao.version2.pay;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.KeyBoardAutoDownActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 作者：young on 2016/11/1 15:41
 */

public class PayActivity extends KeyBoardAutoDownActivity implements View.OnClickListener {

    @ViewInject(R.id.tv_num)
    TextView tv_num;

    @ViewInject(R.id.iv_wx_pay)
    CheckBox cb_wx_pay;
    @ViewInject(R.id.iv_alipay_pay)
    CheckBox cb_alipay_pay;
    @ViewInject(R.id.pay)
    TextView pay;

    @ViewInject(R.id.rl_zfb)
    RelativeLayout rl_zfb;
    @ViewInject(R.id.rl_wx)
    RelativeLayout rl_wx;

    private String title = "支付订单";
    private String ordertype = "0";
    private String tomemid;
    private String price;
    private String total_price;
    private String price_tbb = "0";
    private String paytype = "2";//1:微信支付，2：支付宝支付，3：智惠币支付
    private String zfpass;

    private PayTask task;

    @Override
    protected int getTitleText() {
        return R.string.payment_hint;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_pay;
    }

    @Override
    protected void setListener() {
        super.setListener();
        rl_zfb.setOnClickListener(this);
        rl_wx.setOnClickListener(this);
        pay.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //选择支付宝支付方式时微信选择为未选择

        cb_alipay_pay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && cb_wx_pay.isChecked()) {
                    cb_wx_pay.setChecked(false);
                }
                if (cb_wx_pay.isChecked()) {
                    paytype = "1";
                }
            }
        });
        //选择微信时支付宝为未选择
        cb_wx_pay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && cb_alipay_pay.isChecked()) {
                    cb_alipay_pay.setChecked(false);
                }
            }
        });

        tomemid = getIntent().getAction();


        price = getIntent().getStringExtra("price");//实际支付的金额
        zfpass = getIntent().getStringExtra("pwd");
        price_tbb = getIntent().getStringExtra("price_tbb");
        total_price=getIntent().getStringExtra("total_price");
        tv_num.setText("确认付款 " + price + "元");
        pay.setText(price + "元 确认付款");
        if("".equals(zfpass)||null==zfpass){
            zfpass="";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_zfb:
                if (cb_alipay_pay.isChecked()) {
                    cb_alipay_pay.setChecked(false);
                } else {
                    cb_alipay_pay.setChecked(true);
                }
                break;
            case R.id.rl_wx:
                if (cb_wx_pay.isChecked()) {
                    cb_wx_pay.setChecked(false);
                } else {
                    cb_wx_pay.setChecked(true);
                }
                break;
            case R.id.pay:
                if (checkData()) {
                    if (task == null) {
                        task = new PayTask();
                    }
                    task.setListener(new PayTask.PayListener() {
                        @Override
                        public void successBenefit2() {
                            pay.setClickable(true);
                            Log.i("sssssssssss11111", "fffffff");
                        }

                        @Override
                        public void onFalie2() {
                            pay.setClickable(true);
                            Log.i("sssssssssss222222", "fffffff");
                        }
                    });
//                    pay.setClickable(false);

                    if (cb_wx_pay.isChecked()) {
                        paytype = "1";
                        if (task == null) {
                            task = new PayTask();
                        }
                        task.PayWx(mActivity, title, ordertype, tomemid, total_price, price_tbb, paytype, zfpass);
                    }
                    if (cb_alipay_pay.isChecked()) {
                        paytype = "2";
                        if (task == null) {
                            task = new PayTask();
                        }
                        task.Pay(mActivity, title, ordertype, tomemid, total_price, price_tbb, paytype, zfpass);
                    }
                }
                break;
        }
    }

    private boolean checkData() {
        if (!cb_alipay_pay.isChecked() && !cb_wx_pay.isChecked()) {
            Toast.makeText(mActivity, "请选择支付方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
