package com.youyou.xiaofeibao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @Package com.qianfeng.zw.mgstreet.com.doumee.xiaofeibao.widget
 * @作 用:
 * @创 建 人: zhangwei
 * @日 期: 15/5/8 16:44
 * @修 改 人:
 * @日 期:
 */
public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
