package com.youyou.xiaofeibao.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by yun on 15/12/28.
 */
public class ViewUtils {

    public static View newInstance(Context context, int resId) {
        return View.inflate(context, resId, null);
    }

    public static View newInstance(ViewGroup parent, int resId) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    public static boolean isViewAttachedToDecorView(View view) {
        if (!(view.getContext() instanceof Activity)) {
            return true;
        }
        View decorView = ((Activity) view.getContext()).getWindow().getDecorView();
        if (view == decorView) {
            return true;
        }
        if (view.getWindowToken() != null && view.getWindowToken() != decorView.getWindowToken()) {
            // The view is not in the same window with activity. It's probably in a Dialog.
            return true;
        }
        ViewParent parent = view.getParent();
        while (parent != null) {
            if (parent == decorView) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }
}
