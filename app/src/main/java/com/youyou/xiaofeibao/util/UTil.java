package com.youyou.xiaofeibao.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UTil {

    @SuppressLint("SimpleDateFormat")
    public static String formatTime(Date date) {
        String timeString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        timeString = df.format(date);
        return timeString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTime2(Date date) {
        String timeString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        timeString = df.format(date);
        return timeString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTime3(Date date) {
        String timeString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeString = df.format(date);
        return timeString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTimeTwo(Date date) {
        String timeString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        timeString = df.format(date);
        return timeString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTimeThree(Date date) {
        String timeString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        timeString = df.format(date);
        return timeString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatTimeFour(Date date) {
        String timeString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        timeString = df.format(date);
        return timeString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTime() {
        String timeString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeString = df.format(new Date());
        return timeString;
    }

    public static String getCurrentTimeOne() {
        String timeString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        timeString = df.format(new Date());
        return timeString;
    }

    public static String getCurrentTimeTwo(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static boolean cheMobileck(String phone) {
        Pattern pattern = Pattern.compile("1[3458]\\d{9}");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    // 二次退出
    public static long TwiceBack(Context context, long firsttime) {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firsttime > 800) {// 如果两次按键时间间隔大于800毫秒，则不退出
            Toast.makeText(context, "再按一次退出程序...", Toast.LENGTH_SHORT).show();
            firsttime = secondTime;// 更新firstTime
            return firsttime;
        } else {
            /*EMChatManager.getInstance().logout(new EMCallBack() {

				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					System.out.println("--------退出失败");
				}

				@Override
				public void onProgress(int arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					System.out.println("----------退出成功");
					System.exit(0);// 否则退出程序 
				}
			});*/
//			System.exit(0);// 否则退出程序

            return 0;
        }

    }

    // 获取手机设备码
    public static String getappNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    // 将像素px转化成dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException(
                        "Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }

	
	 /*将字符串转为时间戳*/
    // private static SimpleDateFormat sf = null;
//	　　public static long getStringToDate(String time) {
//	　　sf = newSimpleDateFormat("yyyy年MM月dd日");
//	　　Date date = new Date();
//	　　try{
//	　　date = sf.parse(time);
//	　　} catch(ParseException e) {
//	　　// TODO Auto-generated catch block
//	　　e.printStackTrace();
//	　　}
//	　　return date.getTime();
//	　　}


}
