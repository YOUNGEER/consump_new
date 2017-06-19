package com.youyou.xiaofeibao.version2.response.comment;

import java.util.List;

/**
 * 作者：young on 2016/12/2 09:32
 */

public class CommentResponseData {
    private List<CommentResponseList> commentlist;

    private String avgScore;


    public List<CommentResponseList> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<CommentResponseList> commentlist) {
        this.commentlist = commentlist;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }
}
