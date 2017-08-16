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

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：young on 2017/7/13 17:57
 */

public abstract class RateSeletorUtils {

    private PopupWindow catePop;
    private RateSelectAdapter mAdapter;
    private View mView;
    private ListView listView;
    private View parent;


    public RateSeletorUtils(View parent, Context context, int layout) {
        this.parent = parent;
        mView = LayoutInflater.from(context).inflate(layout, null);
        catePop = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        catePop.setBackgroundDrawable(new BitmapDrawable());
        catePop.setOutsideTouchable(false);
        listView = (ListView) mView.findViewById(R.id.listview_pop);
        mAdapter = new RateSelectAdapter(context, R.layout.list_select_pop);
        listView.setAdapter(mAdapter);
        setData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dismissPopUpwindow();
                setAdapterItem(mAdapter.getItem(position).toString());
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

    private void setData() {
        mAdapter.clearList();
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i / 100f);
        }
        mAdapter.setList(list);
    }

    protected abstract void setAdapterItem(String str);

}
