package com.youyou.xiaofeibao.version2.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.youyou.xiaofeibao.framework.Dialog;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.response.appversion.AppResponseData;
import com.youyou.xiaofeibao.version2.response.appversion.AppResponseObject;

/**
 * Created by Administrator on 2016/8/18.
 */
public class MyUpdateDialog {

    public static void checkVersion(final Context context, final int type) {

        ResponseBuilder<EmptyRequestObject, AppResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.APPVERSION,AppResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<AppResponseObject>() {
            @Override
            public void onSuccess(AppResponseObject appResponseObject) {
                final AppResponseData data = appResponseObject.getData();
                if (!"".equals(getAppVersionName(context)) && !data.getApp_version().equals(getAppVersionName(context))) {
                    Dialog dialog = new Dialog(context);
                    dialog.setTitle("检测到新版本");
//               dialog.setMessage(Object.getData().getInfo());
                    dialog.setMessage("是否进行版本更新？");
                    dialog.setTitle("立即更新");
                    dialog.setConfirmClick(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (!isWifiConnected(context)) {
                                showIsWifiDialog(context, data.getNew_apk());
                            } else {
                                Intent intent = new Intent(context.getApplicationContext(), ApkDownloadService.class);
                                intent.putExtra(Constants.APK_DOWNLOAD_URL, data.getNew_apk());
                                context.startService(intent);
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else {
                    if(type==1){
                        Toast.makeText(context,"当前为最新版本",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).send();
    }

    private static void showIsWifiDialog(final Context context, final String url) {
        Dialog dialog = new Dialog(context);
        dialog.setTitle("温馨提示");
        dialog.setMessage("当前网络为非WIFI，是否要进行版本升级？");
        dialog.setCancelClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setConfirmText("升级");
        dialog.setConfirmClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(context.getApplicationContext(), ApkDownloadService.class);
                intent.putExtra(Constants.APK_DOWNLOAD_URL, url);
                context.startService(intent);
            }
        });
        dialog.show();

    }

    //是否连接WIFI
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
        return versionName;
    }

}
