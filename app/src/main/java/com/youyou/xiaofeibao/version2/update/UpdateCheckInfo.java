package com.youyou.xiaofeibao.version2.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.youyou.xiaofeibao.R;


/**
 * Created by Administrator on 2016/8/18.
 */
public class UpdateCheckInfo {

    UpdateCheckInfo(Context context, String updateMessage, String url) {
        downApk(context, updateMessage, url);
    }

    private void downApk(Context context, String updateMessage, String url) {
        Log.i("wyapk", "00000000");
        showNotification(context, updateMessage, url);

    }

    /**
     * Show Notification
     */
    private void showNotification(Context context, String content, String apkUrl) {
        Intent myIntent = new Intent(context, ApkDownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = context.getApplicationInfo().icon;
        Notification notify = new NotificationCompat.Builder(context)
                .setTicker(context.getString(R.string.android_auto_update_notify_ticker))
                .setContentTitle(context.getString(R.string.android_auto_update_notify_content))
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();

        notify.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notify);
    }
}
