package com.youyou.xiaofeibao.framework.activity.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.StringUtil;
import com.youyou.xiaofeibao.common.ViewUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;


public class CommonTitleView extends RelativeLayout implements TitleContainer {


    private TextView mTitle;
    private ImageView mBackImageView;
    private BaseTitleActivity mBaseTitleActivity;
    private LinearLayout mLeftLayout;
    private LinearLayout mRightLayout;


    public CommonTitleView(Context context) {
        super(context);
    }

    public CommonTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static CommonTitleView newInstance(ViewGroup parent) {
        return (CommonTitleView) ViewUtils.newInstance(parent, R.layout.common_title_view_layout);
    }

    public static CommonTitleView newInstance(Context context) {
        return (CommonTitleView) ViewUtils.newInstance(context, R.layout.common_title_view_layout);
    }

    public static CommonTitleView newInstance(BaseTitleActivity activity) {
        CommonTitleView commonTitleView =
                (CommonTitleView) ViewUtils.newInstance(activity, R.layout.common_title_view_layout);
        commonTitleView.mBaseTitleActivity = activity;
        return commonTitleView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = (TextView) findViewById(R.id.common_title_view_layout_title);
        mBackImageView = (ImageView) findViewById(R.id.common_title_view_layout_left_arrow);
        mLeftLayout = (LinearLayout) findViewById(R.id.common_title_view_layout_left_container);
        mRightLayout = (LinearLayout) findViewById(R.id.common_title_view_layout_right_container);
        initListener();
    }

    private void initListener() {
        mBackImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBaseTitleActivity != null) {
                    mBaseTitleActivity.onBackPressed();
                }
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    @Override
    public void setTitle(int resId) {
        mTitle.setText(StringUtil.getString(resId));
    }


    @Override
    public void setLeftView(View v, ViewGroup.LayoutParams layoutParams) {
        if (v == null) {
            return;
        }
        mLeftLayout.removeAllViews();
        if (layoutParams == null) {
            mLeftLayout.addView(v);
        } else {
            mLeftLayout.addView(v, layoutParams);
        }
    }

    @Override
    public void setRightView(View v, ViewGroup.LayoutParams layoutParams) {
        if (v == null) {
            return;
        }
        mRightLayout.removeAllViews();
        if (layoutParams == null) {
            mRightLayout.addView(v);
        } else {
            mRightLayout.addView(v, layoutParams);
        }
    }

    @Override
    public void setTitleGone() {
        mTitle.setVisibility(GONE);
    }
}
