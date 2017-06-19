package com.youyou.xiaofeibao.framework.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.PermissionConstant;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by yun on 15/12/28.
 */
public class BaseActivity extends AppCompatActivity {
    protected BaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    protected void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 默认是使用fragment来替换
     *
     * @return
     */
    protected int getLayoutId() {
        return R.layout.base_fragment_activity;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mFragment != null) {
            return mFragment.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    //permissiongen  为子类提供一个权限检查方法
    public boolean hasPermission(String... permissions) {
        for (String perssion : permissions) {
            if (ActivityCompat.checkSelfPermission(this, perssion) != PackageManager.PERMISSION_GRANTED) {
                return false;//没有授权
            }
        }
        return true;
    }

    //权限申请方法
    public void requestPermission(int code, String... permission) {
        ActivityCompat.requestPermissions(this, permission, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionConstant.WIRTE_EXTERNAL_CODE:
                doSDCardPermission();
                break;
            case PermissionConstant.CALL_PHONE_CODE:
                doCallPhone();
                break;
        }
    }

    public void doSDCardPermission() {

    }

    public void doCallPhone() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}
