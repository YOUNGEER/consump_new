package com.youyou.xiaofeibao.version2.home;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;


/**
 * 作者：young on 2016/10/12 11:18
 */

public class ToolbarScrollView extends ScrollView {

    private OnScrollChangedListener mOnScrollChangedListener;


    public ToolbarScrollView(Context context) {
        super(context);
    }

    public ToolbarScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i("ssssssssssss", "aaaaaaa");
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }

}
