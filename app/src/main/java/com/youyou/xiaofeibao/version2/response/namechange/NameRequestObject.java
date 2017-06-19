package com.youyou.xiaofeibao.version2.response.namechange;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/23 17:05
 */

public class NameRequestObject extends BaseRequestObject {
    /**
     * param : {"loginName":""}
     */

    private ParamBean param;

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public static class ParamBean {
        /**
         * loginName :
         */

        private String loginName;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }
    }

}
