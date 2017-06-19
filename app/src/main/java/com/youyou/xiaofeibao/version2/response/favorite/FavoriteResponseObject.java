package com.youyou.xiaofeibao.version2.response.favorite;

import com.youyou.xiaofeibao.version2.response.BaseResponseObject;

/**
 * 作者：young on 2016/12/5 15:42
 */

public class FavoriteResponseObject extends BaseResponseObject {

    private FavoriteResponselist data;

    public FavoriteResponselist getData() {
        return data;
    }

    public void setData(FavoriteResponselist data) {
        this.data = data;
    }
}
