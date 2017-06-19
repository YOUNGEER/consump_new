package com.youyou.xiaofeibao.version2.tool;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/29.
 */
public class SeletetimeUtils {

   public interface GetSeleteTimeListener {
      void seleteTimeListener(DatePicker view, int year, int monthOfYear, int dayOfMonth);

   }

   private GetSeleteTimeListener getSeleteTimeListener;
   private DateDialog dialog;

   public void getSeleteTime(Context mActivity, final GetSeleteTimeListener getSeleteTimeListener,int year,int month,int day) {
//      int year;
//      int month;
//      int day;
//      this.getSeleteTimeListener = getSeleteTimeListener;
//      Calendar c = Calendar.getInstance();
//      year = c.get(Calendar.YEAR);
//      month = c.get(Calendar.MONTH);
//      day = c.get(Calendar.DAY_OF_MONTH);

      dialog = new DateDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            dialog.dismiss();
            getSeleteTimeListener.seleteTimeListener(view, year, monthOfYear + 1, dayOfMonth);
         }
      }, year, month-1, day);
      dialog.show();

//      DatePickerDialog dialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
//
//         @Override
//         public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//             getSeleteTimeListener.seleteTimeListener(view, year, monthOfYear + 1, dayOfMonth);
//         }
//
//      }, year, month, day);
//
//      dialog.show();
   }


   private class DateDialog extends DatePickerDialog {
      public DateDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
         super(context, callBack, year, monthOfYear, dayOfMonth);
      }

      @Override
      protected void onStop() {

      }
   }

   private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   /**
    * 日期逻辑
    *
    * @return
    */
   public static String timeLogic(String dateStr) {
      Calendar calendar1 = Calendar.getInstance();
      long now = calendar1.getTimeInMillis();//当前时间
      Calendar calendar2 = Calendar.getInstance();//用来保存时间
      Date date = null;
      try {
         date = sdf.parse(dateStr);
         calendar2.setTime(date);
      } catch (ParseException e) {
         return dateStr;
      }
      long past = calendar2.getTimeInMillis();
      // 相差的秒数
      long time = (now - past) / 1000;
      StringBuffer sb = new StringBuffer();
      if (time > 0 && time < 60) { // 1mins
         return sb.append(time + "秒前").toString();
      } else if (time > 60 && time < 3600) {
         return sb.append(time / 60 + "分钟前").toString();
      } else if (time >= 3600 && time < 3600 * 24) {
         return sb.append(time / 3600 + "小时前").toString();
      } else if (time >= 3600 * 24 && time < 3600 * 48) {
         return sb.append("昨天").toString();
      } else if (time >= 3600 * 48 && time < 3600 * 72) {
         return sb.append("前天").toString();
      } else {
         return sb.append(time / 24 / 3600).append("天前").toString();
      }
   }

}
