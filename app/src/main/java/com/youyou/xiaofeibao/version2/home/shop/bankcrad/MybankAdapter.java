package com.youyou.xiaofeibao.version2.home.shop.bankcrad;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.mybank.MybankResponselist;

/**
 * 作者：young on 2016/12/27 17:00
 */

public class MybankAdapter extends CustormBaseAdapter<MybankResponselist> {
    public MybankAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(MybankResponselist bean, ViewHolder holder, final int position) {
        holder.setTextViewText(R.id.tv_name, bean.getBankname());
        String num = bean.getBankno();
        TextView textView = (TextView) holder.getView(R.id.tv_num);
        if (null != num && !"".equals(num) && num.length() > 4) {
            String sub = num.subSequence(num.length() - 3, num.length()).toString();
            textView.setText("银行卡 **** **** **** **** " + sub);
        }

        ImageView imageView = (ImageView) holder.getView(R.id.iv_delete);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListner != null) {
                    mListner.delete(position);
                }
            }
        });
    }

    private DeleteListner mListner;

    public DeleteListner getListner() {
        return mListner;
    }

    public void setListner(DeleteListner listner) {
        mListner = listner;
    }

    interface DeleteListner {
        void delete(int pos);
    }

}
