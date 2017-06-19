package com.youyou.xiaofeibao.version2.mine.favorite;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.adapter.CustormBaseAdapter;
import com.youyou.xiaofeibao.version2.adapter.ViewHolder;
import com.youyou.xiaofeibao.version2.request.like.LikeRequestObject;
import com.youyou.xiaofeibao.version2.request.like.LikeRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.favorite.FavoriteResponseParam;

/**
 * 作者：young on 2016/12/5 15:41
 */

public class FavoriteAdapter extends CustormBaseAdapter<FavoriteResponseParam> {


    public FavoriteAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void initView(final FavoriteResponseParam bean, ViewHolder holder, int position) {
        holder.setTextViewText(R.id.tv_name, bean.getShopName());
        holder.setTextViewText(R.id.tv_addr, bean.getAddr());
        ImageView iv = (ImageView) holder.getView(R.id.tv_img);
        ImageUtils.display(iv, bean.getDoorImg());
        TextView tv = holder.getView(R.id.tv_cancel);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLike(bean);
            }
        });
    }

    private void getLike(final FavoriteResponseParam bean) {
        LikeRequestObject requestObject = new LikeRequestObject();
        LikeRequestParam param = new LikeRequestParam();
        param.setType("0");
        param.setObjectId(bean.getMemid());
        requestObject.setParam(param);
        ResponseBuilder<LikeRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.ISLIKE,BaseResponseObject.class);

        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject baseResponseObject) {
                getList().remove(bean);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(BaseResponseObject responseObject) {
                super.onFailure(responseObject);

            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);

            }
        }).send();

    }
}
