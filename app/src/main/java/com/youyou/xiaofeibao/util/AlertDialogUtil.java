package com.youyou.xiaofeibao.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2016/4/13.
 * 提示通知工具类
 */
public class AlertDialogUtil {

    public static void showSystemDialog2Button(Context mContext, String title, String content, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle(title).setMessage(content).setPositiveButton("确定", listener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void showSystemDialog1Button(Context mContext, String title, String content, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle(title).setMessage(content).setPositiveButton("确定", listener)
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void showSystemDialog3Button(Context mContext, String title, String content, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener navListener) {
        AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle(title).setMessage(content).setPositiveButton("确定", listener)
                .setNegativeButton("取消", navListener).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void showSystemDialog2LisButton(Context mContext, String title, String content, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle(title).setMessage(content).setPositiveButton("确定", listener)
                .setNegativeButton("取消", cancelListener).create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void showSystemDialog(Context mContext, String title, String content, String oktext, String canceltext, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle(title).setMessage(content)
                .setPositiveButton(oktext, listener)
                .setNegativeButton(canceltext, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
}
