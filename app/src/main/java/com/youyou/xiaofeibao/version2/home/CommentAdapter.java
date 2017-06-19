package com.youyou.xiaofeibao.version2.home;

import android.content.Context;
import android.widget.ImageView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.comment.CommentResponseList;
import com.youyou.xiaofeibao.version2.widget.ListStar;

import java.util.List;

/**
 * 作者：young on 2016/12/1 16:logo_pic
 */

public class CommentAdapter extends CustormBaseAdapter<CommentResponseList> {
    public CommentAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public CommentAdapter(Context context, List<CommentResponseList> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void initView(CommentResponseList bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_name, bean.getMembername())
                .setTextViewText(R.id.tv_date, "" + bean.getCreatedate())
                .setTextViewText(R.id.tv_content, bean.getContent());
        ListStar listStar = (ListStar) holder.getView(R.id.liststar);
        listStar.setClickable(false);
        Float f = 0f;
        try {
            f = bean.getTotalScore() * 1.0f;
        } catch (Exception e) {
            f = 0f;
        }
        listStar.setStar(f);
        ImageUtils.display((ImageView) holder.getView(R.id.iv_img), bean.getImgUrl());
    }
}
