package com.youyou.xiaofeibao.version2.request.changepwd;

/**
 * 作者：young on 2016/12/23 12:01
 */

public class ChangePwdRequestParam {


    /**
     * phone : 18656095669
     * repassword : 666666
     * validatecode : 384569
     * type : 1
     */

    private String phone;
    private String repassword;
    private String validatecode;
    private String type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getValidatecode() {
        return validatecode;
    }

    public void setValidatecode(String validatecode) {
        this.validatecode = validatecode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
