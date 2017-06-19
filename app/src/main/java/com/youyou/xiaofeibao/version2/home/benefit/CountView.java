package com.youyou.xiaofeibao.version2.home.benefit;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.youyou.xiaofeibao.R;

/**
 * 作者：young on 2016/11/18 15:45
 */

public class CountView extends View {

    private Paint mPaint;

    private int titleColor;
    private int titleSize;
    private String titleText;
    private int titleBackgroundColor;

    private Rect mBound;

    public CountView(Context context) {
        this(context, null);

    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final Resources.Theme theme = context.getTheme();
        TypedArray array = theme.obtainStyledAttributes(attrs, R.styleable.BenefitCountView, defStyleAttr, 0);
        if (null != array) {
            int n = array.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case R.styleable.BenefitCountView_titleColor:
                        titleColor = array.getColor(attr, Color.BLACK);
                        break;
                    case R.styleable.BenefitCountView_titleSize:
                        titleSize = array.getDimensionPixelSize(attr, titleSize);
                        break;
                    case R.styleable.BenefitCountView_titleText:
                        titleText = array.getString(attr);
                        break;
                    case R.styleable.BenefitCountView_titleBackgroundColor:
                        titleBackgroundColor = array.getColor(attr, Color.WHITE);
                        break;
                }
            }
            array.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(titleSize);
        /**
         * 得到自定义View的titleText内容的宽和高
         */
        mBound = new Rect();
        mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
    }

    public CountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void initView(Context context, AttributeSet attrs) {


    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mPaint.setColor(titleBackgroundColor);
        canvas.drawCircle(getWidth() / 2f, getWidth() / 2f, getWidth() / 2f, mPaint);
        mPaint.setColor(titleColor);
        canvas.drawText(titleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        int width;
        int height;
        Rect mBounds = new Rect();
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText, 0, titleText.length(), mBounds);
            float textWidth = mBounds.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {

            height = width;
        }
        /**
         * 最后调用父类方法,把View的大小告诉父布局。
         */
        setMeasuredDimension(width, height);


    }
}

