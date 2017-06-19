package com.youyou.xiaofeibao.version2.home.shop.sy;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.benefit.BenefitResponseParam;

/**
 * 作者：young on 2017/1/4 16:40
 */

public class BenefitAdapter extends CustormBaseAdapter<BenefitResponseParam> {


    public BenefitAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView(BenefitResponseParam bean, ViewHolder holder, int position) {

        int type=bean.getType();
        if(type==1){
            holder.setTextViewText(R.id.tv_money,"+"+bean.getMoney());
        }else {
            holder.setTextViewText(R.id.tv_money,"-"+bean.getMoney());
        }
        holder.setTextViewText(R.id.tv_after,bean.getAfter_money()+"")
                .setTextViewText(R.id.tv_time,bean.getCreateyear()+"-"+bean.getCreatedate()+"-"+bean.getCreatetime())
                .setTextViewText(R.id.tv_type,bean.getTitle());
    }
}
