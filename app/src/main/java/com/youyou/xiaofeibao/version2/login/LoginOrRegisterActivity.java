package com.youyou.xiaofeibao.version2.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.util.ProgressUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：young on 2016/10/4 10:31
 */

public class LoginOrRegisterActivity extends BaseActivity implements RegisterSuccess {

    private ArrayList<Fragment> fragmetList;
    @ViewInject(R.id.indicator)
    ViewPagerIndicator mIndicator;
    @ViewInject(R.id.viewPager)
    ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

    private Dialog mProgressUtils=null;

    private List<String> mDatas = Arrays.asList("账号注册", "账号登陆");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_v2;
    }


    protected void initView() {

        mIndicator.setTabItemTitles(mDatas);
        fragmetList = new ArrayList<>();
        RegisterFrgament registerFragment = new RegisterFrgament();
        fragmetList.add(registerFragment);
        fragmetList.add(new LoginFragment());
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmetList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmetList.get(position);
            }
        };

        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 1);

//        registerFragment.setRegisterSuccess(this);

    }

    @Override
    public void success() {
//        mIndicator.setViewPager(mViewPager, 1);
    }

    public  void showDialog(){

        if(null==mProgressUtils){
            mProgressUtils= ProgressUtils.createLoadingDialog(LoginOrRegisterActivity.this);
        }
        if(!mProgressUtils.isShowing()){
            mProgressUtils.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null!=mProgressUtils&&mProgressUtils.isShowing()){
            mProgressUtils.dismiss();
        }
    }

}
