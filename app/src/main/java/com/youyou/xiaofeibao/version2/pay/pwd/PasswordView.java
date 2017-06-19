package com.youyou.xiaofeibao.version2.pay.pwd;

/**
 * 作者：young on 2016/12/21 14:18
 */

interface PasswordView {
    //void setError(String error);

    String getPassWord();

    void clearPassword();

    void setPassword(String password);

    void setPasswordVisibility(boolean visible);

    void togglePasswordVisibility();

    void setOnPasswordChangedListener(GridPasswordView.OnPasswordChangedListener listener);

    void setPasswordType(PasswordType passwordType);
}
