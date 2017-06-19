package com.youyou.xiaofeibao.version2.request.fund;

/**
 * 作者：young on 2017/3/10 14:56
 */

public class ShopFundRequestParam {

    /**
     * starttime : 2017-01-10
     * endtime : 2017-03-10
     * memid : 1aaa6698-d6dd-11e6-ad4a-6c92bf2cdbd1
     * pageNum : 1
     * pageOffset : 10
     */

    private String starttime;
    private String endtime;
    private String memid;
    private String pageNum;
    private String pageOffset;

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getMemid() {
        return memid;
    }

    public void setMemid(String memid) {
        this.memid = memid;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(String pageOffset) {
        this.pageOffset = pageOffset;
    }
}
