package com.youyou.xiaofeibao.version2.home.history;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.version2.response.browse.BrowseResponseParam;
import com.youyou.xiaofeibao.version2.response.browse.BrowseResponseTransData;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * 作者：young on 2016/11/14 09:39
 */

public class HistoryAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private List<BrowseResponseTransData> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public HistoryAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public List<BrowseResponseTransData> getData() {
        return mData;
    }

    public void setData(List<BrowseResponseTransData> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<BrowseResponseTransData> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == BrowseResponseTransData.SECTION;
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
            convertView = mLayoutInflater.inflate(R.layout.v2_listivew_browse_item, null);
            vh.ll_title = (LinearLayout) convertView.findViewById(R.id.ll_title);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);
            vh.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            vh.tv_addr = (TextView) convertView.findViewById(R.id.tv_addr);
            vh.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        BrowseResponseTransData data = mData.get(position);
        if (data.getType() == BrowseResponseTransData.SECTION) {
            vh.ll_title.setVisibility(View.VISIBLE);
            vh.ll_content.setVisibility(View.GONE);
            vh.tv_title.setText(data.getObject().getShopName());

        } else {
            vh.ll_title.setVisibility(View.GONE);
            vh.ll_content.setVisibility(View.VISIBLE);
            BrowseResponseParam param = data.getObject();
            ImageUtils.display(vh.iv_img, param.getDoorImg());
            vh.tv_name.setText(param.getShopName());
            vh.tv_addr.setText(param.getAddr());
            vh.tv_distance.setText(param.getBrowseDate());
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;//2种view的类型 baseAdapter中得方法
    }

    class ViewHolder {
        private LinearLayout ll_title;
        private TextView tv_title;
        private LinearLayout ll_content;
        private ImageView iv_img;
        private TextView tv_name;
        private TextView tv_addr;
        private TextView tv_distance;
    }
}
