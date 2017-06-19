package com.youyou.xiaofeibao.version2.home.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.version2.response.citylist.CityListResponseTranData;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * 作者：young on 2016/11/14 09:39
 */

public class CityAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private List<CityListResponseTranData> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CityAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public List<CityListResponseTranData> getData() {
        return mData;
    }

    public void setData(List<CityListResponseTranData> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<CityListResponseTranData> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == CityListResponseTranData.SECTION;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            vh = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.v2_listview_citylist, null);
            vh.tv_province = (TextView) convertView.findViewById(R.id.tv_province);
            vh.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        CityListResponseTranData data = mData.get(position);
        if (data.getType() == CityListResponseTranData.SECTION) {
            vh.tv_province.setVisibility(View.VISIBLE);
            vh.tv_city.setVisibility(View.GONE);
            vh.tv_province.setText(data.getObject().getCity());

        } else {
            vh.tv_province.setVisibility(View.GONE);
            vh.tv_city.setVisibility(View.VISIBLE);
            vh.tv_city.setText(data.getObject().getCity());
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;//2种view的类型 baseAdapter中得方法
    }

    class ViewHolder {
        private TextView tv_province;
        private TextView tv_city;
    }
}
