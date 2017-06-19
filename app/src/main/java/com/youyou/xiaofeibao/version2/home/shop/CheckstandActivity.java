package com.youyou.xiaofeibao.version2.home.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.zxing.ZxingUtils;
import com.google.zxing.WriterException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/14.
 * 收银台界面
 */

public class CheckstandActivity extends BaseTitleActivity {

    /**
     * 邀请码
     */
    @ViewInject(R.id.tv_inviteCode_checkstand)
    private TextView tv_inviteCode;
    /**
     * 二维码
     */
    @ViewInject(R.id.iv_checkstand_activity)
    private ImageView iv_checkstand;
    /**
     * 条形码
     */
    @ViewInject(R.id.iv_tiaoxing_activity)
    private ImageView iv_tiaoxing;
    /**
     * 当前时间
     */
    @ViewInject(R.id.tv_current_time_checkstand)
    private TextView tv_current_time;
    /**
     * 店铺名称
     */
    @ViewInject(R.id.tv_shopName_checkstand)
    private TextView tv_shopName;
    /**
     * 商家id
     */
    private String shopId;
    /**
     * 商家名称
     */
    private String shopName;

    @Override
    protected void initData() {
        super.initData();
        shopId = getIntent().getStringExtra("shopId");
        shopName = getIntent().getStringExtra("shopName");
        initvoid();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_checkstand;
    }

    @Override
    protected int getTitleText() {
        return R.string.app_name;
    }

    private void initvoid() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        int mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        int mHour = c.get(Calendar.HOUR_OF_DAY);//时
        int mMinute = c.get(Calendar.MINUTE);//分
        tv_current_time.setText(mYear + "/" + mMonth + "/" + mDay + "  " + mHour + ":" + mMinute);
        tv_shopName.setText(shopName + "收款码");
        tv_inviteCode.setText(PreferencesConfig.inviteCode.get());

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.logo_pic);

        try {
            Bitmap dCode = ZxingUtils.createCode(mActivity,"http://www.xftb168.com/web/paytomem?tomem=" + shopId,bitmap);
            if (dCode != null) {
                iv_checkstand.setImageBitmap(dCode);
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

}
