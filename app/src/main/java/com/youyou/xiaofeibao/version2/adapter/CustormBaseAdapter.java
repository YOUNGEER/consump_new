package com.youyou.xiaofeibao.version2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class CustormBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mList;
    private int itemLayoutId;


    public CustormBaseAdapter(Context context, int itemLayoutId) {
        this.mContext = context;
        this.itemLayoutId = itemLayoutId;
        mList = new ArrayList<T>();
    }


    public CustormBaseAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mContext = context;
        this.mList = list;
        this.itemLayoutId = itemLayoutId;
    }

    public void setList(List<T> list, int tag) {
        this.mList = list;
    }

    public void setList(List<T> list, boolean tag) {
        this.mList = list;
    }

    public void setList(List<T> list) {
        mList.clear();
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    public void clearList() {
        mList.clear();
        this.notifyDataSetChanged();
    }

    public List<T> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.newInstance(mContext, convertView,
                position, itemLayoutId);
        initView(getItem(position), holder, position);
        return holder.getmConvertView();
    }

    public abstract void initView(T bean, ViewHolder holder, int position);
}
