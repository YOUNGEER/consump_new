package com.youyou.xiaofeibao.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youyou.xiaofeibao.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;


/**
 * @author zhaoyunhai on 16/3/28.
 */
public class ImageUtils {


    private ImageUtils() {
    }

    private static BitmapUtils bitmapUtils;
    private static BitmapUtils bitmapUtils2;
    private static Context mContext;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param appContext application context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context appContext) {
        mContext=appContext;
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext);
            bitmapUtils.configDefaultLoadFailedImage(appContext.getResources().getDrawable(R.drawable.ic_launcher01));
            //6,设置disk缓存开启,图片会缓存到sd卡,即DiskLruCache
            bitmapUtils.configDiskCacheEnabled(true);
            //7,设置Memory缓存开启,即LruCache
            bitmapUtils.configMemoryCacheEnabled(true);
        }
        return bitmapUtils;
    }

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param appContext application context
     * @return
     */
    public static BitmapUtils getBitmapUtils2(Context appContext) {
        if (bitmapUtils2 == null) {
            bitmapUtils2 = new BitmapUtils(appContext);
            bitmapUtils2.configDefaultLoadFailedImage(appContext.getResources().getDrawable(R.drawable.partner_default));
            //6,设置disk缓存开启,图片会缓存到sd卡,即DiskLruCache
            bitmapUtils2.configDiskCacheEnabled(true);
            //7,设置Memory缓存开启,即LruCache
            bitmapUtils2.configMemoryCacheEnabled(true);
        }
        return bitmapUtils2;
    }

    /**
     * @param imageView
     * @param url
     * @see #display(ImageView, String, boolean)
     */
    public static void display(ImageView imageView, String url) {
//        display(imageView, url, false);
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.ic_launcher01)
                .crossFade(200)
                .error(R.drawable.ic_launcher01)
                .into(imageView);
    }


    public static void display(ImageView imageView, String url, BitmapLoadCallBack<ImageView> callBack) {
        bitmapUtils.display(imageView, url, callBack);
    }

    public static void displayBackground(ImageView imageView, String url) {
        bitmapUtils.display(imageView, url, new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }

            @Override
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {
                imageView.setBackground(GlobalConfig.getAppContext().getResources().getDrawable(R.drawable.ic_launcher01));
            }
        });
    }

    /**
     * @param imageView
     * @param url
     * @param isCitcle  是否显示圆角
     */
    public static void display(ImageView imageView, String url, boolean isCitcle) {
        if (isCitcle) {
            bitmapUtils.display(imageView, url, new BitmapLoadCallBack<View>() {
                @Override
                public void onLoadStarted(View container, String uri, BitmapDisplayConfig config) {
//                    ((ImageView) container).setImageBitmap(ImageTools.toRoundBitmap(BitmapFactory.decodeResource(GlobalConfig.getAppContext().getResources(),R.drawable.ic_launcher02)));
                }

                @Override
                public void onLoadCompleted(View container, String uri,
                                            Bitmap bitmap, BitmapDisplayConfig config,
                                            BitmapLoadFrom from) {
                    // TODO 自动生成的方法存根
                    ((ImageView) container).setImageBitmap(ImageTools.toRoundBitmap(bitmap));
                }

                @Override
                public void onLoadFailed(View container, String uri,
                                         Drawable drawable) {
                    ((ImageView) container).setImageBitmap(ImageTools.toRoundBitmap(BitmapFactory.decodeResource(GlobalConfig.getAppContext().getResources(), R.drawable.ic_launcher02)));

                }
            });
        } else {
            bitmapUtils.display(imageView, url, new BitmapLoadCallBack<ImageView>() {
                @Override
                public void onLoadStarted(ImageView container, String uri, BitmapDisplayConfig config) {
//                    container.setImageDrawable(GlobalConfig.getAppContext().getResources().getDrawable(R.drawable.ic_launcher01));
                }

                @Override
                public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {
                    imageView.setImageDrawable(GlobalConfig.getAppContext().getResources().getDrawable(R.drawable.ic_launcher01));
                }
            });
        }


    }
}
