package com.youyou.xiaofeibao.version2.mine.orderlist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.util.CircularImage;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.shopcomment.ShopCommentRequestData;
import com.youyou.xiaofeibao.version2.request.shopcomment.ShopCommentRequestObject;
import com.youyou.xiaofeibao.version2.request.shopcomment.ShopCommentRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.orderlist.OrderListResponseParam;
import com.youyou.xiaofeibao.version2.widget.ListStar;

/**
 * 作者：young on 2016/12/22 14:54
 */

public class PingjiaActivity extends BaseTitleActivity {
    private Bundle mBundle;
    private OrderListResponseParam mparam;
    @ViewInject(R.id.liststar)
    ListStar liststar;
    @ViewInject(R.id.et_content)
    EditText et_content;
    @ViewInject(R.id.tv_submit)
    TextView tv_submit;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.iv_img)
    CircularImage iv_img;

    @Override
    protected void initData() {
        super.initData();
        liststar.setClickable(true);
        mBundle = getIntent().getExtras();
        mparam = (OrderListResponseParam) mBundle.getSerializable("data");

        tv_name.setText(mparam.getShopName());
        ImageUtils.display(iv_img, mparam.getDoorImg());

    }


    @Override
    protected void setListener() {
        super.setListener();
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitData();
            }
        });
    }

    private void submitData() {
        ShopCommentRequestObject requestObject = new ShopCommentRequestObject();
        ShopCommentRequestParam param = new ShopCommentRequestParam();
        ShopCommentRequestData data = new ShopCommentRequestData();
        data.setContent(et_content.getText().toString().trim());
        data.setTotalScore((int) liststar.getStarStep());
        data.setGoodsOrderId(mparam.getPay_number());
        data.setShopId(mparam.getShopId());
        param.setShopComment(data);
        requestObject.setParam(param);
        ResponseBuilder<ShopCommentRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.SUBMITCOMMENT,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject baseResponseObject) {
                Toast.makeText(mActivity, "评价成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).send();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_pingjia;
    }

    @Override
    protected int getTitleText() {
        return R.string.pingjia;
    }

}
