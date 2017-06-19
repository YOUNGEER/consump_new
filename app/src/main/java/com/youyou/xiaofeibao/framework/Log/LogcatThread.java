package com.youyou.xiaofeibao.framework.Log;

import android.os.Environment;

import com.youyou.xiaofeibao.common.TimeUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class LogcatThread extends Thread {

    /**
     * 是否开启DEBUG模式
     */
    private static LogcatThread LOGCAT_THREAD;

    private FileWriter mFileWriter;

    public static LogcatThread getIntance() {
        if (LOGCAT_THREAD == null) {
            LOGCAT_THREAD = new LogcatThread();
        }
        return LOGCAT_THREAD;
    }


    public LogcatThread() {

    }

    @Override
    public void run() {

        long threadStart = System.currentTimeMillis();
        Process process;
        InputStream inputstream;
        BufferedReader bufferedreader;
        try {
            Runtime.getRuntime().exec("logcat -c");
            process = Runtime.getRuntime().exec("logcat -v time");//"logcat -v time -b radio"
            inputstream = process.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(
                    inputstream);
            bufferedreader = new BufferedReader(inputstreamreader);
            String str = "";

            while ((str = bufferedreader.readLine()) != null) {

                if (null == mFileWriter) {
                    mFileWriter = saveFile(threadStart);
                }
                if (null != mFileWriter) {
                    mFileWriter.write(str);
                    mFileWriter.write("\n");
                    mFileWriter.flush();
                }

                // 线程运行10分钟自动销毁
                if (System.currentTimeMillis() - threadStart > 600000) {
                    break;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private FileWriter saveFile(long ms) {
        File logPath = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + LogManager.FILE_PATH + "/logcat/");
        if (!logPath.exists()) {
            logPath.mkdirs();
        }

        String filename;
        try {
            filename = URLEncoder.encode(TimeUtils.getmDateYYYYMMDD4(ms), "UTF-8") + ".txt";
            File logFile = new File(logPath, filename);
            try {
                FileWriter fileWriter = new FileWriter(logFile, true);
                return fileWriter;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;


    }


}

