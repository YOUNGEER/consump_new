package com.youyou.xiaofeibao.version2.home.benefit;

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
import com.youyou.xiaofeibao.version2.home.benefit.detail.DetailsActivity;
import com.youyou.xiaofeibao.version2.home.benefit.myshop.MyShopActivity;
import com.youyou.xiaofeibao.version2.home.benefit.proxy.ProxyActivity;
import com.youyou.xiaofeibao.version2.home.postal.Postal2Activity;
import com.youyou.xiaofeibao.version2.home.shop.bankcrad.BankCardActivity;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.response.proxy.ProxyResponseObject;
import com.youyou.xiaofeibao.version2.response.proxy.ProxyResponseParam;
import com.youyou.xiaofeibao.wxapi.WechatShareHelper;

/**
 * 作者：young on 2016/11/18 15:37
 */

public class AgentActivity extends BaseTitleActivity implements View.OnClickListener {
    @ViewInject(R.id.pl_refresh)
    PullToRefreshScrollView pl_refresh;
    @ViewInject(R.id.tv_shopname)
    TextView tv_shopname;
    @ViewInject(R.id.tv_money_count)
    TextView tv_money_count;
    @ViewInject(R.id.tv_supply)
    TextView tv_supply;
    @ViewInject(R.id.tv_total)
    TextView tv_total;
    @ViewInject(R.id.tv_turnover)
    TextView tv_turnover;
    @ViewInject(R.id.tv_dpgl)
    TextView tv_dpgl;
    @ViewInject(R.id.tv_syt)
    TextView tv_syt;
    @ViewInject(R.id.tv_yhk)
    TextView tv_yhk;
    @ViewInject(R.id.tv_share)
    TextView tv_share;
    @ViewInject(R.id.tv_register)
    TextView tv_register;
    @ViewInject(R.id.tv_baodan)
    TextView tv_baodan;

    private String today;

    private String money="";

    private String today_money;
    private String history_money;

    @Override
    protected int getTitleText() {
        return R.string.agent;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_agent;
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_supply.setOnClickListener(this);
        tv_dpgl.setOnClickListener(this);
        tv_syt.setOnClickListener(this);
        tv_yhk.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_baodan.setOnClickListener(this);

        pl_refresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pl_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getData();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        getData();

    }

    private void getData() {
        ResponseBuilder<EmptyRequestObject, ProxyResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.MYPROXY,ProxyResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<ProxyResponseObject>() {
            @Override
            public void onSuccess(ProxyResponseObject Object) {
                if(pl_refresh.isRefreshing()){
                    pl_refresh.onRefreshComplete();
                }

                ProxyResponseParam param = Object.getData().getProxy();
                if (null != param.getProxyname()) {
                    tv_shopname.setText(param.getProxyname());
                }

                tv_money_count.setText("代理余额：" + param.getBalance() + "");
                tv_total.setText(param.getSettlementing_money() + "");
                tv_turnover.setText(param.getShop_money() + "");
                money=param.getBalance()+"";
                today=param.getDay_money()+"";
                history_money=param.getHistory_withdrawal()+"";
                today_money=param.getToday_withdrawal()+"";

            }

            @Override
            public void onFailure(ProxyResponseObject Object) {
                super.onFailure(Object);
                if(pl_refresh.isRefreshing()){
                    pl_refresh.onRefreshComplete();
                }
                if (Object.getCode().equals("-1")) {
                    startActivity(new Intent(mActivity, LoginOrRegisterActivity.class));
                }
            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if(pl_refresh.isRefreshing()){
                    pl_refresh.onRefreshComplete();
                }
            }
        }).send();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_supply:
//                Intent intent=new Intent(mActivity, PostalActivity.class);
//                intent.putExtra("from",2);
//                intent.putExtra("today",today_money);
//                intent.putExtra("history",history_money);
//                startActivityForResult(intent,1);
                Intent intent = new Intent(mActivity, Postal2Activity.class);
                intent.setAction(tv_turnover.getText().toString().trim());
                intent.putExtra("type", "2");
                startActivityForResult(intent,1);
                break;
            case R.id.tv_dpgl://我的商店
                startActivity(new Intent(mActivity, MyShopActivity.class));
                break;
            case R.id.tv_syt://收益明细
                startActivity(new Intent(mActivity, DetailsActivity.class));
                break;
            case R.id.tv_yhk://我的银行卡
                startActivity(new Intent(mActivity, BankCardActivity.class));
                break;
            case R.id.tv_share://邀请注册
                WechatShareHelper helper=new WechatShareHelper(mActivity);
                helper.shareToWechat();
                break;
            case R.id.tv_register:
                String url=Config.Base_Url+"web/shopShare?memid="+ PreferencesConfig.v2_uesrid.get();
                Intent intent2 = new Intent(mActivity, InfoWebView.class);
                intent2.putExtra("name", "扫码注册");
                intent2.setAction(url);
                startActivity(intent2);
                break;
            case R.id.tv_baodan:
                startActivity(new Intent(AgentActivity.this, ProxyActivity.class));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            getData();
        }
    }
}
