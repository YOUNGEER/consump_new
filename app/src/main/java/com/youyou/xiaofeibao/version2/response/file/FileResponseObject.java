package com.youyou.xiaofeibao.version2.response.file;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/12 16:15
 */

public class FileResponseObject extends BaseResponseObject {
    private FileResponseData data;

    public FileResponseData getData() {
        return data;
    }

    public void setData(FileResponseData data) {
        this.data = data;
    }
}
