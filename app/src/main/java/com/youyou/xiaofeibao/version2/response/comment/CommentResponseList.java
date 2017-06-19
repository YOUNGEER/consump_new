package com.youyou.xiaofeibao.version2.response.comment;

/**
 * 作者：young on 2016/12/2 09:33
 */

public class CommentResponseList {


    /**
     * content : 很好
     * createdate : 2016-08-07
     * imgUrl : 20160811/28ec34be-99af-4314-a5d1-9a508848653601.jpg
     * membername : zs123456
     * totalScore : 5
     */

    private String content;
    private String createdate;
    private String imgUrl;
    private String membername;
    private int totalScore;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
