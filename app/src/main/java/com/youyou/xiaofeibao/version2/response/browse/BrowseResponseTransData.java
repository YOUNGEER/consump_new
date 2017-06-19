package com.youyou.xiaofeibao.version2.response.browse;

/**
 * 作者：young on 2016/11/14 09:47
 */

public class BrowseResponseTransData {

    public static final int ITEM = 1;
    public static final int SECTION = 0;


    private int type;
    private BrowseResponseParam mObject;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BrowseResponseParam getObject() {
        return mObject;
    }

    public void setObject(BrowseResponseParam object) {
        mObject = object;
    }

    public BrowseResponseTransData(int type, BrowseResponseParam object) {
        this.type = type;
        mObject = object;
    }
}
