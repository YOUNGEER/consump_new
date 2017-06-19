package com.youyou.xiaofeibao.framework.Log;

public class LogCustomTag implements LogTagInterface {

    private String tagName = "custom";

    public LogCustomTag(String s) {
        tagName = s;
    }

    @Override
    public String getTagName() {
        return tagName;
    }

}
