package com.youyou.xiaofeibao.framework.Log;


import android.os.Environment;
import android.text.TextUtils;

public class LogManager {

    public final static String FILE_PATH = "/consumption/obd";

    /**
     * 是否可以在控制台打印日志
     */
    private boolean isLog = true;
    /**
     * 是否可以打印日志到文件中
     */
    private boolean isLogFile = false;

    private String logFilePath;

    /**
     * 单例持有器
     */
    private static final class InstanceHolder {
        private static final LogManager INSTANCE = new LogManager();
    }

    /**
     * 禁止构造
     */
    private LogManager() {
    }

    /**
     * 获得单例
     */
    public static LogManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean isLog) {
        this.isLog = isLog;
    }

    public boolean isLogFile() {
        return isLogFile;
    }

    public void setLogFile(boolean isLogFile) {
        this.isLogFile = isLogFile;
    }

    public String getLogFilePath() {
        if (TextUtils.isEmpty(logFilePath)) {
            logFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_PATH + "/client_Log/";
        }
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }


}
