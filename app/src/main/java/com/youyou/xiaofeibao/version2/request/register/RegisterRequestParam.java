package com.youyou.xiaofeibao.version2.request.register;

/**
 * 作者：young on 2016/10/20 11:06
 */

public class RegisterRequestParam {


    /**
     * phone : 17755765582
     * password : 123456
     * validatecode : 469341
     * invitecode :
     */

    private String phone;
    private String password;
    private String validatecode;
    private String invitecode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidatecode() {
        return validatecode;
    }

    public void setValidatecode(String validatecode) {
        this.validatecode = validatecode;
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }
}
