package com.youyou.xiaofeibao.version2.home.shop.beshop;

import android.content.Context;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.response.allcategory.AddressTypeList;
import com.youyou.xiaofeibao.version2.response.allcategory.BusinessList;
import com.youyou.xiaofeibao.version2.response.allcategory.CategoryList;
import com.youyou.xiaofeibao.version2.response.allcategory.ContactTypeList;

import java.util.List;

/**
 * 作者：young on 2017/7/14 10:40
 */

public class SelectAdapters extends CustormBaseAdapter<Object> {
    private int mtag;

    public SelectAdapters(Context context, int itemLayoutId, int tag) {
        super(context, itemLayoutId);
        mtag = tag;
    }

    public SelectAdapters(Context context, List<Object> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void initView(Object bean, ViewHolder holder, int position) {
        String str = "";
        TextView textView = holder.getView(R.id.tv_content);
        switch (mtag) {
            case 0:
                AddressTypeList data = ((AddressTypeList) bean);
                str = data.getVal();
                break;
            case 1:
                BusinessList data1 = ((BusinessList) bean);
                str = data1.getLicenseName();
                break;
            case 2:
                ContactTypeList data2 = ((ContactTypeList) bean);
                str = data2.getTypeName();
                break;
            case 3:
                CategoryList data3 = ((CategoryList) bean);
                str = data3.getName();
                break;

        }

        textView.setText(str);
    }
}
