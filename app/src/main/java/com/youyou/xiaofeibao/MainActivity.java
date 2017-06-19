package com.youyou.xiaofeibao;

import android.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.youyou.xiaofeibao.framework.activity.BaseActivity;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.alliance.AllicanceFragment;
import com.youyou.xiaofeibao.version2.home.HomeFragment;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.mine.MineFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.rb_home)
    RadioButton iv_home;
    @ViewInject(R.id.rb_alliance)
    RadioButton iv_alliance;
    @ViewInject(R.id.rb_mine)
    RadioButton iv_mine;

    private ArrayList<Fragment> fragmetList;
    private Fragment currentFrag;
    private FragmentManager mFragmentManager;

    private AllicanceFragment mAllicanceFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_v2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        if (PreferencesConfig.IS_FIRST.get()) {
            PreferencesConfig.IS_FIRST.set(false);
            startActivity(new Intent(MainActivity.this, GuideActivity.class));
            finish();
        } else {
            initView();
            setListener();
        }

        if (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else {
            requestPermission(Config.PERMISSION_FILE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    protected void initView() {
        mFragmentManager = getSupportFragmentManager();
        initViewPage();
    }

    private void initViewPage() {
        fragmetList = new ArrayList<>();
        mAllicanceFragment=new AllicanceFragment();
        fragmetList.add(new HomeFragment());
        fragmetList.add(mAllicanceFragment);
        fragmetList.add(new MineFragment());
        currentFrag = fragmetList.get(0);
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, currentFrag).commit();
    }

    public void setPagePosition(int page) {
        setChecked(page);
    }


    protected void setListener() {

        iv_home.setOnClickListener(this);
        iv_alliance.setOnClickListener(this);
        iv_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int code = v.getId();
        switch (code) {
            case R.id.rb_home:
                setChecked(0);
                break;
            case R.id.rb_alliance:
                setChecked(1);
                break;
            case R.id.rb_mine:
                if("".equals(PreferencesConfig.v2_token.get())){
                    startActivity(new Intent(this, LoginOrRegisterActivity.class));
                }else {
                    setChecked(2);
                }
                break;
        }

    }

    public void cityChange(){
        mAllicanceFragment.getDistrictData();
    }

    private void setChecked(int pos) {
        switchFragmentShow(fragmetList.get(pos));
        switch (pos) {
            case 0:
                setTitle(R.string.title_home);
                iv_home.setChecked(true);
                iv_alliance.setChecked(false);
                iv_mine.setChecked(false);
                break;
            case 1:
                setTitle(R.string.title_alliance);
                iv_home.setChecked(false);
                iv_alliance.setChecked(true);
                iv_mine.setChecked(false);
                break;
            case 2:
                setTitle(R.string.title_mine);
                iv_home.setChecked(false);
                iv_alliance.setChecked(false);
                iv_mine.setChecked(true);
                break;
        }

    }

    private void switchFragmentShow(Fragment fragment) {
        if (fragment != currentFrag) {
            if (!fragment.isAdded()) {
                mFragmentManager.beginTransaction().hide(currentFrag)
                        .add(R.id.fragment_container, fragment).commit();
            } else {
                mFragmentManager.beginTransaction().hide(currentFrag)
                        .show(fragment).commit();
            }
            currentFrag = fragment;
        }
    }

    /*****************
     * 以下实现两次退出逻辑
     *********************/
    boolean isExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), R.string.system_exit,
                    Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            MainActivity.this.finish();
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }

    };

    /**************** 以上实现两次退出逻辑 *********************/

}
