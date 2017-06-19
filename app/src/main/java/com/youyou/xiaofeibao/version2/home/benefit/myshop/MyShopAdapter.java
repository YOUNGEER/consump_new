package com.youyou.xiaofeibao.version2.home.benefit.myshop;

import android.content.Context;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.util.CircularImage;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.myshop.MyshopResponseParam;

/**
 * 作者：young on 2016/12/24 10:32
 */

public class MyShopAdapter extends CustormBaseAdapter<MyshopResponseParam> {
    public MyShopAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(MyshopResponseParam bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_name, bean.getShopName());
        CircularImage ci = (CircularImage) holder.getView(R.id.iv_img);
        ImageUtils.display(ci, bean.getDoorImg());

    }
}
