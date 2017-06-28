package com.youyou.xiaofeibao.version2.pay;

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
import com.youyou.xiaofeibao.version2.pay.pwd.GridPasswordView;
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

public class PayActivityPre extends KeyBoardAutoDownActivity {
    @ViewInject(R.id.et_total)
    EditText et_total;
    @ViewInject(R.id.cb_isTong)
    CheckBox cb_isTong;
    @ViewInject(R.id.tv_tong_need)
    TextView tv_tong_need;//实时显示通宝币的数量
    @ViewInject(R.id.tv_tong)
    TextView tv_tong;
    @ViewInject(R.id.tv_need_pay)
    TextView tv_need_pay;
    @ViewInject(R.id.tv_back_tong)
    TextView tv_back_tong;
    @ViewInject(R.id.tv_sure)
    TextView tv_sure;
    @ViewInject(R.id.ll_isTong)
    LinearLayout ll_isTong;

    private Double total_tong;
    private Double pay_tong;
    private Double subNum = 0.00;
    private boolean isTran = false;
    private String shopReturnRate="0.0";

    private String title = "支付订单";
    private String ordertype = "0";
    private String tomemid;
    private Double price;//金额
    private String price_tbb = "0";//通宝币
    private String paytype = "3";//1:微信支付，2：支付宝支付，3：智惠币支付
    private String zfpass = "";

    private PopupWindow pwdPop;
    private GridPasswordView pwdview;
    private View mParent;
    private TextView mBack;

    private boolean isError=true;//标识支付密码是否正确

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

            getMemidByUrl();
        }else{

            shopReturnRate=getIntent().getStringExtra("return");
            setTitle(getIntent().getStringExtra("name"));
        }

        getTongNum();

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

        inidEtFormat();

        try {
            total_tong = Double.parseDouble(PreferencesConfig.v2_allmoney.get());
        } catch (Exception e) {
            total_tong = 0.00;
        }

        tv_tong.setText("(可用余额" + total_tong + ")");

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.i("sds","sds");


                if (tv_sure.isActivated()) {//可点击状态
                    if (isTran) {//需要第三方支付
                        if (cb_isTong.isChecked() && pay_tong > 0) {
                            showPop();
                        } else {
                            Intent intent = new Intent(mActivity, PayActivity.class);
                            intent.setAction(getIntent().getAction());
                            intent.putExtra("num", subNum + "");
                            intent.putExtra("pwd", zfpass);
                            intent.putExtra("price", price);
                            if(null==price_tbb){
                                price_tbb="0";
                            }
                            intent.putExtra("price_tbb", price_tbb);
                            Log.i("vvvvvvvv11",tomemid+"   "+subNum+"     "+price+"       "+zfpass+"        "+price_tbb+"      ");
                            startActivityForResult(intent, 1);
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

    /**
     * 弹出支付密码的页面
     */
    private void showPop() {
        pwdPop.showAtLocation(mParent, Gravity.BOTTOM,0,0);

        pwdview.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {

               checkTongPwd(pwdview.getPassWord(),pwdview);
                if(!isError){
                    return;
                }

                zfpass = pwdview.getPassWord();
                if (null != zfpass && !"".equals(zfpass) && zfpass.length() == 6) {
                    if (isTran && cb_isTong.isChecked() && pay_tong > 0) {
                        Intent intent = new Intent(mActivity, PayActivity.class);
                        intent.setAction(getIntent().getAction());
                        intent.putExtra("num", subNum + "");
                        intent.putExtra("pwd", zfpass);
                        intent.putExtra("price", price);
                        if(null==price_tbb){
                            price_tbb="0";
                        }
                        intent.putExtra("price_tbb", price_tbb);
//                        Log.i("vvvvvvvv11",tomemid+"   "+subNum+"     "+price+"       "+zfpass+"        "+price_tbb+"      ");
                        startActivityForResult(intent, 1);
                    } else {
//                        Log.i("vvvvvvvv33",tomemid+"   "+subNum+"     "+price+"       "+zfpass+"        "+price_tbb+"      ");
                        new PayTask().Pay(mActivity, title, ordertype, tomemid, price + "", price_tbb, paytype,zfpass);
                    }
                    zfpass = "";
                }
                pwdview.clearPassword();
                if(pwdPop.isShowing()){
                    pwdPop.dismiss();
                }
            }
        });
    }

    /**
     * 验证支付密码是否正确
     * @param pwd
     * @param pwdview
     */
    private void checkTongPwd(String pwd, final GridPasswordView pwdview) {
        CheckPwdRequestObject request=new CheckPwdRequestObject();
        CheckPwdRequestParam param=new CheckPwdRequestParam();
        param.setZfpass( MD5.getCode(zfpass));
        request.setParam(param);
        ResponseBuilder<CheckPwdRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(request, Config.CHECKPWD,BaseResponseObject.class);
        builder.setCallBack(new NetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject reponse) {
                isError=true;
            }

            @Override
            public void onNetFailure(String str) {
                pwdview.clearPassword();
                isError=false;
            }

            @Override
            public void onServerFailure(String str) {
                pwdview.clearPassword();
                isError=false;
            }

            @Override
            public void onFailure(BaseResponseObject baseResponseObject) {
                Toast.makeText(mActivity,"您的支付密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
                pwdview.clearPassword();
                isError=false;
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
        et_total.addTextChangedListener(new TextWatcher() {
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
                    et_total.setText(str.subSequence(1, 2) + ".00");
                    et_total.setSelection(et_total.getText().toString().trim().length());
                }
                if (str.startsWith("0") && str.length() > 2 && !str.subSequence(1, 2).equals(".")) {
                    et_total.setText(str.subSequence(1, str.length()));
                    et_total.setSelection(et_total.getText().toString().trim().length());
                }
                if (!str.startsWith(".") && str.length() > 4 && str.contains(".")) {
                    String[] strings = str.split("\\.");
                    if (strings.length == 2 && strings[1].length() > 2) {
                        strings[1] = strings[1].subSequence(0, 2).toString();
                        et_total.setText(strings[0] + "." + strings[1]);
                        et_total.setSelection(et_total.getText().toString().trim().length());
                    }
                }

                if (str.length() > 8) {
                    Toast.makeText(mActivity, "不能超过八位数", Toast.LENGTH_SHORT).show();
                    et_total.setText(str.substring(0, str.length() - 1));
                    et_total.setSelection(et_total.getText().toString().trim().length());
                    return;
                }
                caculateMoney();
            }
        });

    }

    private void caculateMoney() {

        try {
            price = Double.parseDouble(et_total.getText().toString().trim());
        } catch (Exception e) {
            price = 0.00;
        }

        if (cb_isTong.isChecked()) {

            subNum = MathUtil.subtractDouble(price, total_tong);
            tv_tong_need.setTextColor(Color.BLACK);
            if (subNum >= 0) {
                pay_tong = total_tong;
            } else {
                pay_tong = price;
            }
        } else {

            subNum = price;
            tv_tong_need.setTextColor(Color.WHITE);
        }
        if(subNum==0){
        }else if (subNum <0) {
            isTran = false;
            subNum = 0.00;
            tv_tong_need.setText(price + "");
            tv_sure.setText("智惠币" + price + " 确认支付");
        } else {
            isTran = true;
            tv_tong_need.setText(total_tong + "");
            tv_sure.setText(subNum + " 确认支付");
        }

        String f4="";
        try {
            Double percent= Double.parseDouble(shopReturnRate);
            f4 = MathUtil.formatDouble2Num(MathUtil.multiplyDouble(subNum, percent)) + "";
        }catch (Exception e){
            Toast.makeText(mActivity,"数据异常", Toast.LENGTH_SHORT).show();

        }

        tv_need_pay.setText(subNum + "");
        tv_back_tong.setText(f4);


        if (price == 0) {
            tv_sure.setActivated(false);
        } else {
            tv_sure.setActivated(true);
        }

        if(cb_isTong.isChecked()){
            price_tbb = tv_tong_need.getText().toString().trim();
        }else {
            price_tbb = "0";
        }
    }

    private void getTongNum() {
        ResponseBuilder<EmptyRequestObject, TongResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.TONGNUM,TongResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<TongResponseObject>() {
            @Override
            public void onSuccess(TongResponseObject Object) {
                PreferencesConfig.v2_allmoney.set(Object.getData().getGoldNum());
                try {
                    total_tong = Double.parseDouble(PreferencesConfig.v2_allmoney.get());
                } catch (Exception e) {
                    total_tong = 0.00;
                }
                tv_tong.setText("(可用余额" + total_tong + ")");

                Double tong = 0.0;
                try {
                    tong = Double.parseDouble(Object.getData().getGoldNum());
                } catch (Exception e) {
                    tong = 0.00;
                }

                if (tong == 0) {
                    ll_isTong.setVisibility(View.GONE);
                } else {
                    ll_isTong.setVisibility(View.VISIBLE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            Toast.makeText(mActivity, "支付成功！", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
