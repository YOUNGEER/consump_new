package com.youyou.xiaofeibao.version2.home.shop.order;

import android.content.Context;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.shoporders.ShopOrderResponseParam;

/**
 * 作者：young on 2016/12/12 14:21
 */

public class OrderManageAdapter extends CustormBaseAdapter<ShopOrderResponseParam> {

    public OrderManageAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(ShopOrderResponseParam bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_num, "订单号："+bean.getPay_number())
                .setTextViewText(R.id.tv_count, bean.getTotal_money() + "")
                .setTextViewText(R.id.tv_createdate, bean.getCreateDate())
                .setTextViewText(R.id.tv_des, bean.getOrder_description());

    }
}
