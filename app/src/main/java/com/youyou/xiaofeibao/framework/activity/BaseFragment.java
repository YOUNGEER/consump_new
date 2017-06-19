package com.youyou.xiaofeibao.framework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

/**
 * Created by yun on 15/12/28.
 */
public abstract class BaseFragment extends Fragment {
    protected View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutResId(), container, false);
        ViewUtils.inject(this, mContentView);
        initView();
        initData();
        updateUI();
        setListener();
        return mContentView;
    }

    protected void initView() {

    }


    protected abstract void initData();

    protected abstract void updateUI();


    protected abstract void setListener();

    abstract protected int getLayoutResId();

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

//    /**
//     * 一体化状物栏
//     */
//    private void setTranslucentStatus() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window win = getActivity().getWindow();
//            WindowManager.LayoutParams winParams = win.getAttributes();
//            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            winParams.flags |= bits;
//            win.setAttributes(winParams);
//        }
//        SystemStatusManager tintManager = new SystemStatusManager(getActivity());
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(0);
//    }

}
