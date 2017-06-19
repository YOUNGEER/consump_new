package com.youyou.xiaofeibao.version2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, int position, int layoutId) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = View.inflate(context, layoutId, null);
        mConvertView.setTag(this);
    }

    public static ViewHolder newInstance(Context context, View convertView,
                                         int position, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(context, position, layoutId);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public View getmConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {

            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setTextViewText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setTextViewText(int viewId, String text, int color) {
        TextView textView = getView(viewId);
        textView.setText(text);
        textView.setTextColor(color);
        return this;
    }

    public ViewHolder setImageViewResouce(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageViewBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

}
