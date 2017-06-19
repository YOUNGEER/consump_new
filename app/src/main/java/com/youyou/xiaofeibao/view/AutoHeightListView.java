package com.youyou.xiaofeibao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author zhaoyunhai on 16/4/12.
 */
public class AutoHeightListView extends ListView {


    public AutoHeightListView(Context context) {
        // TODO Auto-generated method stub
        super(context);
    }

    public AutoHeightListView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        super(context, attrs);
    }

    public AutoHeightListView(Context context, AttributeSet attrs, int defStyle) {
        // TODO Auto-generated method stub
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
