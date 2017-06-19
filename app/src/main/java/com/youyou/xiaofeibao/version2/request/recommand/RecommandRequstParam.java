package com.youyou.xiaofeibao.version2.request.recommand;

/**
 * 作者：young on 2016/12/9 10:51
 */

public class RecommandRequstParam {

    /**
     * latitude : 31.74593
     * longitude : 117.287537
     */


    private String latitude;
    private String longitude;
    /**
     * cityCode : 127
     */

    private String cityCode;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
