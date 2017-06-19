package com.youyou.xiaofeibao.version2.tool;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * 作者：young on 2017/3/22 12:32
 */

public class AppModule implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        builder.setDiskCache();//自定义磁盘缓存
//
//        builder.setMemoryCache();//自定义内存缓存
//
//        builder.setBitmapPool(); //自定义图片池
//
//        builder.setDiskCacheService();//自定义本地缓存的线程池
//
//        builder.setResizeService();//自定义核心处理的线程池
//
//        builder.setDecodeFormat();//自定义图片质量

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
