package com.youyou.xiaofeibao.version2.mine.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.login.ForgetPwdActivity;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.settings.ChangeTongPwdActivty;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.version2.update.MyUpdateDialog;

import java.io.File;
import java.math.BigDecimal;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 作者：young on 2016/12/7 10:00
 */

public class SettingActivity extends BaseTitleActivity implements View.OnClickListener {

    @ViewInject(R.id.loginout)
    TextView loginout;
    @ViewInject(R.id.tv_set_pwd)
    TextView tv_set_pwd;
    @ViewInject(R.id.tv_clear)
    TextView tv_clear;
    @ViewInject(R.id.tv_changpwd)
    TextView tv_changpwd;
    @ViewInject(R.id.tv_about)
    TextView tv_about;

    @Override
    protected int getTitleText() {
        return R.string.setting;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_setting;
    }

    @Override
    protected void setListener() {
        super.setListener();

        tv_about.setText("当前版本" +getVersion());

        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesConfig.v2_token.set("");
                PreferencesConfig.v2_nickname.set("");
                PreferencesConfig.v2_url.set("");
                PreferencesConfig.v2_phone.set("");
                PreferencesConfig.v2_loginName.set("未登录");
                JPushInterface.setAlias(mActivity, "", new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.i("ssssssssssss",i+"   "+s);
                    }
                });
                startActivity(new Intent(mActivity, LoginOrRegisterActivity.class));
                finish();
            }
        });

        tv_set_pwd.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        tv_clear.setText(getCacheSize());
        tv_changpwd.setOnClickListener(this);

        tv_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_pwd:
                startActivity(new Intent(mActivity, ChangeTongPwdActivty.class));
                break;
            case R.id.tv_clear:
                Toast.makeText(getApplicationContext(), "正在清除缓存", Toast.LENGTH_SHORT).show();
                clearCacheFolder(mActivity.getFilesDir(), System.currentTimeMillis());
                clearCacheFolder(mActivity.getCacheDir(), System.currentTimeMillis());
                /** 子线程运行 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(getApplicationContext()).clearDiskCache();
                    }
                }).start();
                getCacheSize();
                break;
            case R.id.tv_changpwd:
                Intent intent = new Intent(mActivity, ForgetPwdActivity.class);
                intent.setAction("1");
                startActivityForResult(intent, 1);

                break;
            case R.id.tv_about:
                MyUpdateDialog.checkVersion(mActivity, 1);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            setResult(1);
            finish();
        }
    }

    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    //获取缓存的大小
    private CharSequence getCacheSize() {
        File cacheDir = this.getCacheDir();
        long size = getSize(cacheDir);
        return Formatter.formatFileSize(this, size);
    }

    public long getSize(File file) {
        // 判断文件是否存在
        if (file.exists()) {
            // 如果是目录则递归计算其内容的总大小，如果是文件则直接返回其大小
            if (!file.isFile()) {
                // 获取文件大小
                File[] fl = file.listFiles();
                long ss = 0;
                for (File f : fl)
                    ss += getSize(f);
                //转换
                BigDecimal filesize = new BigDecimal(ss);

                BigDecimal megabyte = new BigDecimal(1024 * 1024);

                float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();

                tv_clear.setText(returnValue + "M");
                return ss;
            } else {
                long ss = file.length();
                return ss;
            }
        } else {
            Toast.makeText(getApplicationContext(), "缓存文件为空", Toast.LENGTH_SHORT).show();
        }
        tv_clear.setText(0 + "M");
        return 0;
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无法获取";
        }
    }
}
