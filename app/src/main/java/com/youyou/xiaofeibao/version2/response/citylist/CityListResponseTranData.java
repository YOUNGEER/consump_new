package com.youyou.xiaofeibao.version2.response.citylist;

/**
 * 作者：young on 2016/12/23 09:52
 */

public class CityListResponseTranData {

    public static final int ITEM = 1;
    public static final int SECTION = 0;


    private int type;
    private CityResponseCities mObject;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CityResponseCities getObject() {
        return mObject;
    }

    public void setObject(CityResponseCities object) {
        mObject = object;
    }

    public CityListResponseTranData(int type, CityResponseCities object) {
        this.type = type;
        mObject = object;
    }

}
