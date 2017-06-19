package com.youyou.xiaofeibao.version2.home.shop.store;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.youyou.xiaofeibao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyunhai on 16/4/12.
 */
public class MapAdressAdapter extends BaseAdapter {
    private final Context mContext;
    private List<PoiInfo> mList = new ArrayList<>();

    public MapAdressAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(mContext, R.layout.item_map_address, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name_itemAddress);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address_itemAddress);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_name.setText(mList.get(position).name);
        holder.tv_address.setText(mList.get(position).address);
        return convertView;
    }

    public void setData(List<PoiInfo> allPoi) {
        if (allPoi == null) {
            return;
        }
        this.mList.clear();
        mList.addAll(allPoi);
        this.notifyDataSetChanged();
    }

    private class Holder {

        public TextView tv_name;
        public TextView tv_address;
    }

    public List<PoiInfo> getmList() {
        return mList;
    }
}
