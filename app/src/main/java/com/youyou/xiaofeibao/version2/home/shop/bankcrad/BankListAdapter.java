package com.youyou.xiaofeibao.version2.home.shop.bankcrad;

import android.content.Context;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.banklist.BanklistResponseList;

/**
 * 作者：young on 2016/12/27 14:59
 */

public class BankListAdapter extends CustormBaseAdapter<BanklistResponseList> {
    public BankListAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(BanklistResponseList bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_content,bean.getBank_name());

    }
}
