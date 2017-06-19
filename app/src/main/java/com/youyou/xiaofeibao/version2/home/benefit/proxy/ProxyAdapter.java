package com.youyou.xiaofeibao.version2.home.benefit.proxy;

import android.content.Context;
import android.widget.ImageView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.zhaoshang.MyRecommendProxyListBean;

/**
 * 作者：young on 2017/5/25 14:46
 */

public class ProxyAdapter extends CustormBaseAdapter<MyRecommendProxyListBean> {


    public ProxyAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(MyRecommendProxyListBean bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_type,bean.getAgentType())
                .setTextViewText(R.id.tv_name,bean.getName());
        ImageUtils.display((ImageView) holder.getView(R.id.iv_img),bean.getImgUrl());
    }
}
