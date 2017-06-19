package com.youyou.xiaofeibao.version2.mine.yaoqing;

import android.content.Context;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.yaoqing.YQResponseParam;

/**
 * 作者：young on 2017/4/12 17:43
 */

public class YaoQingAdapter extends CustormBaseAdapter<YQResponseParam> {
    public YaoQingAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(YQResponseParam bean, ViewHolder holder, int position) {

        holder.setTextViewText(R.id.tv_phone,bean.getPhone())
                .setTextViewText(R.id.tv_time,bean.getCreatedate())
                .setTextViewText(R.id.tv_money,bean.getMoney());
    }


}
