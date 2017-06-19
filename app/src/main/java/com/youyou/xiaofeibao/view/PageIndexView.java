package com.youyou.xiaofeibao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.GlobalConfig;
import com.youyou.xiaofeibao.common.LayoutUtils;
import com.youyou.xiaofeibao.framework.Log.LogDoumee;
import com.youyou.xiaofeibao.framework.Log.LogTag;
import com.youyou.xiaofeibao.util.SystemUtil;


/**
 * 自定义页码指示器
 */
public class PageIndexView extends View {
    int screentWidth = SystemUtil.getScreentWidth((WindowManager) GlobalConfig.getAppContext().getSystemService(Context.WINDOW_SERVICE));
    public int pageCount = 3;
    public int height = LayoutUtils.getDimenPx(R.dimen.page_index_height);
    public int left = LayoutUtils.getDimenPx(R.dimen.page_index_left);
    private int targetPage;
    private int itemWith = screentWidth / pageCount;
    private int percent;
    private int lastPage;
    private Paint paint;
    private static final int TOTAL_PAINT_TIMES = 100; //控制绘制速度,分100次完成绘制
    int currentTimes = 0;
    private int viewWidth;
    private int viewHeight;
    int offset;
    private int backgroundColor = -1;
    private Paint paintBack;


    public PageIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PageIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageIndexView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paintBack = new Paint();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paintBack.setStyle(Paint.Style.FILL);
        paint.setColor(GlobalConfig.getAppContext().getResources().getColor(R.color.app_main_color));
        screentWidth = SystemUtil.getScreentWidth((WindowManager) GlobalConfig.getAppContext().getSystemService(Context.WINDOW_SERVICE));
        height = LayoutUtils.getDimenPx(R.dimen.page_index_height);
        left = LayoutUtils.getDimenPx(R.dimen.page_index_left);
        itemWith = screentWidth / pageCount;
        offset = 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (pageCount <= 0) {
            pageCount = 1;
        }
       /* currentTimes++;
        int plusWith = currentTimes / TOTAL_PAINT_TIMES;

        int left = lastPage*itemWith;
        int right = left + itemWith;
        for(int i=0;i<targetPage*itemWith;i++) {
            canvas.restore();
            if (lastPage == 0) {
                canvas.drawRect(targetPage * itemWith, 0, itemWith + targetPage * itemWith, height, paint);
            }

            if (lastPage < targetPage) {
                canvas.drawRect(left+i,0, right+i,height,paint);//右边
            }else{
                canvas.drawRect(left-i,0, right-i,height,paint);
            }

        }*/

        if (backgroundColor == -1) {
            paintBack.setColor(GlobalConfig.getAppContext().getResources().getColor(R.color.app_main_color));
        } else {
            paintBack.setColor(GlobalConfig.getAppContext().getResources().getColor(backgroundColor));
        }
        canvas.drawRect(0, height - 3, viewWidth, height, paintBack);
        canvas.save();
        canvas.drawRect(offset + targetPage * itemWith, 0, itemWith + targetPage * itemWith - offset, height, paint);
        canvas.restore();


        if (LogDoumee.isLoggable(LogTag.GLOBAL, LogDoumee.ERROR)) {
            LogDoumee.e(LogTag.GLOBAL, "targetPage:" + targetPage + "left" + targetPage * itemWith + "---right--" + (itemWith + targetPage * itemWith));
        }

    }

    public void setTargetPage(final int page) {
        TranslateAnimation anim = new TranslateAnimation(targetPage * itemWith, itemWith * page, 0, 0);

        anim.setDuration(200);
//        this.startAnimation(anim);
        lastPage = targetPage;
        targetPage = page;

        this.invalidate();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    public void setPageCount(int count) {
        backgroundColor = -1;
        pageCount = count;
        init();
    }

    public void setPageCount(int count, int color) {
        pageCount = count;
        this.backgroundColor = color;
        init();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(true, targetPage * itemWith, 0, itemWith + targetPage * itemWith, height);
    }
}
