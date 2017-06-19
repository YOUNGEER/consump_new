package com.youyou.xiaofeibao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author zhaoyunhai on 16/4/12.
 */
public class AutoHeightGridView extends GridView {

    public AutoHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHeightGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
