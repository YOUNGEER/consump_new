package com.youyou.xiaofeibao.framework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.title.CommonTitleView;
import com.youyou.xiaofeibao.framework.activity.title.TitleContainer;
import com.lidroid.xutils.ViewUtils;


/**
 * Created by yun on 15/12/28.
 */
public abstract class BaseTitleActivity extends BaseActivity {

    private ActionBar mActionBar;
    protected TitleContainer mCustomTitleView;
    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(R.style.AppthemeActivity);
        mCustomTitleView = CommonTitleView.newInstance(this);
        mActivity = this;
        ViewUtils.inject(this);
        initToolbar();
        setCustomTitleView(mCustomTitleView);
//        setTranslucentStatus();
        initView();
        setListener();
        initData();
    }

    protected void initView() {

    }

    protected void setListener() {

    }

    protected void initData() {

    }


    protected <V extends TitleContainer> void setCustomTitleView(V view) {
        if (view instanceof View) {

            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);

            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setElevation(0);

            ActionBar.LayoutParams layoutParams =
                    new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                            ActionBar.LayoutParams.MATCH_PARENT);
            mActionBar.setCustomView((View) view, layoutParams);


            if (((View) view).getParent() instanceof Toolbar) {
                Toolbar parent = (Toolbar) ((View) view).getParent();
                parent.setContentInsetsAbsolute(0, 0);
            }
            mCustomTitleView = view;
        } else if (view == null) {
            mActionBar.setElevation(0);
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowCustomEnabled(false);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_title_fragment_activity;
    }

    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeAsUpIndicator(R.drawable.button_yuanjiao_income);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
//        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        setTitle(getTitleText());
    }

    abstract protected int getTitleText();

    public void setTitleGone() {
        if (mCustomTitleView != null) {
            mCustomTitleView.setTitleGone();
        }
    }

    public void setLeftTitleView(View v) {
        if (mCustomTitleView != null) {
            mCustomTitleView.setLeftView(v, null);
        }
    }

    final public void setRightTitleView(View v) {
        if (mCustomTitleView != null) {
            mCustomTitleView.setRightView(v, null);
        }
    }

    final public void setRightTitleView(View v, ViewGroup.LayoutParams layoutParams) {
        if (mCustomTitleView != null) {
            mCustomTitleView.setRightView(v, layoutParams);
        }
    }

    final public void setLeftTitleView(View v, ViewGroup.LayoutParams layoutParams) {
        if (mCustomTitleView != null) {
            mCustomTitleView.setLeftView(v, layoutParams);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mCustomTitleView != null) {
            mCustomTitleView.setTitle(title);
            return;
        }
        /*if (mCollapsingToolbar != null) {
            mCollapsingToolbar.setTitle(title);
            return;
        }*/
        super.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        if (mCustomTitleView != null) {
            mCustomTitleView.setTitle(titleId);
        }
       /* if (mCollapsingToolbar != null) {
            mCollapsingToolbar.setTitle(StringUtil.getString(titleId));
            return;
        }*/
        super.setTitle(titleId);
    }

}
