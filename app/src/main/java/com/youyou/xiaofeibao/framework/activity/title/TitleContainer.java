package com.youyou.xiaofeibao.framework.activity.title;

import android.view.View;
import android.view.ViewGroup;

/**
 * 当 title 设入自定义 view 时候需要继承这个类，实现设置 title 的接口
 * <p/>
 * Created by yun on 16/1/7.
 */
public interface TitleContainer {

    void setTitle(CharSequence title);

    void setTitle(int resId);

    void setLeftView(View view, ViewGroup.LayoutParams layoutParams);

    void setRightView(View view, ViewGroup.LayoutParams layoutParams);

    void setTitleGone();

}
