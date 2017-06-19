package com.youyou.xiaofeibao.version2.mine.yaoqing;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 作者：young on 2017/4/17 16:30
 */

public class AutoListView extends PullToRefreshListView {
    public AutoListView(Context context) {
        super(context);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoListView(Context context, Mode mode) {
        super(context, mode);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
