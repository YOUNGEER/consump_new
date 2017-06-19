package com.youyou.xiaofeibao;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.youyou.xiaofeibao.version2.home.shop.sy.BenefitActivity;
import com.youyou.xiaofeibao.version2.xunfei.VoiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/7/8.
 */
public class MyPushReceiver extends BroadcastReceiver {


    private String type = "-1";
    private String id = "";
    private JSONObject jsonObject = null;
    private String msg = "";

    private String message;

    @Override
    public void onReceive(Context context, Intent intent) {
//      if (helper == null) {
//         helper = new MsgSqliteHelper(context);
//      }

        Bundle bundle = intent.getExtras();
        message = bundle.getString(JPushInterface.EXTRA_EXTRA, "");

        Log.i("ssssssssssssOnReceiver", message);
        if (!message.equals("")) {
            try {
                jsonObject = new JSONObject(message);
                type = jsonObject.getString("type");
                msg = jsonObject.getString("msg");
            } catch (JSONException e) {
                jsonObject = null;
                type = "-1";
            }
        }

        //收到了通知
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//         play();
            try {
                VoiceUtils.getInstance().initmTts(context, msg);
            } catch (Exception e) {
                Log.i("sfdsfasdfas", e.toString());
            }
//

        }
        //点击了通知
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

            if (type.equals("1")) {
                Intent intent1 = new Intent(new Intent(context, BenefitActivity.class));
                intent1.putExtra("code", getAppSatus(context));
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);

            }
        }

    }

    public int getAppSatus(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals("com.doumee.pharmacy")) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals("com.doumee.pharmacy")) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }

    private MediaPlayer mp = null;

    private void play() {
        if (null == mp) {
            mp = new MediaPlayer();
        }
        mp.reset();
        AssetFileDescriptor file = ConsumApplication.getInstance().getResources().openRawResourceFd(R.raw.coun);
        try {
            mp.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                    file.getLength());
            mp.prepare();
            file.close();
            mp.setVolume(0.5f, 0.5f);
            mp.setLooping(false);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
            relasePlay();
        }
    }

    private void relasePlay() {
        if (null == mp) {
            return;
        }
        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
        mp = null;
    }
}
