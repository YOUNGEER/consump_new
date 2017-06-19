package com.youyou.xiaofeibao;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youyou.xiaofeibao.framework.activity.BaseActivity;

import java.util.ArrayList;

/**
 * @author zhaoyunhai on 16/4/28.
 */
public class GuideActivity extends BaseActivity {
    private ArrayList<Integer> list = new ArrayList<Integer>();

    private ViewPager viewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        viewPage = (ViewPager) findViewById(R.id.viewpage);
        initData();

        viewPage.setAdapter(new GuidPageAdapter());

    }

    private void initData() {
        list.add(R.drawable.guid1);
        list.add(R.drawable.guid2);
        list.add(R.drawable.guid3);
    }

    private class GuidPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackground(GuideActivity.this.getResources().getDrawable(list.get(position)));
            container.addView(imageView);
            if(position==2){
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GuideActivity.this,MainActivity.class));
                        finish();
                    }
                });
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
}
