package com.youyou.xiaofeibao.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youyou.xiaofeibao.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import java.util.List;


public class RollViewPager extends ViewPager {

    private List<View> viewList;
    //显示当前viewpager中文字集合
    private List<String> titleList;
    //需要去管理显示内容的TextView
//	private TextView top_news_title;
    //显示当前viewpager中图片的链接地址
    private List<String> imageUrlList;
    private Context context;
    private BitmapUtils bitmapUtils;
    private MyRollPagerAdapter myRollPagerAdapter;
    private int currentPosition;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //延续当前3秒一次的滚动状态
            RollViewPager.this.setCurrentItem(currentPosition);
            startRoll();
        }
    };


    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //放行(不允许拦截事件)
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                if (Math.abs(moveX - downX) > Math.abs(moveY - downY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    //夫控件可以去拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                //夫控件可以去拦截事件
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    private RunnableTask runnableTask;
    private int downX;
    private int downY;
    private onPageClick pageClick;

    public RollViewPager(Context context) {
        super(context);
        this.context = context;
    }

    public RollViewPager(Context context, final List<View> viewList, onPageClick pageClick) {
        super(context);
        this.context = context;
        this.pageClick = pageClick;
        this.viewList = viewList;

        bitmapUtils = new BitmapUtils(context);
        runnableTask = new RunnableTask();

        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                arg0 = arg0 % imageUrlList.size();
                for (int i = 0; i < viewList.size(); i++) {
                    if (i == arg0) {

                        viewList.get(i).setBackgroundResource(R.drawable.dot_focus);
                    } else {
                        viewList.get(i).setBackgroundResource(R.drawable.dot_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    //移除view调用的方法
    @Override
    protected void onDetachedFromWindow() {
        //清除当前handler维护的任务
        handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }


    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public void startRoll() {
        //滚动viewpager的方法
        if (myRollPagerAdapter == null) {
            myRollPagerAdapter = new MyRollPagerAdapter();
            this.setAdapter(myRollPagerAdapter);
            currentPosition = Integer.MAX_VALUE / 2 / imageUrlList.size() * imageUrlList.size();
            this.setCurrentItem(currentPosition);

        } else {
            myRollPagerAdapter.notifyDataSetChanged();
        }
        //滚动，按时滚动
        if (imageUrlList.size() > 1) {
            handler.postDelayed(runnableTask, 5000);
        }
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    class RunnableTask implements Runnable {
        @Override
        public void run() {
            currentPosition = RollViewPager.this.getCurrentItem() + 1;
            //发送一条空消息
            handler.obtainMessage().sendToTarget();
        }
    }

    public interface onPageClick {
        public void onclick(int position);
    }

    class MyRollPagerAdapter extends PagerAdapter {
        private int downX;
        private long downTime;
        protected static final String tag = "MyRollPagerAdapter";

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final int realPosition = position % imageUrlList.size();
            View view = View.inflate(context, R.layout.roll_viewpage_item, null);
            BitmapDisplayConfig config = new BitmapDisplayConfig();
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            //异步加载图片，并且将图片添加到指定控件上
            bitmapUtils.display(imageView, imageUrlList.get(realPosition), new BitmapLoadCallBack<View>() {


                @Override
                public void onLoadStarted(View container, String uri, BitmapDisplayConfig config) {
                    container.setBackgroundDrawable(context.getResources().getDrawable((R.drawable.ic_launcher01)));
                }

                @Override
                public void onLoadCompleted(View container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                    ((ImageView) container).setImageBitmap(bitmap);
                }

                @Override
                public void onLoadFailed(View container, String uri, Drawable drawable) {
                    container.setBackgroundDrawable(context.getResources().getDrawable((R.drawable.ic_launcher01)));
                }
            });
//			bitmapUtils.display(imageView, imageUrlList.get(realPosition));
            ((RollViewPager) container).addView(view);

            view.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
//						handler.removeCallbacksAndMessages(null);
                            downTime = System.currentTimeMillis();
                            downX = (int) event.getX();
                            break;
                        case MotionEvent.ACTION_UP:
//						startRoll();  TODO 不知为何滚动不走up事件，只能将清除滚动的handler取消掉  （暂时）
                            pageClick.onclick(realPosition);
                            if ((System.currentTimeMillis() - downTime) < 500 && downX < (event.getX() + 5) && downX > (event.getX() - 5)) {
                                //触发点击事件(回调)
                                Log.i(tag, "点击事件被触发");
                            }
                            break;
                    }
                    return true;
                }
            });

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            position = position % imageUrlList.size();
            ((RollViewPager) container).removeView((View) object);
        }
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }
}
