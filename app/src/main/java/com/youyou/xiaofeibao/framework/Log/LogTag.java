package com.youyou.xiaofeibao.framework.Log;

public enum LogTag implements LogTagInterface {

    /**
     * 临时
     */
    TEMP(true),
    /**
     * 临时
     */
    LOCATION(true),

    /**
     * 全局
     */
    GLOBAL(true),
    /**
     * 框架
     */
    FRAMEWORK(true),
    /**
     * 界面
     */
    UI(true),

    /**
     * 网络
     */
    HTTP_NET(true),
    /**
     * 脚本
     */
    SCRIPT(true),
    /**
     * 统计
     */
    ANALYSIS(true),

    /**
     * 启动
     */
    LAUNCH(true),
    SDCARD(true),
    PAY(true);

    public boolean isFlag() {
        return flag;
    }

    private final boolean flag;

    LogTag(boolean b) {
        this.flag = b;
    }

    @Override
    public String getTagName() {
        return name();
    }

}
