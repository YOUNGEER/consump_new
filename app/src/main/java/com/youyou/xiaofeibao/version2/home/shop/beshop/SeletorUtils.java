package com.youyou.xiaofeibao.version2.home.shop.beshop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.youyou.xiaofeibao.R;

import java.util.List;

/**
 * 作者：young on 2017/7/13 17:57
 */

public abstract class SeletorUtils {

    private PopupWindow catePop;
    private SelectAdapters adapter;
    private View mContentView;
    private ListView listView;
    private View parent;

    public SeletorUtils(View parent, LayoutInflater inflater, Context context, int tag) {
        this.parent = parent;
        mContentView = inflater.inflate(R.layout.pop_select_money, null);
        catePop = new PopupWindow(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        catePop.setBackgroundDrawable(new BitmapDrawable());
        catePop.setOutsideTouchable(false);
        listView = (ListView) mContentView.findViewById(R.id.listview_pop);
        adapter = new SelectAdapters(context, R.layout.list_select_pop, tag);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismissPopUpwindow();
                setAdapterItem(adapter.getItem(position));
            }
        });
    }

    public void showPopUpwindow() {
        catePop.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    public void dismissPopUpwindow() {
        if (catePop.isShowing())
            catePop.dismiss();
    }

    public void setData(List categorys) {
        adapter.clearList();
        adapter.setList(categorys);
    }

    abstract void setAdapterItem(Object bean);

}
