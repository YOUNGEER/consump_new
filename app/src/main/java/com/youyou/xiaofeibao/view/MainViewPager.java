package com.youyou.xiaofeibao.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/***
 * ��ҳ����
 *
 * @author Administrator
 */
public class MainViewPager extends ViewPager {

    private boolean isSlipping = false;//可滑动标志位

    public MainViewPager(Context context) {
        super(context);
    }

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!isSlipping) {
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!isSlipping) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }

    /**
     * 禁止滑动
     */
    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {

    }

    /***
     * 设置是否可以滑动
     *
     * @param isSlipping
     */
    public void setSlipping(boolean isSlipping) {
        this.isSlipping = isSlipping;
    }


}
