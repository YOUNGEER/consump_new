package com.youyou.xiaofeibao.version2.alliance;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.shoplist.ShopListResponseParam;
import com.youyou.xiaofeibao.version2.widget.ListStar;

import java.text.DecimalFormat;

/**
 * 作者：young on 2016/12/3 14:25
 */

public class ShopDataAdapter extends CustormBaseAdapter<ShopListResponseParam> {


    public ShopDataAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(ShopListResponseParam bean, ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("0.0");
        double x;
        try {
            x = bean.getDistance();
        } catch (Exception e) {
            x = 0f;
        }
        String result = df.format(x);


        ListStar listStar = (ListStar) holder.getView(R.id.liststar_alliance);
        listStar.setClickable(false);
        try {
            float f = Float.parseFloat(bean.getAvgScore());
            listStar.setStar(f);
        } catch (Exception e) {
            listStar.setStar(0f);
        }

        holder.setTextViewText(R.id.tv_name, bean.getShopName())
                .setTextViewText(R.id.tv_addr, bean.getAddr())
                .setTextViewText(R.id.tv_distance, result + "km")
                .setTextViewText(R.id.star_num, bean.getAvgScore())
                ;

        TextView discount=holder.getView(R.id.tv_discount);

        ImageUtils.display((ImageView) holder.getView(R.id.iv_img), bean.getDoorImg());

        if (bean.getDiscount().equals("返币0.0%")){
            discount.setVisibility(View.GONE);
        }else {
            discount.setVisibility(View.VISIBLE);
            discount.setText(bean.getDiscount());
        }
    }
}
