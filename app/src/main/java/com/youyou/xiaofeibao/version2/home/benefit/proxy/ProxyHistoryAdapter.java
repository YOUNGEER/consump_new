package com.youyou.xiaofeibao.version2.home.benefit.proxy;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.proxyhistory.ProxyHistoryResponseParam;

/**
 * 作者：young on 2017/1/4 16:40
 */

public class ProxyHistoryAdapter extends CustormBaseAdapter<ProxyHistoryResponseParam> {


    public ProxyHistoryAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView(ProxyHistoryResponseParam bean, ViewHolder holder, int position) {

        TextView textView=holder.getView(R.id.tv_type);
        holder.setTextViewText(R.id.tv_name,bean.getBankname()+"    "+bean.getBankno())
                .setTextViewText(R.id.tv_time,bean.getCreatetime())
        .setTextViewText(R.id.tv_money,bean.getMoney());

        String Status=bean.getStatus();
        if("1".equals(Status)){
            textView.setText("提现完成");
        }
        else if("0".equals(Status)){
            textView.setText("等待审核");
        }else if("-1".equals(Status)){
            textView.setText("审核未通过");
        }
    }
}
