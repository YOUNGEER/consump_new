package com.youyou.xiaofeibao;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by Administrator on 2016/7/22.
 * 用来显示 上拉刷新的时候 提示语
 */
public class PullToRefreshView {
    //ScrollV
    public static void ScrollViewrefresh(PullToRefreshScrollView ScrollView, Context context) {
        ScrollView.getLoadingLayoutProxy(false, true).setPullLabel(context.getString(R.string.release_to_load));
        ScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(context.getString(R.string.loading));
        ScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(context.getString(R.string.pull_to_load));
    }

    //listview
    public static void ListViewrefresh(PullToRefreshListView ScrollView, Context context) {
        ScrollView.getLoadingLayoutProxy(false, true).setPullLabel(context.getString(R.string.release_to_load));
        ScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(context.getString(R.string.loading));
        ScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(context.getString(R.string.pull_to_load));
    }
}
