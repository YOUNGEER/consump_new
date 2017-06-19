package com.youyou.xiaofeibao.version2.tool;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：young on 2017/2/24 16:22
 */


public class ActivityCollection {

    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);

    }

    public static void removeActivity(Activity activity){
        activities.remove(activities);
    }

}
