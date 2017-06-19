package com.youyou.xiaofeibao.framework.Log;


import com.youyou.xiaofeibao.common.TimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LogDoumee {


    public enum CodeLocationStyle {

        /**
         * 第一行
         */
        FIRST(false, true),
        /**
         * 随后的行
         */
        SUBSEQUENT(true, false);

        /**
         * 是否添加at字眼在行首
         */
        private boolean isAt;

        /**
         * 是否使用简单类名
         */
        private boolean isSimpleClassName;

        private CodeLocationStyle(boolean isAt, boolean isSimpleClassName) {
            this.isAt = isAt;
            this.isSimpleClassName = isSimpleClassName;
        }

        /**
         * @return the {@link #isAt}
         */
        public boolean isAt() {
            return isAt;
        }

        /**
         * @return the {@link #isSimpleClassName}
         */
        public boolean isSimpleClassName() {
            return isSimpleClassName;
        }

    }

    /**
     * 日志级别
     */
    public static final int VERBOSE = 1;
    /**
     * 日志级别
     */
    public static final int DEBUG = 2;
    /**
     * 日志级别
     */
    public static final int INFO = 3;
    /**
     * 日志级别
     */
    public static final int WARN = 4;
    /**
     * 日志级别
     */
    public static final int ERROR = 5;


    /**
     * 时间格式
     */
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    private static void smartPrint(int level, LogTagInterface tag, String msg, Throwable t, boolean printStackTrace) {

        if (!isLoggable(tag, level)) {
            return;
        }

        Thread currentThread = Thread.currentThread();

        StackTraceElement[] stackTrace = currentThread.getStackTrace();

        int i = 4;

        StringBuilder sb = new StringBuilder();
        sb.append(currentThread.getId())//
                .append("|")//
                .append(getCodeLocation(CodeLocationStyle.FIRST, null, stackTrace[i]))//
                .append("|")//
                .append(msg);

        String msgResult = sb.toString();
        print(level, tag, msgResult, t);
        filePrint(level, tag, msgResult, t);

        i++;

        for (; printStackTrace && i < stackTrace.length; i++) {
            String s = getCodeLocation(CodeLocationStyle.SUBSEQUENT, currentThread, stackTrace[i]).toString();
            print(level, tag, s, null);
            filePrint(level, tag, s, null);
        }

    }

    private static void saveFile(String aContext) {
        File logPath = new File(LogManager.getInstance().getLogFilePath());
        if (!logPath.exists()) {
            logPath.mkdir();
        }
        if (checkLogFileNumber()) {
            File logFile = null;
            FileWriter fw = null;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            try {
                String fileName = "/log_" + format.format(c.getTime())
                        + ".txt";
                logFile = new File(LogManager.getInstance().getLogFilePath() + fileName);

                fw = new FileWriter(logFile);
                fw.write(aContext);

            } catch (IOException e) {
            } finally {
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    private static boolean checkLogFileNumber() {
        File logPath = new File(LogManager.getInstance().getLogFilePath());
        String[] fileList = logPath.list();
        File oldestFile = null;
        while (true) {
            if (fileList.length > 4) {
                for (int i = 0; i < fileList.length; i++) {
                    if (oldestFile == null) {
                        oldestFile = new File(LogManager.getInstance().getLogFilePath()
                                + fileList[i]);
                    } else {
                        if (oldestFile.lastModified() > new File(LogManager.getInstance().getLogFilePath()
                                + fileList[i]).lastModified()) {
                            oldestFile = new File(LogManager.getInstance().getLogFilePath()
                                    + fileList[i]);
                        }
                    }
                }
                if (oldestFile != null && oldestFile.exists()) {
                    if (!oldestFile.delete()) {
                        return false;
                    }
                }
                oldestFile = null;
            }
            fileList = logPath.list();
            if (fileList.length <= 4) {
                return true;
            }
        }


    }

    private static void print(int level, LogTagInterface tag, String msg, Throwable t) {
        String tagName;
        if (tag == null) {
            tagName = Thread.currentThread().getStackTrace()[4].getFileName().replace(".java", "");
        } else {
            tagName = tag.getTagName();
        }
        switch (level) {
            case VERBOSE:
                if (t != null) {
                    android.util.Log.v(tagName, msg, t);
                } else {
                    android.util.Log.v(tagName, msg);
                }
                break;
            case DEBUG:
                if (t != null) {
                    android.util.Log.d(tagName, msg, t);
                } else {
                    android.util.Log.d(tagName, msg);
                }
                break;
            case INFO:
                if (t != null) {
                    android.util.Log.i(tagName, msg, t);
                } else {
                    android.util.Log.i(tagName, msg);
                }
                break;
            case WARN:
                if (t != null) {
                    android.util.Log.w(tagName, msg, t);
                } else {
                    android.util.Log.w(tagName, msg);
                }
                break;
            case ERROR:
                if (t != null) {
                    android.util.Log.e(tagName, msg, t);
                } else {
                    android.util.Log.e(tagName, msg);
                }
                break;
        }
    }

    private static StringBuilder getCodeLocation(CodeLocationStyle style, Thread currentThread, StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        int lineNumber = stackTraceElement.getLineNumber();
        String methodName = stackTraceElement.getMethodName();
        String fileName = stackTraceElement.getFileName();
        StringBuilder sb = new StringBuilder();
        if (style.isAt()) {
            sb.append("	at ");
        }
        if (style.isSimpleClassName()) {
            sb.append(getSimpleName(className));
        } else {
            sb.append(className);
        }
        sb.append(".").append(methodName).append("(").append(fileName).append(":").append(lineNumber).append(")");
        return sb;
    }

    private static String getSimpleName(String className) {
        String[] split = className.split("\\.");
        return split[split.length - 1];
    }

    /**
     * 是否可以打印日志
     *
     * @param tag
     * @param level
     * @return
     */
    public static boolean isLoggable(String tag, int level) {
        return isLoggable(new LogCustomTag(tag), level);
    }

    /**
     * 是否可以打印日志
     *
     * @param tag
     * @param level
     * @return
     */
    public static boolean isLoggable(LogTagInterface tag, int level) {
        return tag instanceof LogTag && LogManager.getInstance().isLog() && ((LogTag) tag).isFlag();
    }

    /**
     * 是否可以打印日志到文件
     *
     * @param tag
     * @param level
     * @return
     */
//	public static boolean isFileLoggable(String tag, int level) {
//		return isFileLoggable(new LogCustomTag(tag), level);
//	}

    /**
     * 是否可以打印日志到文件
     *
     * @param tag
     * @param level
     * @return
     */
//	public static boolean isFileLoggable(LogTagInterface tag, int level) {
//		return LogManager.getInstance().isLogFile();
//	}
    public static String levelName(int level) {
        switch (level) {
            case VERBOSE:
                return "VERBOSE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            default:
                return "DEFAULT";
        }
    }

    public static synchronized void filePrint(int level, LogTagInterface tag, String msg, Throwable t) {

        if (!isLoggable(tag, level)) {
            return;
        }

        File logFile = null;
        PrintWriter printWriter = null;
        try {
            String fileName = "/client_normal.txt";
            if (new File(LogManager.getInstance().getLogFilePath()).mkdirs()) ;
            logFile = new File(LogManager.getInstance().getLogFilePath() + fileName);
            printWriter = new PrintWriter(new FileOutputStream(logFile, true));

            // 级别
            printWriter.print("|");
            printWriter.print(levelName(level));

            // 时间
            printWriter.print("|");
            printWriter.print(TimeUtils.getDateHHMMssSSS(TimeUtils.getCurrentTime()));

            // TAG
            printWriter.print("|");
            if (tag == null) {
                String replace = Thread.currentThread().getStackTrace()[4].getFileName().replace(".java", "");
                printWriter.print(replace);
            } else {
                printWriter.print(tag);
            }

            // 信息
            printWriter.print("|");
            printWriter.print(msg);

            // 异常
            if (null != t) {
                t.printStackTrace(printWriter);
            }

            printWriter.println();
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
    }


    public static void v(String msg) {
        smartPrint(VERBOSE, null, msg, null, false);
    }

    public static void vs(String msg) {
        smartPrint(VERBOSE, null, msg, null, true);
    }

    @Deprecated
    public static void v(String tag, String msg) {
        smartPrint(VERBOSE, new LogCustomTag(tag), msg, null, false);
    }

    public static void v(LogTagInterface tag, String msg) {
        smartPrint(VERBOSE, tag, msg, null, false);
    }

    public static void v(LogTagInterface tag, String msg, Throwable t) {
        smartPrint(VERBOSE, tag, msg, t, false);
    }

    public static void vs(LogTagInterface tag, String msg) {
        smartPrint(VERBOSE, tag, msg, null, true);
    }

    public static void d(String msg) {
        smartPrint(DEBUG, null, msg, null, false);
    }

    public static void ds(String msg) {
        smartPrint(DEBUG, null, msg, null, true);
    }

    @Deprecated
    public static void d(String tag, String msg) {
        smartPrint(DEBUG, new LogCustomTag(tag), msg, null, false);
    }

    public static void d(LogTagInterface tag, String msg) {
        smartPrint(DEBUG, tag, msg, null, false);
    }

    public static void d(LogTagInterface tag, String msg, Throwable t) {
        smartPrint(DEBUG, tag, msg, t, false);
    }

    public static void ds(LogTagInterface tag, String msg) {
        smartPrint(DEBUG, tag, msg, null, true);
    }

    public static void i(String msg) {
        smartPrint(INFO, null, msg, null, false);
    }

    public static void is(String msg) {
        smartPrint(INFO, null, msg, null, true);
    }

    @Deprecated
    public static void i(String tag, String msg) {
        smartPrint(INFO, new LogCustomTag(tag), msg, null, false);
    }

    public static void i(LogTagInterface tag, String msg) {
        smartPrint(INFO, tag, msg, null, false);
    }

    public static void i(LogTagInterface tag, String msg, Throwable t) {
        smartPrint(INFO, tag, msg, t, false);
    }

    public static void is(LogTagInterface tag, String msg) {
        smartPrint(INFO, tag, msg, null, true);
    }

    public static void w(String msg) {
        smartPrint(WARN, null, msg, null, false);
    }

    public static void ws(String msg) {
        smartPrint(WARN, null, msg, null, true);
    }

    @Deprecated
    public static void w(String tag, String msg) {
        smartPrint(WARN, new LogCustomTag(tag), msg, null, false);
    }

    public static void w(LogTagInterface tag, String msg) {
        smartPrint(WARN, tag, msg, null, false);
    }

    public static void w(LogTagInterface tag, String msg, Throwable t) {
        smartPrint(WARN, tag, msg, t, false);
    }

    public static void ws(LogTagInterface tag, String msg) {
        smartPrint(WARN, tag, msg, null, true);
    }

    public static void e(String msg) {
        smartPrint(ERROR, null, msg, null, false);
    }

    public static void es(String msg) {
        smartPrint(ERROR, null, msg, null, true);
    }

    @Deprecated
    public static void e(String tag, String msg) {
        smartPrint(ERROR, new LogCustomTag(tag), msg, null, false);
    }

    public static void e(LogTagInterface tag, String msg) {
        smartPrint(ERROR, tag, msg, null, false);
    }

    public static void e(LogTagInterface tag, String msg, Throwable t) {
        smartPrint(ERROR, tag, msg, t, false);
    }

    public static void es(LogTagInterface tag, String msg) {
        smartPrint(ERROR, tag, msg, null, true);
    }


}
