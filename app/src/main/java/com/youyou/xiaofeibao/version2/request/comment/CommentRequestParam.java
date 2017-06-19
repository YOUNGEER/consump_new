package com.youyou.xiaofeibao.version2.request.comment;

/**
 * 作者：young on 2016/12/2 09:31
 */

public class CommentRequestParam {


    /**
     * memid : 0041a20b-4dfc-4f69-b843-221237dabf87
     * pageNum : 1
     * pageOffest : 10
     */

    private String memid;
    private String pageNum;
    private String pageOffest;

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

    public String getPageOffest() {
        return pageOffest;
    }

    public void setPageOffest(String pageOffest) {
        this.pageOffest = pageOffest;
    }
}
