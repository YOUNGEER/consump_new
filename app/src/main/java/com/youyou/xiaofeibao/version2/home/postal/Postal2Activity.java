package com.youyou.xiaofeibao.version2.home.postal;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.Dialog;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.home.shop.bankcrad.AddBankActivity;
import com.youyou.xiaofeibao.version2.home.shop.bankcrad.BankCardActivity;
import com.youyou.xiaofeibao.version2.pay.pwd.GridPasswordView;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.trans.TransRequestObject;
import com.youyou.xiaofeibao.version2.request.trans.TransRequestParam;
import com.youyou.xiaofeibao.version2.response.mybank.MybankResponseObjecet;
import com.youyou.xiaofeibao.version2.response.mybank.MybankResponselist;
import com.youyou.xiaofeibao.version2.tool.MD5;
import com.youyou.xiaofeibao.version2.tool.Progress;

import java.util.List;

/**
 * 作者：young on 2016/12/27 14:38
 */

public class Postal2Activity extends BaseTitleActivity {
    @ViewInject(R.id.rl_bank)
    RelativeLayout rl_bank;
    @ViewInject(R.id.tv_bankname)
    TextView tv_bankname;
    @ViewInject(R.id.tv_bankdes)
    TextView tv_bankdes;

    @ViewInject(R.id.et_money)
    EditText et_money;
    @ViewInject(R.id.tv_sure)
    TextView tv_sure;
    @ViewInject(R.id.tv_hint)
    TextView tv_hint;
    @ViewInject(R.id.tv_addall)
    TextView tv_addall;

    private MybankResponselist mData;
    private Dialog dialog;
    private String trade_type;

    private String money = "";
    private Double money_dounle;

    private PopupWindow pwdPop;
    private GridPasswordView pwdview;
    private View mParent;
    private TextView mBack;

    @Override
    protected int getTitleText() {
        return R.string.postal2;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_postal2;
    }

    @Override
    protected void initData() {
        super.initData();
        money = getIntent().getAction();
        getBanklist();

        if (money == null || "".equals(money)) {
            money = "0";
        }

        trade_type = getIntent().getStringExtra("type");

        tv_hint.setText("可提现余额" + money + "元");
        mParent=(ScrollView) getLayoutInflater().inflate(R.layout.v2_activity_postal2,null);
        LinearLayout linearLayout= (LinearLayout) getLayoutInflater().inflate(R.layout.pop_pwd,null);
        pwdview= (GridPasswordView) linearLayout.findViewById(R.id.pwd_view);
        mBack= (TextView) linearLayout.findViewById(R.id.tv_back);

        pwdPop=new PopupWindow(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        pwdPop.setTouchable(true);
        pwdPop.setOutsideTouchable(true);
        pwdPop.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        pwdPop.setAnimationStyle(R.style.popupAnimation);

        mBack.setOnClickListener(v -> {
            if (null != pwdPop && pwdPop.isShowing()) {
                pwdPop.dismiss();
                pwdview.clearPassword();
            }
        });

        /**
         * 监听消失
         * */
        pwdPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(null!=pwdview){
                    pwdview.clearPassword();
                }
            }
        });

        pwdview.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
            }
            @Override
            public void onInputFinish(String psw) {
                tiXian(pwdview.getPassWord());
                pwdview.clearPassword();
                if(pwdPop.isShowing()){
                    pwdPop.dismiss();
                }
            }
        });

    }

    @Override
    protected void setListener() {
        super.setListener();
        rl_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, BankCardActivity.class);
                intent.setAction("2");
                startActivityForResult(intent, 2);
            }
        });

        tv_addall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_money.setText(money);
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    pwdPop.showAtLocation(mParent, Gravity.BOTTOM,0,0);

                }
            }
        });



        /**监听
         * 输入的金额是否合法
         * */
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String str = s.toString().trim();
                if (str.startsWith("0") && str.length() == 2 && !str.subSequence(1, 2).equals(".")) {
                    et_money.setText(str.subSequence(1, 2) + ".00");
                    et_money.setSelection(et_money.getText().toString().trim().length());
                }
                if (str.startsWith("0") && str.length() > 2 && !str.subSequence(1, 2).equals(".")) {
                    et_money.setText(str.subSequence(1, str.length()));
                    et_money.setSelection(et_money.getText().toString().trim().length());
                }
                if (!str.startsWith(".") && str.length() > 4 && str.contains(".")) {
                    String[] strings = str.split("\\.");
                    if (strings.length == 2 && strings[1].length() > 2) {
                        strings[1] = strings[1].subSequence(0, 2).toString();
                        et_money.setText(strings[0] + "." + strings[1]);
                        et_money.setSelection(et_money.getText().toString().trim().length());
                    }
                }

                if (str.length() > 8) {
                    Toast.makeText(mActivity, "不能超过八位数", Toast.LENGTH_SHORT).show();
                    et_money.setText(str.substring(0, str.length() - 1));
                    et_money.setSelection(et_money.getText().toString().trim().length());
                    return;
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data && null != data.getExtras() && requestCode == 2 && resultCode == 2) {
            Bundle bundle = data.getExtras();
            mData = (MybankResponselist) bundle.get("data");
            tv_bankname.setText(mData.getBankname());
            String num = mData.getBankno();
            if (null != num && !"".equals(num) && num.length() > 4) {
                String sub = num.subSequence(num.length() - 4, num.length()).toString();
                tv_bankdes.setText("尾号 " + sub + " 银行卡");
            }
        }

        if (null != data && null != data.getExtras() && requestCode == 1 && resultCode == 1) {
            getBanklist();
        }


    }

    private boolean checkData() {
        if (null == mData) {
            Toast.makeText(mActivity, "填写银行卡", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_money.getText().toString().trim())) {
            Toast.makeText(mActivity, "输入提现金额", Toast.LENGTH_SHORT).show();
            return false;
        }
        Double dd = 0.0;
        try {
            dd = Double.parseDouble(et_money.getText().toString().trim());
        } catch (Exception e) {
            Toast.makeText(mActivity, "请输入正确的金额格式", Toast.LENGTH_SHORT).show();
            return false;
        }
        money_dounle = Double.parseDouble(money);

        if(dd<1.0){
            Toast.makeText(mActivity, "无法金额不能低于一元", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dd > money_dounle) {
            Toast.makeText(mActivity, "无法提现超过余额现金", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getBanklist() {

        ResponseBuilder<EmptyRequestObject, MybankResponseObjecet> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.MYBANK,MybankResponseObjecet.class);
        builder.setCallBack(new BaseNetCallBack<MybankResponseObjecet>() {
            @Override
            public void onSuccess(MybankResponseObjecet object) {
                List<MybankResponselist> bkList = object.getData().getBkList();
                if (bkList.size() == 0) {
                    Dialog dialog = new Dialog(mActivity);
                    dialog.setTitle("温馨提示：");
                    dialog.setMessage("暂未绑定银行卡，立即绑定？");
                    dialog.setConfirmText("确定绑定");
                    dialog.setConfirmClick(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(mActivity, AddBankActivity.class);
                            intent.setAction("1");
                            startActivityForResult(intent, 1);
                        }
                    });
                    dialog.show();
                } else {
                    mData = bkList.get(0);
                    tv_bankname.setText(mData.getBankname());
                    String num = mData.getBankno();
                    if (null != num && !"".equals(num) && num.length() > 4) {
                        String sub = num.subSequence(num.length() - 4, num.length()).toString();
                        tv_bankdes.setText("尾号 " + sub + " 银行卡");
                    }
                }
            }
        }).send();
    }

    private void tiXian(String pwd) {

        final android.app.Dialog dialog= Progress.createLoadingDialog(mActivity,"");
        dialog.show();
        TransRequestObject requstObject = new TransRequestObject();
        TransRequestParam param = new TransRequestParam();
        param.setTotal_fee(et_money.getText().toString().trim());
        param.setBankid(mData.getId() + "");
        param.setTrade_type(trade_type);
        param.setZfpass(MD5.getCode(pwd));
        requstObject.setParam(param);

        ResponseBuilder<TransRequestObject, MybankResponseObjecet> builder = new ResponseBuilder<>(requstObject, Config.WYKTRANFER,MybankResponseObjecet.class);
        builder.setCallBack(new BaseNetCallBack<MybankResponseObjecet>() {
            @Override
            public void onSuccess(MybankResponseObjecet object) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(mActivity, object.getMsg(), Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();

            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(MybankResponseObjecet object) {
                super.onFailure(object);
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(mActivity, object.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }).send();

    }


}
