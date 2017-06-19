package com.youyou.xiaofeibao.version2.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youyou.xiaofeibao.MainActivity;
import com.youyou.xiaofeibao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lijian@joyintech.com">lianghong</a>
 * @Description:首页图片广告view
 * @Create on 2014-11-21 下午12:56:58
 */
public class BannerView extends LinearLayout {
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    public List<View> views;
    // 底部小点图片
    private ImageView[] dots;
    // 记录当前选中位置
    private int currentIndex;
    Context context;
    private Runnable viewpagerRunnable;

    private static Handler handler;
    private LinearLayout mNumLayout;
    private int mPageSize;

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    public BannerView(Context context) {
        super(context);
        this.context = context;
    }

    public void setpageSize(int size) {
        if(size==0){
            return;
        }
        //需要显示的页数
        mPageSize = size;

        if (handler == null) {
            handler = new Handler();
        }

        // 初始化页面
        initViews();
        // 初始化底部小点
        initDots();

        // 添加事件监听
        initListener();

        // 开启自动切换图片
        initRunnable();

    }

    public BannerView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // 导入布局
        LayoutInflater.from(context).inflate(R.layout.banner_view, this, true);
    }


    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(context);
        views = new ArrayList<View>();
        mNumLayout = (LinearLayout) findViewById(R.id.ll);
        mNumLayout.removeAllViews();

        for (int i = 0; i < mPageSize; i++) {
            //动态添加页和小圆点
            views.add(inflater.inflate(R.layout.helppay1, null));
            ImageView iv = new ImageView(context);
            iv.setPadding(10, 10, 10, 10);
            iv.setImageResource(R.drawable.bg_dot);
            mNumLayout.addView(iv);
        }
        vpAdapter = new ViewPagerAdapter(views, (MainActivity) context);
        vp = (ViewPager) findViewById(R.id.bannerPager);
        vp.setAdapter(vpAdapter);

    }

    private void initDots() {
        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) mNumLayout.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;

    }

    /**
     * 添加事件监听
     */
    private void initListener() {
        vp.setOnPageChangeListener(new OnPageChangeListener() {
            boolean isScrolled = false;

            @Override
            public void onPageScrollStateChanged(int status) {
                switch (status) {
                    case 1:// 手势滑动
                        isScrolled = false;
                        break;
                    case 2:// 界面切换
                        isScrolled = true;
                        break;
                    case 0:// 滑动结束

                        // 当前为最后一张，此时从右向左滑，则切换到第一张
                        if (vp.getCurrentItem() == vp.getAdapter()
                                .getCount() - 1 && !isScrolled) {
                            vp.setCurrentItem(0);
                        }
                        // 当前为第一张，此时从左向右滑，则切换到最后一张
                        else if (vp.getCurrentItem() == 0 && !isScrolled) {
                            vp.setCurrentItem(vp.getAdapter()
                                    .getCount() - 1);
                        }
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // 设置底部小点选中状态
                setCurrentDot(arg0);
            }

            @Override
            public void onPageSelected(int index) {

            }
        });
    }

    private static final int TIME = 3500;

    /**
     * 定时切换
     */
    protected void initRunnable() {
        if (viewpagerRunnable != null) {
            handler.removeCallbacks(viewpagerRunnable);
        }
        viewpagerRunnable = new Runnable() {
            @Override
            public void run() {
                int nowIndex = vp.getCurrentItem();
                int count = vp.getAdapter().getCount();
                // 如果下一张的索引大于最后一张，则切换到第一张
                if (nowIndex + 1 >= count) {
                    vp.setCurrentItem(0);
                } else {
                    vp.setCurrentItem(nowIndex + 1);
                }
                handler.postDelayed(viewpagerRunnable, TIME);
            }
        };
        handler.postDelayed(viewpagerRunnable, TIME);
    }

}
