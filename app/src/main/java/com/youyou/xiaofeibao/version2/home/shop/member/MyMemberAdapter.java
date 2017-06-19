package com.youyou.xiaofeibao.version2.home.shop.member;

import android.content.Context;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.util.CircularImage;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.mymember.MymemberResponseParam;

/**
 * 作者：young on 2016/12/22 20:34
 */

public class MyMemberAdapter extends CustormBaseAdapter<MymemberResponseParam> {
    public MyMemberAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(MymemberResponseParam bean, ViewHolder holder, int position) {
        CircularImage iv = (CircularImage) holder.getView(R.id.iv_img);
        ImageUtils.display(iv, bean.getImgUrl());
        holder.setTextViewText(R.id.tv_name, "会员姓名：" + bean.getLoginName())
                .setTextViewText(R.id.tv_phone, "会员号码：" + bean.getPhone());

    }
}
