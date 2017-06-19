package com.youyou.xiaofeibao.version2.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;

/**
 * @author zhaoyunhai on 16/3/30.
 */
public class PopupWindows {
    private final Activity mActivity;
    private View.OnClickListener mConfirmListener;
    private View.OnClickListener mCancelListener;
    private View.OnClickListener mConfirmSecondListener;
    private PopupWindow popupWindow;

    public PopupWindows(Activity activity) {
        this.mActivity = activity;
    }

    private void showPupwindows() {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowView = inflater.inflate(R.layout.popup_windows, null);
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置PopupWindow的弹出和消失效果
        popupWindow.setAnimationStyle(R.style.popupAnimation);
        TextView confirmButton = (TextView) popupWindowView.findViewById(R.id.confirmFirstButton);
        if (mConfirmListener != null) {
            confirmButton.setOnClickListener(mConfirmListener);
        }
        TextView confirmSecnodButton = (TextView) popupWindowView.findViewById(R.id.confirmSecondButton);
        if (mConfirmSecondListener != null) {
            confirmSecnodButton.setOnClickListener(mConfirmSecondListener);
        }

        TextView cancleButton = (TextView) popupWindowView.findViewById(R.id.cancleButton);
        if (mCancelListener != null) {
            cancleButton.setOnClickListener(mCancelListener);
        } else {

        }
        popupWindow.showAtLocation(confirmButton, Gravity.BOTTOM, 0, 0);
    }

    public void setConfirmClickListener(View.OnClickListener listener) {
        this.mConfirmListener = listener;
    }

    public void setConfirmSecondClickListener(View.OnClickListener listener) {
        this.mConfirmSecondListener = listener;
    }

    public void setCancelFirmClickListener(View.OnClickListener listener) {
        this.mCancelListener = listener;
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public void show() {
        showPupwindows();
    }
}
