package com.youyou.xiaofeibao.version2.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.recommand.RecommandResponseList;
import com.youyou.xiaofeibao.version2.widget.ListStar;

import java.text.DecimalFormat;

/**
 * 作者：young on 2016/12/8 09:34
 */

public class RecommandAdapter extends CustormBaseAdapter<RecommandResponseList> {
    public RecommandAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(RecommandResponseList bean, ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("0.0");
        double x = bean.getDistance();
        String result = df.format(x);

        float f;
        try {
            f = Float.parseFloat(bean.getAvgScore());
        } catch (Exception e) {
            f = 0.0f;
        }
        ListStar listStar = (ListStar) holder.getView(R.id.liststar);
        listStar.setClickable(false);
        listStar.setStar(f);

        holder.setTextViewText(R.id.tv_name, bean.getShopName())
                .setTextViewText(R.id.tv_addr, bean.getAddr())
                .setTextViewText(R.id.tv_distance, result + "km")
                .setTextViewText(R.id.star_num, bean.getAvgScore());

        ImageUtils.display((ImageView) holder.getView(R.id.iv_img), bean.getDoorImg());


        TextView discount=holder.getView(R.id.tv_discount);
        if (bean.getDiscount().equals("返币0.0%")){
            discount.setVisibility(View.GONE);
        }else {
            discount.setVisibility(View.VISIBLE);
            discount.setText(bean.getDiscount());
        }


    }
}
