package com.youyou.xiaofeibao.version2.home.partner;

import android.content.Context;
import android.widget.ImageView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.alliancemarket.AllianceMarketResponseParam;

/**
 * 作者：young on 2016/10/21 15:36
 */

public class AllianceMarketAdapter extends CustormBaseAdapter<AllianceMarketResponseParam> {

    public AllianceMarketAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(AllianceMarketResponseParam bean, ViewHolder holder, int position) {
        String url = bean.getDoorImg();
        ImageUtils.display((ImageView) holder.getView(R.id.img), url);
        holder.setTextViewText(R.id.name, bean.getShopName() + "(" + bean.getBranchName() + ")")
                .setTextViewText(R.id.addr, bean.getAddr2());
    }
}
