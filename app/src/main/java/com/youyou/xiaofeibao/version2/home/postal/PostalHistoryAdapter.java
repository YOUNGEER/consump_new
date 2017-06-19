package com.youyou.xiaofeibao.version2.home.postal;/**
 * 作者：young on 2017/2/15 18:06
 */

import android.content.Context;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.postalhistory.PostalHistoryResponseParam;


public class PostalHistoryAdapter extends CustormBaseAdapter<PostalHistoryResponseParam> {
    public PostalHistoryAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(PostalHistoryResponseParam bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_xiang,bean.getDescription())
                .setTextViewText(R.id.tv_num,bean.getMoney()+"")
                .setTextViewText(R.id.tv_postal_time,bean.getCreatedate());
    }
}
