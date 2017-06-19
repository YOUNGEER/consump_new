package com.youyou.xiaofeibao.version2.request.file;

import com.youyou.xiaofeibao.version2.request.BaseRequestObject;

/**
 * 作者：young on 2016/12/12 15:55
 */

public class FileRequestObject extends BaseRequestObject {

    private FileRequestParam param;


    public FileRequestParam getParam() {
        return param;
    }

    public void setParam(FileRequestParam param) {
        this.param = param;
    }
}
