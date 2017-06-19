package com.youyou.xiaofeibao.wxapi;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.youyou.xiaofeibao.R;

/**
 * 作者：young on 2017/4/10 09:39
 */

public class WechatShareHelper {
    private TextView tv_line;
    private TextView tv_friend;
    private AlertDialog mDialog;
    private Context mContext;

    public WechatShareHelper(Context context) {
        mContext = context;
        initView();
    }

    private void initView() {
        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.share_item, null);
        tv_line = (TextView) view.findViewById(R.id.tv_line);
        tv_friend = (TextView) view.findViewById(R.id.tv_friend);
        mDialog = new AlertDialog.Builder(mContext).setView(view).create();

        mDialog.setTitle("邀请注册");
        mDialog.show();
    }

    public void shareToWechat(){

        tv_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WeChatUtil.shareWx(mContext, SendMessageToWX.Req.WXSceneTimeline);
                mDialog.dismiss();
            }
        });

        tv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeChatUtil.shareWx(mContext,SendMessageToWX.Req.WXSceneSession);
                mDialog.dismiss();
            }
        });
    }

}
