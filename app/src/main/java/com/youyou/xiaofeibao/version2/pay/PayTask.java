package com.youyou.xiaofeibao.version2.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseResp;
import com.youyou.xiaofeibao.common.GlobalConfig;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.request.pay.PayRequestObject;
import com.youyou.xiaofeibao.version2.request.pay.PayRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObjectPay;
import com.youyou.xiaofeibao.version2.response.pay.PayResponseObject;
import com.youyou.xiaofeibao.version2.tool.MD5;
import com.youyou.xiaofeibao.wxapi.WXPayClickListener;
import com.youyou.xiaofeibao.wxapi.Wechatpay;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：young on 2016/12/21 10:42
 */

public class PayTask {

    private static final int SDK_PAY_FLAG = 11;
    private static final int SDK_AUTH_FLAG=13;
    private static final int SDK_CHECK_FLAG = 12;
    private static final int MSG_GET_ACTIVATION = 2;

    private Activity mActivity;

    public void Pay(final Activity mActivity, String title, String ordertype, String tomemid, String price
            , String price_tbb, final String paytype, String zfpass) {
        this.mActivity = mActivity;
        zfpass= MD5.getCode(zfpass);
        PayRequestObject requestObject = new PayRequestObject();
        PayRequestParam param = new PayRequestParam();
        param.setTradetype("APP");//用户名
        param.setTitle(title);
        param.setTomemid(tomemid);
        param.setPrice(price);
        param.setPrice_tbb(price_tbb + "");
        param.setOrdertype(ordertype);//0，商家与个人支付订单，1：个人与个人转账。2：购买收益权
        param.setPaytype(paytype);//1:微信支付，2：支付宝支付，3：智惠币支付
        param.setZfpass(zfpass);

        Log.i("wwwwww", tomemid + "       " + paytype);

        Map<String, String> map = new HashMap<>();
        map.put("tradetype", "APP");
        map.put("title", title);
        map.put("ordertype", ordertype);
        map.put("tomemid", tomemid);
        map.put("price", price);
        map.put("price_tbb", price_tbb);
        map.put("paytype", paytype);
        map.put("zfpass", zfpass);

        param.setSign(PayUtils.getXfbSign(map));
        requestObject.setParam(param);

        ResponseBuilder<PayRequestObject, BaseResponseObjectPay> builder = new ResponseBuilder<>(requestObject, Config.GETZFBORDER,BaseResponseObjectPay.class);

        builder.setCallBack(new NetCallBack<BaseResponseObjectPay>() {
            @Override
            public void onSuccess(BaseResponseObjectPay reponse) {
                Log.i("wechat_pay", "onSuccess" + reponse.getData());
                if (paytype.equals("3")) {
                    Toast.makeText(mActivity, "支付成功！", Toast.LENGTH_SHORT).show();
                    mActivity.startActivity(new Intent(mActivity, PaySuccessActivity.class));

                } else if (paytype.equals("2")) {
                    payTypeForData(reponse.getData());//其他类型订单
                }
            }

            @Override
            public void onNetFailure(String str) {
                Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onFalie2();
                }
            }

            @Override
            public void onServerFailure(String str) {
                Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onFalie2();
                }
            }

            @Override
            public void onFailure(BaseResponseObjectPay onFailure) {

                if (listener != null) {
                    listener.onFalie2();
                }

                Toast.makeText(mActivity, onFailure.getMsg(), Toast.LENGTH_SHORT).show();
                if (onFailure.getCode().equals("-1")) {
                    mActivity.startActivity(new Intent(mActivity, LoginOrRegisterActivity.class));
                }
            }
        }).send();
    }

    public void PayWx(final Activity mActivity, String title, String ordertype, String tomemid, String price
            , String price_tbb, final String paytype, String zfpass) {
        this.mActivity = mActivity;
        zfpass= MD5.getCode(zfpass);
        PayRequestObject requestObject = new PayRequestObject();
        PayRequestParam param = new PayRequestParam();
        param.setTradetype("APP");//用户名
        param.setTitle(title);
        param.setTomemid(tomemid);
        param.setPrice(price);
        param.setPrice_tbb(price_tbb + "");
        param.setOrdertype(ordertype);//0，商家与个人支付订单，1：个人与个人转账。2：购买收益权
        param.setPaytype(paytype);//1:微信支付，2：支付宝支付，3：智惠币支付
        param.setZfpass(zfpass);

        Map<String, String> map = new HashMap<>();
        map.put("tradetype", "APP");
        map.put("title", title);
        map.put("ordertype", ordertype);
        map.put("tomemid", tomemid);
        map.put("price", price);
        map.put("price_tbb", price_tbb);
        map.put("paytype", paytype);
        map.put("zfpass", zfpass);

        param.setSign(PayUtils.getXfbSign(map));
        requestObject.setParam(param);

        ResponseBuilder<PayRequestObject, PayResponseObject> builder = new ResponseBuilder<>(requestObject, Config.GETZFBORDER,PayResponseObject.class);

        builder.setCallBack(new NetCallBack<PayResponseObject>() {
            @Override
            public void onSuccess(PayResponseObject reponse) {
                Wechatpay.WechatPay(GlobalConfig.getAppContext(), reponse.getData());
            }

            @Override
            public void onNetFailure(String str) {
                Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onFalie2();
                }
            }

            @Override
            public void onServerFailure(String str) {
                Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onFalie2();
                }
            }

            @Override
            public void onFailure(PayResponseObject onFailure) {

                if (listener != null) {
                    listener.onFalie2();
                }

                if (onFailure.getCode().equals("-1")) {
                    mActivity.startActivity(new Intent(mActivity, LoginOrRegisterActivity.class));
                }
            }
        }).send();
        method1(mActivity);
    }


    private void method1(final Activity mActivity) {
        Log.i("wechat_pay", "支付回调");
        Wechatpay.setOnRespListener(new WXPayClickListener() {
            @Override
            public void onResp(BaseResp resp) {
                Log.i("wechat_pay", "onResp" + resp.errStr);
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        Toast.makeText(mActivity, "支付成功！", Toast.LENGTH_SHORT).show();
                        mActivity.startActivity(new Intent(mActivity, PaySuccessActivity.class));
                        if (listener != null) {
                            listener.successBenefit2();
                        }
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        // msg = "用户取消了支付";
                        if (listener != null) {
                            listener.onFalie2();
                        }
                        Toast.makeText(mActivity, "用户取消了支付！", Toast.LENGTH_SHORT).show();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    case BaseResp.ErrCode.ERR_COMM:
                    case BaseResp.ErrCode.ERR_SENT_FAILED:
                        // msg = "支付失败！";
                        Toast.makeText(mActivity, "支付失败！", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onFalie2();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private Handler mHandler = new Handler() {//支付结果的返回处理
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("sssssss123",msg.obj.toString());
            switch (msg.what) {
                case MSG_GET_ACTIVATION:
                    if (listener != null) {
                        listener.onFalie2();
                    }

                    break;
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.i("sssssss123","SDK_PAY_FLAG");
//                    AliPayResult payResult = new AliPayResult((String) msg.obj);
//                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.i("sssssss123","SDK_PAY_FLAG9000");
//                        onResult(AlipayResultItem.RESULT_SUCCESS);
                        if (listener != null) {
                            listener.successBenefit2();
                        }
                        mActivity.startActivity(new Intent(mActivity, PaySuccessActivity.class));

                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
//						Toast.makeText(PayDemoActivity.this, "支付结果确认中",
//								Toast.LENGTH_SHORT).show();
                        } else {
                            if (listener != null) {
                                listener.onFalie2();
                            }
                        }
                    }
                    break;
                }

                case SDK_CHECK_FLAG: {

                    if (listener != null) {
                        listener.onFalie2();
                    }
                    break;
                }
            }
        }
    };

    private void payTypeForData(final String payInfo) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                com.alipay.sdk.app.PayTask alipay = new com.alipay.sdk.app.PayTask(mActivity);
                // 调用支付接口，获取支付结果
                if (TextUtils.isEmpty(alipay.getVersion())) {
                    return;
                }
                try {
//                    String result = alipay.payV2(payInfo,true);
//                    Message msg = new Message();
//                    msg.what = SDK_PAY_FLAG;
//                    msg.obj = result;
//                    mHandler.sendMessage(msg);

                    Map<String, String> result = alipay.payV2(payInfo, true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);

                } catch (Exception e) {
                    return;
                }
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    private PayListener listener;

    public PayListener getListener() {
        return listener;
    }

    public void setListener(PayListener listener) {
        this.listener = listener;
    }


    public interface PayListener {

        void successBenefit2();

        void onFalie2();

    }

}
