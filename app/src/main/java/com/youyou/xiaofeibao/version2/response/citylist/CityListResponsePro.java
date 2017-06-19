package com.youyou.xiaofeibao.version2.response.citylist;

import java.util.List;

/**
 * 作者：young on 2016/12/23 09:25
 */

public class CityListResponsePro {
    private String province;

    private List<CityResponseCities> clist;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<CityResponseCities> getClist() {
        return clist;
    }

    public void setClist(List<CityResponseCities> clist) {
        this.clist = clist;
    }
}
