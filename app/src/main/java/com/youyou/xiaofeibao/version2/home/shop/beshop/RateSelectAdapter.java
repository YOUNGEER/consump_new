package com.youyou.xiaofeibao.version2.home.shop.beshop;

import android.content.Context;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;

import java.util.List;

/**
 * 作者：young on 2017/7/13 18:02
 */

public class RateSelectAdapter extends CustormBaseAdapter<Float> {
    public RateSelectAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public RateSelectAdapter(Context context, List<Float> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void initView(Float bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_content, bean.toString());
    }
}
