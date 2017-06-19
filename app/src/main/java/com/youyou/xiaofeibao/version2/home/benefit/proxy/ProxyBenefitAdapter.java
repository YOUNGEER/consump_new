package com.youyou.xiaofeibao.version2.home.benefit.proxy;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.proxydetail.BilllogListBean;

/**
 * 作者：young on 2017/1/4 16:40
 */

public class ProxyBenefitAdapter extends CustormBaseAdapter<BilllogListBean> {


    public ProxyBenefitAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView(BilllogListBean bean, ViewHolder holder, int position) {

        holder.setTextViewText(R.id.tv_after,bean.getAfter_money()+"")
                .setTextViewText(R.id.tv_time,bean.getCreateyear()+"-"+bean.getCreatedate()+"-"+bean.getCreatetime())
                .setTextViewText(R.id.tv_type,bean.getTitle())
        .setTextViewText(R.id.tv_money,bean.getMoney());
    }
}
