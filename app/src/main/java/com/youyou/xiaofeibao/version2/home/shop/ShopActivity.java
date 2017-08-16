package com.youyou.xiaofeibao.version2.home.shop;

import android.content.Intent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.home.InfoWebView;
import com.youyou.xiaofeibao.version2.home.postal.PostalActivity;
import com.youyou.xiaofeibao.version2.home.shop.bankcrad.BankCardActivity;
import com.youyou.xiaofeibao.version2.home.shop.member.MyMemberActivity;
import com.youyou.xiaofeibao.version2.home.shop.order.OrderManagerActivity;
import com.youyou.xiaofeibao.version2.home.shop.store.StoreManageActivity;
import com.youyou.xiaofeibao.version2.home.shop.sy.BenefitActivity;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.response.shopcount.ShopCountResponseObject;
import com.youyou.xiaofeibao.version2.response.shopcount.ShopCountResponseShop;
import com.youyou.xiaofeibao.wxapi.WechatShareHelper;

/**
 * 作者：young on 2016/10/20 18:01
 */

public class ShopActivity extends BaseTitleActivity {
    @ViewInject(R.id.pl_refresh)
    PullToRefreshScrollView pl_refresh;
    @ViewInject(R.id.tv_ddgl)
    TextView tv_ddgl;
    @ViewInject(R.id.tv_dpgl)
    TextView tv_dpgl;
    @ViewInject(R.id.tv_syt)
    TextView tv_syt;
    @ViewInject(R.id.tv_wdhy)
    TextView tv_wdhy;
    @ViewInject(R.id.tv_yhk)
    TextView tv_yhk;
    @ViewInject(R.id.tv_symx)
    TextView tv_symx;

    @ViewInject(R.id.tv_shopname)
    TextView tv_shopname;
    @ViewInject(R.id.tv_money_count)
    TextView tv_money_count;
    @ViewInject(R.id.tv_widthdraw)
    TextView tv_widthdraw;
    @ViewInject(R.id.tv_settle)
    TextView tv_settle;
    @ViewInject(R.id.tv_supply)
    TextView tv_supply;
    @ViewInject(R.id.tv_share)
    TextView tv_share;
    @ViewInject(R.id.tv_register)
    TextView tv_register;

    private String shopid;

    private String yingshou_money;
    private String tongbaobi_money;

    @Override
    protected int getTitleText() {
        return R.string.shop;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_shop;
    }

    @Override
    protected void initData() {
        super.initData();

        getDate();
    }

    private void getDate() {
        ResponseBuilder<EmptyRequestObject, ShopCountResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.SHOPACCOUNT,ShopCountResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<ShopCountResponseObject>() {
            @Override
            public void onSuccess(ShopCountResponseObject shopCountResponseObject) {
                if (pl_refresh.isRefreshing()) {
                    pl_refresh.onRefreshComplete();
                }
                ShopCountResponseShop data = shopCountResponseObject.getData().getShop();
                tv_shopname.setText(data.getShopName());
                tv_money_count.setText("总余额：" + data.getTotal_money());

                yingshou_money = data.getMoney() + "";
                tongbaobi_money = data.getSettlementing_money() + "";

                tv_settle.setText(yingshou_money + "");
                tv_widthdraw.setText(tongbaobi_money + "");

                shopid = data.getMemid();
            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if (pl_refresh.isRefreshing()) {
                    pl_refresh.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(ShopCountResponseObject responseObject) {
                super.onFailure(responseObject);
                if (pl_refresh.isRefreshing()) {
                    pl_refresh.onRefreshComplete();
                }
            }
        }).send();
    }

    @Override
    protected void setListener() {
        super.setListener();
        //店铺管理
        tv_dpgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, StoreManageActivity.class));
//                try {
//                    VoiceUtils.getInstance().initmTts(getApplicationContext(),"大涛哥是一个傻逼");
//
//                }catch (Exception e){
//                    Log.i("sfdsfasdfas",e.toString());
//                }
            }
        });

        //订单管理
        tv_ddgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, OrderManagerActivity.class));

            }
        });

        //收银台
        tv_syt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, CheckstandActivity.class);
                intent.putExtra("shopId", shopid);
                intent.putExtra("shopName", tv_shopname.getText().toString().trim());
                startActivity(intent);
            }
        });

        //我的会员
        tv_wdhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(mActivity, MyMemberActivity.class));
            }
        });

        //我的银行卡
        tv_yhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, BankCardActivity.class));

            }
        });

        //申请提现
        tv_supply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PostalActivity.class);
                intent.putExtra("from", 1);
                intent.putExtra("yingshou", yingshou_money);
                intent.putExtra("tongbaobi", tongbaobi_money);
                startActivityForResult(intent, 1);
            }
        });

        //收益明细
        tv_symx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, BenefitActivity.class));
            }
        });


        pl_refresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pl_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getDate();
            }
        });

        //分享注册
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WechatShareHelper helper=new WechatShareHelper(mActivity);
                helper.shareToWechat();
            }
        });

        //扫码注册
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=Config.Base_Url+"web/shopShare?memid="+PreferencesConfig.v2_uesrid.get();
                Intent intent = new Intent(mActivity, InfoWebView.class);
                intent.putExtra("name", "扫码注册");
                intent.setAction(url);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            getDate();
        }
    }


}
