package com.youyou.xiaofeibao.version2.mine.orderlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.orderlist.OrderListResponseParam;

/**
 * 作者：young on 2016/12/22 13:54
 */

public class OrderlistAdapter extends CustormBaseAdapter<OrderListResponseParam> {


    public OrderlistAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(final OrderListResponseParam bean, ViewHolder holder, final int position) {
        holder.setTextViewText(R.id.tv_name, bean.getShopName())
                .setTextViewText(R.id.tv_time, bean.getCreateDate())
                .setTextViewText(R.id.tv_price, "¥" + bean.getTotal_money());
        ImageView iv = (ImageView) holder.getView(R.id.iv_img);
        TextView tv = (TextView) holder.getView(R.id.tv_pingjia);
        ImageUtils.display(iv, bean.getDoorImg());

        final String type = bean.getPay_status();
        if (type.equals("0")) {
            tv.setText("待评价");

        } else {
            tv.setText("评价完成");
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("0")) {
                    Intent intent = new Intent(mContext, PingjiaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", bean);
                    intent.putExtras(bundle);
                    intent.putExtras(intent);
                    mContext.startActivity(intent);
                }

            }
        });
    }
}
