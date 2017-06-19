package com.youyou.xiaofeibao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.GlobalConfig;
import com.youyou.xiaofeibao.common.LayoutUtils;
import com.youyou.xiaofeibao.util.SystemUtil;

/**
 * @author zhaoyunhai on 16/4/14.
 */
public class CurrentDrawable extends Drawable {
    int screentWidth = SystemUtil.getScreentWidth((WindowManager) GlobalConfig.getAppContext().getSystemService(Context.WINDOW_SERVICE));
    public int pageCount = 3;
    public int height = LayoutUtils.getDimenPx(R.dimen.page_index_height);
    public int left = LayoutUtils.getDimenPx(R.dimen.page_index_left);

    public CurrentDrawable(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(GlobalConfig.getAppContext().getResources().getColor(R.color.app_main_color));

        canvas.drawRect(left, 0, screentWidth / pageCount, height, paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
