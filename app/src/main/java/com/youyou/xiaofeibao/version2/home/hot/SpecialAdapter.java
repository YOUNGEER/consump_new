package com.youyou.xiaofeibao.version2.home.hot;

import android.content.Context;
import android.widget.ImageView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.special.SpecialResponseParam;

import java.util.List;

/**
 * 作者：young on 2016/12/1 16:logo_pic
 */

public class SpecialAdapter extends CustormBaseAdapter<SpecialResponseParam> {
    public SpecialAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public SpecialAdapter(Context context, List<SpecialResponseParam> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void initView(SpecialResponseParam bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_name, bean.getName())
                .setTextViewText(R.id.tv_price, "¥" + bean.getPrice());
        ImageUtils.display((ImageView) holder.getView(R.id.iv_img), bean.getImgurl());


    }
}
