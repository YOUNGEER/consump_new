package com.youyou.xiaofeibao.wxapi;

import android.app.Dialog;
import android.content.Context;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youyou.xiaofeibao.util.ProgressUtils;
import com.youyou.xiaofeibao.version2.Config;

/**
 * 作者：young on 2017/2/27 16:33
 */


public class WechatLogin {
    private  Dialog mProgressUtils=null;

    public static WXPayClickListener mListener = null;
    private static IWXAPI wechatapi;
    private  Context mContext;

    public WechatLogin(Context context) {
        mContext=context;
        getInstrance(context);
    }

    public  IWXAPI getInstrance(Context context) {

        if (wechatapi == null) {
            wechatapi = WXAPIFactory.createWXAPI(context, Config.WX_SHARE);
            wechatapi.registerApp(Config.WX_SHARE);
        }
        return wechatapi;
    }

    public  void bindwx(){

        final SendAuth.Req req=new SendAuth.Req();
        req.scope="snsapi_userinfo";
        req.state="wechat_sdk_demo";
        wechatapi.sendReq(req);
    }

    /**
     * @return
     * @功能描述:获取是否安装微信
     * @返回类型:boolean:如果安装则返回true，没安装返回false！
     */
    //INFO 微信/判断是否安装微信
    public  boolean getOpenWXApp(Context context) {
        boolean installWX = getInstrance(context).openWXApp();
        return installWX;
    }
}
