package com.youyou.xiaofeibao.version2.alliance.adapter;

import android.content.Context;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;

import java.util.List;

/**
 * 作者：young on 2016/9/7 14:22
 */
public class CustomSelectAdapter extends CustormBaseAdapter<CustomSelectBean> {

    private String content;

    public CustomSelectAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public void setList(List<CustomSelectBean> list, String content) {
        this.mList = list;
        this.content = content;
        notifyDataSetChanged();
    }


    public CustomSelectAdapter(Context context, List<CustomSelectBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void initView(CustomSelectBean bean, ViewHolder holder, int position) {

        TextView textView = holder.getView(R.id.tv_content);
        textView.setText(bean.getName());

        if (bean.getName().equals(content)) {
            textView.setTextColor(mContext.getResources().getColor(R.color.had_select_item));
        } else {
            textView.setTextColor(mContext.getResources().getColor(R.color.select_item));
        }
    }

}
