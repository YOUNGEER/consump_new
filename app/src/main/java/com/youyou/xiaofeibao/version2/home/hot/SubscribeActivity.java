package com.youyou.xiaofeibao.version2.home.hot;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.net.SimpleNetCallBack;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.home.CommentAdapter;
import com.youyou.xiaofeibao.version2.home.MapViewShowAddrActivity;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.pay.pwd.PayActivityPre2;
import com.youyou.xiaofeibao.version2.request.comment.CommentRequestObject;
import com.youyou.xiaofeibao.version2.request.comment.CommentRequestParam;
import com.youyou.xiaofeibao.version2.request.like.LikeRequestObject;
import com.youyou.xiaofeibao.version2.request.like.LikeRequestParam;
import com.youyou.xiaofeibao.version2.request.shopinfo.ShopInfoRequestObject;
import com.youyou.xiaofeibao.version2.request.shopinfo.ShopInfoRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.comment.CommentResponseList;
import com.youyou.xiaofeibao.version2.response.comment.CommentResponseObject;
import com.youyou.xiaofeibao.version2.response.shopinfo.ShopInfoResponseObject;
import com.youyou.xiaofeibao.version2.response.shopinfo.ShopinfoResponseParam;
import com.youyou.xiaofeibao.version2.response.special.SpecialResponseObject;
import com.youyou.xiaofeibao.version2.response.special.SpecialResponseParam;
import com.youyou.xiaofeibao.version2.widget.ListStar;
import com.youyou.xiaofeibao.view.AutoHeightListView;

import java.util.List;

/**
 * 作者：young on 2016/11/10 10:20
 */

public class SubscribeActivity extends BaseTitleActivity {

    @ViewInject(R.id.tv_shopname)
    TextView tv_shopname;
    @ViewInject(R.id.iv_door)
    ImageView iv_door;
    @ViewInject(R.id.tv_addr)
    TextView tv_addr;
    @ViewInject(R.id.tv_intro)
    TextView tv_intro;
    @ViewInject(R.id.tv_tele)
    TextView tv_tele;
    @ViewInject(R.id.tv_nodata_special)
    TextView tv_nodata_special;
    @ViewInject(R.id.tv_nodata_comment)
    TextView tv_nodata_comment;
    @ViewInject(R.id.tv_more_comment)
    TextView tv_more_comment;
    @ViewInject(R.id.cb_like)
    CheckBox cb_like;
    @ViewInject(R.id.tv_callback)
    TextView tv_callback;
    @ViewInject(R.id.tv_call_hint)
    TextView tv_call_hint;

    @ViewInject(R.id.lv_speciall)
    AutoHeightListView lv_speciall;
    @ViewInject(R.id.lv_comment)
    AutoHeightListView lv_comment;
    @ViewInject(R.id.tv_subscribe)
    LinearLayout tv_subscribe;
    private String shopReturnRate="";

    @ViewInject(R.id.liststar)
    ListStar liststar;

    private SpecialAdapter mSpecialAdapter;
    private CommentAdapter mCommentAdapter;

    private String phoneNum;

    private int pagecount = 3;
    private String latitude;
    private String longitude;

    @Override
    protected int getTitleText() {
        return R.string.yuyue_shop;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_subcribe_shop;
    }

    @Override
    protected void initData() {
        super.initData();

        mSpecialAdapter = new SpecialAdapter(mActivity, R.layout.v2_listview_special);
        mCommentAdapter = new CommentAdapter(mActivity, R.layout.v2_listview_comment);
        lv_speciall.setAdapter(mSpecialAdapter);
        lv_comment.setAdapter(mCommentAdapter);

        getShopInfo();
        getSpeciallData();
        getCommentData(3);

        liststar.setClickable(false);

    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(mActivity, LoginOrRegisterActivity.class));
                } else {
                    Intent intent = new Intent(mActivity, PayActivityPre2.class);
                    intent.setAction(getIntent().getAction());
                    intent.putExtra("name",tv_shopname.getText().toString().trim());
                    intent.putExtra("return",shopReturnRate);
                    intent.putExtra("sub",true);
                    startActivity(intent);
                }
            }
        });
        //跳转到电话页面
        tv_tele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tv_tele.getText().toString().trim()));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });
        cb_like.setClickable(false);
        cb_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_like.isChecked()) {
                    getLike("1");
                } else {
                    getLike("0");
                }
            }
        });

        cb_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        tv_more_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagecount = pagecount + 3;
                getCommentData(pagecount);
            }
        });

        tv_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, MapViewShowAddrActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lon",longitude);
                intent.putExtra("addr",tv_addr.getText().toString().trim());
                intent.putExtra("name",tv_shopname.getText().toString().trim());
                startActivity(intent);


            }
        });
    }


    //获取商户的信息
    private void getShopInfo() {
        ShopInfoRequestObject responseObject = new ShopInfoRequestObject();
        ShopInfoRequestParam param = new ShopInfoRequestParam();
        param.setMemid(getIntent().getAction());
        responseObject.setParam(param);
        ResponseBuilder<ShopInfoRequestObject, ShopInfoResponseObject> builder = new ResponseBuilder<>(responseObject, Config.SHOPINFO,ShopInfoResponseObject.class);

        builder.setCallBack(new SimpleNetCallBack<ShopInfoResponseObject>() {
            @Override
            public void onNetFail() {

            }

            @Override
            public void onSuccess(ShopInfoResponseObject reponse) {
                ShopinfoResponseParam param = reponse.getData().getShop();
                tv_shopname.setText(param.getShopName());

                ImageUtils.display(iv_door,param.getDoorImg());

                tv_addr.setText(param.getAddr());
                tv_intro.setText(param.getIntroduction());
                tv_tele.setText(param.getShopPhone());
                latitude=param.getLatitude();
                longitude=param.getLongitude();
                shopReturnRate=param.getShopReturnRate();
                try {
                    int srr= (int)( Double.parseDouble(shopReturnRate)*100);
                    if(0==srr){
                        tv_callback.setVisibility(View.GONE);
                    }else {
                        tv_callback.setVisibility(View.VISIBLE);
                        tv_callback.setText("支付立返"+srr+".00%");
                        tv_call_hint.setText("(支付立返"+srr+".00%)");
                    }

                }catch (Exception e){
                    tv_callback.setVisibility(View.GONE);
                    tv_call_hint.setVisibility(View.GONE);
                }

                String collect = reponse.getData().getShop().getCollect();
                if (collect.equals("0")) {
                    cb_like.setChecked(false);
                } else {
                    cb_like.setChecked(true);
                }
            }
        }).send();
    }

    //获取商户的特色商品信息
    private void getSpeciallData() {
        ShopInfoRequestObject responseObject = new ShopInfoRequestObject();
        ShopInfoRequestParam param = new ShopInfoRequestParam();
        param.setMemid(getIntent().getAction());
        responseObject.setParam(param);
        ResponseBuilder<ShopInfoRequestObject, SpecialResponseObject> builder = new ResponseBuilder<>(responseObject, Config.SPECIALL,SpecialResponseObject.class);

        builder.setCallBack(new SimpleNetCallBack<SpecialResponseObject>() {
            @Override
            public void onNetFail() {

            }

            @Override
            public void onSuccess(SpecialResponseObject reponse) {
                List<SpecialResponseParam> products = reponse.getData().getProducts();
                if (products.size() == 0) {
                    tv_nodata_special.setVisibility(View.VISIBLE);
                } else {
                    tv_nodata_special.setVisibility(View.GONE);
                    mSpecialAdapter.setList(reponse.getData().getProducts());
                }

            }
        }).send();
    }

    //用户评价
    private void getCommentData(final int pagcount) {
        CommentRequestObject responseObject = new CommentRequestObject();
        CommentRequestParam param = new CommentRequestParam();
        param.setMemid(getIntent().getAction());
        param.setPageNum("1");
        param.setPageOffest(pagcount + "");
        responseObject.setParam(param);
        ResponseBuilder<CommentRequestObject, CommentResponseObject> builder = new ResponseBuilder<>(responseObject, Config.COMMENT,CommentResponseObject.class);

        builder.setCallBack(new SimpleNetCallBack<CommentResponseObject>() {
            @Override
            public void onNetFail() {

            }

            @Override
            public void onSuccess(CommentResponseObject reponse) {
                Float f = 0f;
                try {
                    f = Float.parseFloat(reponse.getData().getAvgScore());
                } catch (Exception e) {
                    f = 0f;
                }

                liststar.setStar(f);
                List<CommentResponseList> commentlist = reponse.getData().getCommentlist();
                if (commentlist.size() == 0) {
                    tv_nodata_comment.setVisibility(View.VISIBLE);
                    liststar.setVisibility(View.GONE);
                } else {
                    tv_nodata_comment.setVisibility(View.GONE);
                    tv_more_comment.setVisibility(View.VISIBLE);
                    liststar.setVisibility(View.VISIBLE);
                }
                mCommentAdapter.setList(reponse.getData().getCommentlist());

                if (reponse.getData().getCommentlist().size() == pagcount) {
                    tv_more_comment.setVisibility(View.GONE);
                }
            }
        }).send();
    }

    private void getLike(final String type) {
        LikeRequestObject requestObject = new LikeRequestObject();
        LikeRequestParam param = new LikeRequestParam();
        param.setType(type);
        param.setObjectId(getIntent().getAction());
        requestObject.setParam(param);
        ResponseBuilder<LikeRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.ISLIKE,BaseResponseObject.class);

        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject baseResponseObject) {
                if (type.equals("0")) {
                    cb_like.setChecked(false);
                } else {
                    cb_like.setChecked(true);
                }

            }

            @Override
            public void onFailure(BaseResponseObject responseObject) {
                super.onFailure(responseObject);
                if (type.equals("0")) {
                    cb_like.setChecked(true);
                } else {
                    cb_like.setChecked(false);
                }
                if (responseObject.getCode().equals("-1")) {
                    startActivity(new Intent(mActivity, LoginOrRegisterActivity.class));
                }
            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if (type.equals("0")) {
                    cb_like.setChecked(true);
                } else {
                    cb_like.setChecked(false);
                }
            }
        }).send();

    }


}
