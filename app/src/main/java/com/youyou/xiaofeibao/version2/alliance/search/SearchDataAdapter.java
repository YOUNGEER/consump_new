package com.youyou.xiaofeibao.version2.alliance.search;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.shoplist.ShopListResponseParam;
import com.youyou.xiaofeibao.version2.widget.ListStar;

/**
 * 作者：young on 2016/12/3 14:25
 */

public class SearchDataAdapter extends CustormBaseAdapter<ShopListResponseParam> {


    public SearchDataAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(ShopListResponseParam bean, ViewHolder holder, int position) {

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
                .setTextViewText(R.id.star_num, bean.getAvgScore())
                .setTextViewText(R.id.tv_discount,bean.getDiscount());

        holder.getView(R.id.tv_distance).setVisibility(View.GONE);

        ImageUtils.display((ImageView) holder.getView(R.id.iv_img), bean.getDoorImg());
    }
}
