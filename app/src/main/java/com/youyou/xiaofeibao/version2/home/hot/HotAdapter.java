package com.youyou.xiaofeibao.version2.home.hot;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.homehot.HothomeParam;

import java.text.DecimalFormat;

/**
 * 作者：young on 2016/10/13 09:45
 */

public class HotAdapter extends CustormBaseAdapter<HothomeParam> {

    public HotAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(HothomeParam bean, ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("0.0");
        double x = bean.getDistance();
        String result = df.format(x);
//        int distance = (int) (bean.getDistance() * 1000);

        holder.setTextViewText(R.id.tv_name, bean.getShopname())
                .setTextViewText(R.id.tv_addr, bean.getAddr())
                .setTextViewText(R.id.tv_distance, result + "km");

        ImageUtils.display((ImageView) holder.getView(R.id.iv_img), bean.getDoorimg());

        TextView discount=holder.getView(R.id.tv_discount);
        if (bean.getDiscount().equals("返币0.0%")){
            discount.setVisibility(View.GONE);
        }else {
            discount.setVisibility(View.VISIBLE);
            discount.setText(bean.getDiscount());
        }

    }
}
