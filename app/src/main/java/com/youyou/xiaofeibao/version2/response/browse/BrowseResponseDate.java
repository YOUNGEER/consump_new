package com.youyou.xiaofeibao.version2.response.browse;

import java.util.List;

/**
 * 作者：young on 2016/11/14 09:47
 */

public class BrowseResponseDate {

    private String time;

    private List<BrowseResponseParam> mlist;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<BrowseResponseParam> getMlist() {
        return mlist;
    }

    public void setMlist(List<BrowseResponseParam> mlist) {
        this.mlist = mlist;
    }
}
