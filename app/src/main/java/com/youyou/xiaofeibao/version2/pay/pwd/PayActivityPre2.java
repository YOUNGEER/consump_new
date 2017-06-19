package com.youyou.xiaofeibao.version2.pay.pwd;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.Dialog;
import com.youyou.xiaofeibao.framework.activity.KeyBoardAutoDownActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.net.SimpleNetCallBack;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.pay.PayActivity;
import com.youyou.xiaofeibao.version2.pay.PayTask;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.checkpwd.CheckPwdRequestObject;
import com.youyou.xiaofeibao.version2.request.checkpwd.CheckPwdRequestParam;
import com.youyou.xiaofeibao.version2.request.shopinfo.ShopInfoRequestObject;
import com.youyou.xiaofeibao.version2.request.shopinfo.ShopInfoRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.shopinfo.ShopInfoResponseObject;
import com.youyou.xiaofeibao.version2.response.tong.TongResponseObject;
import com.youyou.xiaofeibao.version2.settings.ChangeTongPwdActivty;
import com.youyou.xiaofeibao.version2.tool.MD5;
import com.youyou.xiaofeibao.version2.tool.MathUtil;

/**
 * 作者：young on 2016/12/20 15:10
 */

public class PayActivityPre2 extends KeyBoardAutoDownActivity {
    @ViewInject(R.id.et_total)
    EditText et_total_price;
    @ViewInject(R.id.cb_isTong)
    CheckBox cb_isTong;
    @ViewInject(R.id.tv_tong_need)
    TextView tv_pay_tong;
    @ViewInject(R.id.tv_tong)
    TextView tv_total_tong;
    @ViewInject(R.id.tv_need_pay)
    TextView tv_real_pay;
    @ViewInject(R.id.tv_back_tong)
    TextView tv_back_tong;
    @ViewInject(R.id.tv_sure)
    TextView tv_sure;
    @ViewInject(R.id.ll_isTong)
    LinearLayout ll_isTong;

    private Double d_total_tong;//总的通宝币数量
    private Double d_pay_tong;//需要支付的通宝币数量
    private Double d_subNum_tong = 0.00;//支付的金额和通宝币的差值
    private Double d_real_pay;//实际需要付的金钱
    private boolean isTrans = false;
    private String shopReturnRate="0.0";
    private boolean isError=true;//通宝币密码是否正确

    private String title = "支付订单";
    private String ordertype = "0";
    private String tomemid;
    private String paytype = "3";//1:微信支付，2：支付宝支付，3：智惠币支付
    private String zfpass = "";

    private PopupWindow pwdPop;
    private GridPasswordView pwdview;
    private View mParent;
    private TextView mBack;

    @Override
    protected int getTitleText() {
        return R.string.order_pay;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_pay2;
    }

    @Override
    protected void initData() {
        super.initData();

        tv_sure.setActivated(false);
        tomemid = getIntent().getAction();

        //扫一扫跳过来的
        if(false==getIntent().getBooleanExtra("sub",false)){
            Log.i("sssssssssss","111111111111111"+shopReturnRate+"222222222222");
            getMemidByUrl();
        }else{
            Log.i("sssssssssss","2222222222222");
            shopReturnRate=getIntent().getStringExtra("return");
            setTitle(getIntent().getStringExtra("name"));
        }

        getTongNum();   //获取通宝币数量，为0时gone，并且判断是否设置了支付密码

        mParent=(ScrollView) getLayoutInflater().inflate(R.layout.v2_activity_pay2,null);
        LinearLayout linearLayout= (LinearLayout) getLayoutInflater().inflate(R.layout.pop_pwd,null);
        pwdview= (GridPasswordView) linearLayout.findViewById(R.id.pwd_view);
        mBack= (TextView) linearLayout.findViewById(R.id.tv_back);

        pwdPop=new PopupWindow(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        pwdPop.setTouchable(true);
        pwdPop.setOutsideTouchable(true);
        pwdPop.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        pwdPop.setAnimationStyle(R.style.popupAnimation);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=pwdPop&&pwdPop.isShowing()){
                    pwdPop.dismiss();
                    pwdview.clearPassword();
                }
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

        inidEtFormat();//监听动态计算各数值的显示

        try {
            d_total_tong = Double.parseDouble(PreferencesConfig.v2_allmoney.get());
        } catch (Exception e) {
            d_total_tong = 0.00;
        }

        tv_total_tong.setText("(可用余额" + d_total_tong + ")");

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_sure.isActivated()) {//可点击状态
                    if (isTrans) {//需要第三方支付
                        if (d_pay_tong > 0) {//通宝币和第三方
                            showPop();
                        } else {//只需要第三方支付
                            Intent intent = new Intent(mActivity, PayActivity.class);
                            intent.setAction(getIntent().getAction());
                            intent.putExtra("pwd", zfpass);
                            intent.putExtra("price", d_real_pay+"");
                            intent.putExtra("total_price",et_total_price.getText().toString().trim());
                            intent.putExtra("price_tbb", d_pay_tong+"");

                            startActivity(intent);
                        }
                    } else {//只需要智惠币支付
                        showPop();
                    }
                }
            }
        });

        cb_isTong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                caculateMoney();
            }
        });
    }

    private void showPop() {
        pwdPop.showAtLocation(mParent, Gravity.BOTTOM,0,0);

        pwdview.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {

                zfpass = pwdview.getPassWord();

                checkTongPwd(pwdview.getPassWord(),pwdview);
            }
        });
    }

    private void onPutfinish() {

        if(!isError){
            Toast.makeText(mActivity,"您的支付密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
            pwdview.clearPassword();
            return;
        }

        if (null != zfpass && !"".equals(zfpass) && zfpass.length() == 6) {
            if (d_real_pay> 0) {
                Intent intent = new Intent(mActivity, PayActivity.class);
                intent.setAction(getIntent().getAction());
                intent.putExtra("pwd", zfpass);
                intent.putExtra("price", d_real_pay+"");
                intent.putExtra("total_price",et_total_price.getText().toString().trim());
                intent.putExtra("price_tbb", d_pay_tong+"");
                startActivity(intent);
            } else {
                new PayTask().Pay(mActivity, title, ordertype, tomemid, et_total_price.getText().toString().trim(), d_pay_tong+"", paytype,zfpass);
            }
            zfpass = "";
        }
        pwdview.clearPassword();
        if(pwdPop.isShowing()){
            pwdPop.dismiss();
        }
    }

    /**
     * 检查通宝币密码
     * @param pwd
     * @param pwdview
     */
    private void checkTongPwd(String pwd, final GridPasswordView pwdview) {

        Log.i("ssssssfffff",getIntent().getAction()+"    "+MD5.getCode(getIntent().getAction()));

        CheckPwdRequestObject request=new CheckPwdRequestObject();
        CheckPwdRequestParam param=new CheckPwdRequestParam();
        param.setZfpass( MD5.getCode(zfpass));
        request.setParam(param);
        ResponseBuilder<CheckPwdRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(request, Config.CHECKPWD,BaseResponseObject.class);
        builder.setCallBack(new NetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject reponse) {
                isError=true;
                onPutfinish();
            }

            @Override
            public void onNetFailure(String str) {
                isError=false;
                onPutfinish();
            }

            @Override
            public void onServerFailure(String str) {
                pwdview.clearPassword();
                isError=false;
                onPutfinish();
            }

            @Override
            public void onFailure(BaseResponseObject baseResponseObject) {

                isError=false;
                onPutfinish();
            }
        }).send();

    }

    private void getMemidByUrl() {

        //获取商户的信息
            ShopInfoRequestObject responseObject = new ShopInfoRequestObject();
            ShopInfoRequestParam param = new ShopInfoRequestParam();
            param.setMemid(tomemid);
            responseObject.setParam(param);
            ResponseBuilder<ShopInfoRequestObject, ShopInfoResponseObject> builder = new ResponseBuilder<>(responseObject, Config.SHOPINFO,ShopInfoResponseObject.class);

            builder.setCallBack(new SimpleNetCallBack<ShopInfoResponseObject>() {
                @Override
                public void onNetFail() {

                }

                @Override
                public void onSuccess(ShopInfoResponseObject reponse) {
                  shopReturnRate=reponse.getData().getShop().getShopReturnRate();
                    setTitle(reponse.getData().getShop().getShopName());
                }
            }).send();
    }

    private void inidEtFormat() {
        /**监听
         * 输入的金额是否合法
         * */
        et_total_price.addTextChangedListener(new TextWatcher() {
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
                    et_total_price.setText(str.subSequence(1, 2) + ".00");
                    et_total_price.setSelection(et_total_price.getText().toString().trim().length());
                }
                if (str.startsWith("0") && str.length() > 2 && !str.subSequence(1, 2).equals(".")) {
                    et_total_price.setText(str.subSequence(1, str.length()));
                    et_total_price.setSelection(et_total_price.getText().toString().trim().length());
                }
                if (!str.startsWith(".") && str.length() > 4 && str.contains(".")) {
                    String[] strings = str.split("\\.");
                    if (strings.length == 2 && strings[1].length() > 2) {
                        strings[1] = strings[1].subSequence(0, 2).toString();
                        et_total_price.setText(strings[0] + "." + strings[1]);
                        et_total_price.setSelection(et_total_price.getText().toString().trim().length());
                    }
                }

                if (str.length() > 8) {
                    Toast.makeText(mActivity, "不能超过八位数", Toast.LENGTH_SHORT).show();
                    et_total_price.setText(str.substring(0, str.length() - 1));
                    et_total_price.setSelection(et_total_price.getText().toString().trim().length());
                    return;
                }
                caculateMoney();
            }
        });

    }

    /**
    *动态计算各数值的显示
     */
    private void caculateMoney() {
        Double total_price;//输入的金额
        String f4="";//返币的数量
        Double percent=0.0;
        try {
            percent= Double.parseDouble(shopReturnRate);
        }catch (Exception e){
            tv_back_tong.setText("返币数据异常");
            percent=0.0;
        }

        try {
            total_price = Double.parseDouble(et_total_price.getText().toString().trim());
        } catch (Exception e) {
            total_price = 0.00;
        }

        if (cb_isTong.isChecked()) {//计算通宝币之间的差
            d_subNum_tong = MathUtil.subtractDouble(total_price, d_total_tong);//计算输入的金额和通宝币之间的差值
            tv_pay_tong.setTextColor(Color.BLACK);
            if (d_subNum_tong > 0) {//输入金额大于通宝币数量,需要跳转第三方支付

                d_pay_tong = d_total_tong;
                d_real_pay=d_subNum_tong;
                isTrans = true;

                tv_pay_tong.setText(d_total_tong + "");
                tv_real_pay.setText(d_real_pay+"");
                tv_sure.setText(d_real_pay + " 确认支付");
                f4 = MathUtil.formatDouble(d_subNum_tong * percent, 2);
                tv_back_tong.setText(f4);


            } else {                    //通宝币支付，不需要第三方支付
                d_pay_tong = total_price;
                d_real_pay=0.00;
                isTrans = false;

                tv_pay_tong.setText(d_pay_tong + "");
                tv_real_pay.setText(d_real_pay+"");
                tv_back_tong.setText("0.0");
                tv_sure.setText("智惠币" + d_pay_tong + " 确认支付");
            }

        } else {//不要带入通宝币进行计算

            d_pay_tong=0.0;
            d_real_pay = total_price;
            isTrans = true;

            tv_pay_tong.setTextColor(Color.WHITE);
            tv_pay_tong.setText(d_pay_tong + "");
            tv_real_pay.setText(d_real_pay+"");
            f4 = MathUtil.formatDouble(d_real_pay * percent, 2);
            tv_back_tong.setText(f4);
            tv_sure.setText(d_real_pay + " 确认支付");

        }

        if (total_price == 0) {
            tv_sure.setActivated(false);
        } else {
            tv_sure.setActivated(true);
        }

    }

    /**
     * 获取通宝币数量
     */
    private void getTongNum() {
        ResponseBuilder<EmptyRequestObject, TongResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.TONGNUM,TongResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<TongResponseObject>() {
            @Override
            public void onSuccess(TongResponseObject Object) {
                PreferencesConfig.v2_allmoney.set(Object.getData().getGoldNum());
                try {
                    d_total_tong = Double.parseDouble(PreferencesConfig.v2_allmoney.get());
                } catch (Exception e) {
                    d_total_tong = 0.00;
                }
                tv_total_tong.setText("(可用余额" + d_total_tong + ")");

                Double tong = 0.0;
                try {
                    tong = Double.parseDouble(Object.getData().getGoldNum());
                } catch (Exception e) {
                    tong = 0.00;
                }

                if (tong == 0) {
                    ll_isTong.setVisibility(View.GONE);
                    cb_isTong.setChecked(false);
                } else {
                    ll_isTong.setVisibility(View.VISIBLE);
                    cb_isTong.setChecked(true);
                }

                if ("0".equals(Object.getData().getHasPayPwd())) {
                    showDialog();
                }
            }
        }).send();
    }

    private void showDialog() {
        Dialog dialog = new Dialog(mActivity);
        dialog.setTitle("温馨提示");
        dialog.setMessage("您还未设置智惠币支付密码，请在 我->设置->支付密码设置 中进行操作，或者直接点确定按钮");
        dialog.setConfirmText("确定");
        dialog.setConfirmClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(mActivity, ChangeTongPwdActivty.class));
            }
        });
        dialog.show();
    }

}
