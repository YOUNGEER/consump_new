package com.youyou.xiaofeibao.version2.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class RefreshLayout extends SwipeRefreshLayout implements
        OnScrollListener {

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * listview实例
     */
    private ListView mListView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private ILoadListener mOnLoadListener;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    private boolean isFling = false;


    /**
     * 加载更多的监听器
     */
    public static interface ILoadListener {
        public void onLoad();

    }


    /**
     * @param context
     */
    public RefreshLayout(Context context) {
        this(context, null);
        initData(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
            }
        }
    }

    /**
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                mLastY = 1000;
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                Log.i("cccccccc222", canLoad() + "   " + isFling);
                if (canLoad() && isFling) {
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        Log.i("cccccccc3333", isBottom() + "   " + isLoading + "    " + isPullUp());
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            if (mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1)) {
                final View bottomChildView =
                        mListView.getChildAt(mListView.getLastVisiblePosition() - mListView.getFirstVisiblePosition());
                if (bottomChildView != null) {
                    return mListView.getHeight() >= bottomChildView.getBottom();
                }
            }
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        Log.i("ssssss", "down" + mYDown + "      up" + mLastY);
        return (mYDown - mLastY) >= 100;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态为正在加载
            setLoading(true);
            //加载下一页数据
            mOnLoadListener.onLoad();
        }
    }

    public void setLoadDone() {
        setLoading(false);
    }

    public void setLoadunDone() {
        setLoading(true);
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (!isLoading) {
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(ILoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    public boolean isFling() {
        return isFling;
    }

    public void setFling(boolean fling) {
        isFling = fling;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        Log.i("cccccccc1111", canLoad() + "   " + scrollState + "    " + isFling());
        if (scrollState == SCROLL_STATE_FLING) {
            setFling(true);
        }
        if (canLoad() && scrollState == SCROLL_STATE_IDLE && isFling()) {
            loadData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
    }
}
