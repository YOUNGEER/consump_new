package com.youyou.xiaofeibao.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.response.pay.PayResponseData;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeChatUtil {

    // 微信的版本,如果低于此版本则不支持微信分享
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
    public static WXPayClickListener mListener = null;
    private static IWXAPI wechatapi;

    public static IWXAPI getInstrance(Context context) {
        if (wechatapi == null) {
            wechatapi = WXAPIFactory.createWXAPI(context, Config.WX_SHARE, false);
            wechatapi.registerApp(Config.WX_SHARE);
        }
        return wechatapi;
    }

    public static IWXAPI getInstrance2(Context context) {
        if (wechatapi == null) {
            wechatapi = WXAPIFactory.createWXAPI(context, Config.WX_SHARE);
            wechatapi.registerApp(Config.WX_SHARE);
        }
        return wechatapi;
    }


    /**
     * @param context
     * @return
     * @功能描述:获取微信的版本是否支持分享
     * @返回类型:boolean:如果支持分享则返回true，不支持返回false！
     */
    //INFO 微信/判断微信版本是否支持分享
    public static boolean getVersion(Context context) {
        int wxSdkVersion = getInstrance(context).getWXAppSupportAPI();
        if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
            return true;
        }
        return false;
    }

    public static boolean isWXInstalled(Context context) {
        return getInstrance(context).isWXAppInstalled();
    }

    /**
     * @param context
     * @return
     * @功能描述:获取微信的版本是否支持支付
     * @返回类型:boolean:如果支持支付则返回true，不支持返回false！
     * @时间:
     */
    //INFO 微信/判断微信版本是否支持支付
    public static boolean getPaySupported(Context context) {
        int wxSdkVersion = getInstrance(context).getWXAppSupportAPI();
        if (wxSdkVersion >= Build.PAY_SUPPORTED_SDK_INT) {
            return true;
        }
        return false;
    }

    /**
     * @return
     * @功能描述:获取是否安装微信
     * @返回类型:boolean:如果安装则返回true，没安装返回false！
     */
    //INFO 微信/判断是否安装微信
    public static boolean getOpenWXApp(Context context) {
        boolean installWX = getInstrance(context).openWXApp();

        return installWX;
    }

    /**
     * @param context
     * @return
     * @功能描述:打开微信分享
     * @返回类型:boolean
     */
    public static boolean openWeChat(Context context) {
        return getInstrance(context).openWXApp();
    }

    /**
     * INFO 微信/唤起微信登陆页面
     */
    public static boolean loginWeChat(Context context) {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "mapbar_weixin_login_state";
        return getInstrance(context).sendReq(req);
    }


    public static void shareWx(Context context,int type){
        if (!isWXInstalled(context)) {
            Log.i("wechat_pay", "是否进行注册" + isWXInstalled(context));
            getInstrance(context).registerApp(Config.PAY);
        }
        if(!WeChatUtil.getOpenWXApp(context)){
            Toast.makeText(context,"请安装微信后再分享",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!WeChatUtil.getVersion(context)){
            Toast.makeText(context,"当前微信版本不支持分享，请升级",Toast.LENGTH_SHORT).show();
            return;
        }
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "http://www.xftb168.com/web/toWxRegister?merchantMemId="+ PreferencesConfig.v2_uesrid.get();
        WXMediaMessage message = new WXMediaMessage(webpageObject);
        message.title = "智惠返邀您一起享优惠";
        message.description = "智惠返——扫码支付实时到账，商户提现秒到，万亿市场等您来享！";
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_pic);
        message.thumbData = Util.bmpToByteArray(bitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = message;
        // 分享或收藏的目标场景，通过修改scene场景值实现。
        // 发送到聊天界面 —— WXSceneSession
        // 发送到朋友圈 —— WXSceneTimeline
        // 添加到微信收藏 —— WXSceneFavorite
        req.scene = type;
        getInstrance2(context).sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
