package com.youyou.xiaofeibao.version2.tool;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youyou.xiaofeibao.R;


/**
 * Created by Administrator on 2016/5/17.
 */
public class Progress {

    public static Dialog createLoadingDialog(Context context, String msg) {


        View v = LayoutInflater.from(context).inflate(R.layout.v2_loading_dialog,
                null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局

        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
//      TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字

        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_rotate);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
//      tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog


        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;

    }

}
