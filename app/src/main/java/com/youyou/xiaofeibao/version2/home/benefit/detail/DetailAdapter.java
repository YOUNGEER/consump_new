package com.youyou.xiaofeibao.version2.home.benefit.detail;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.util.LogUtils;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.detail.DetailResponseParam;

/**
 * 作者：young on 2016/12/24 11:34
 */

public class DetailAdapter extends CustormBaseAdapter<DetailResponseParam> {
    public DetailAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }


    @Override
    public void initView(DetailResponseParam bean, ViewHolder holder, int position) {
        ImageView iv=(ImageView)holder.getView(R.id.iv_img);

        int type=bean.getType();

        String money=bean.getMoney()+"";
        Log.i("isdoblessd",money);

        if(type==1){
            iv.setBackground(mContext.getResources().getDrawable(R.drawable.shou));
            holder.setTextViewText(R.id.tv_money,"+"+money);
        }else {
            iv.setBackground(mContext.getResources().getDrawable(R.drawable.zhi));
            holder.setTextViewText(R.id.tv_money,"-"+money);
        }

        holder.setTextViewText(R.id.tv_date,bean.getCreatedate())
                .setTextViewText(R.id.tv_time,bean.getCreatetime())
                .setTextViewText(R.id.tv_title,bean.getTitle());
    }
}
