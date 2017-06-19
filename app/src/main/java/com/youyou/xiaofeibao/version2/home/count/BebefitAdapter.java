package com.youyou.xiaofeibao.version2.home.count;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.mycount.MycountResponseParam;

/**
 * 作者：young on 2016/12/8 15:18
 */

public class BebefitAdapter extends CustormBaseAdapter<MycountResponseParam> {
    public BebefitAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView(MycountResponseParam bean, ViewHolder holder, int position) {
        ImageView iv = (ImageView) holder.getView(R.id.iv_img);

        int type = bean.getType();
        if (type == 1) {
            iv.setBackground(mContext.getResources().getDrawable(R.drawable.shou));
            holder.setTextViewText(R.id.tv_money, "+" + bean.getGoldnum());
        } else {
            iv.setBackground(mContext.getResources().getDrawable(R.drawable.zhi));
            holder.setTextViewText(R.id.tv_money, "-" + bean.getGoldnum());
        }
        holder.setTextViewText(R.id.tv_date, bean.getCreatedate())
                .setTextViewText(R.id.tv_time, bean.getCreatetime())
                .setTextViewText(R.id.tv_title, bean.getTitle());
    }
}
