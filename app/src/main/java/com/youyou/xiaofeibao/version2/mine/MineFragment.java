package com.youyou.xiaofeibao.version2.mine;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.framework.activity.BaseFragment;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.util.CircularImage;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.home.count.MyCountActivity;
import com.youyou.xiaofeibao.version2.home.history.HistoryActivity;
import com.youyou.xiaofeibao.version2.home.postal.Postal2Activity;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.mine.countinfo.CountInfoActivity;
import com.youyou.xiaofeibao.version2.mine.favorite.FavoriteActivity;
import com.youyou.xiaofeibao.version2.mine.orderlist.OrderListActivity;
import com.youyou.xiaofeibao.version2.mine.setting.SettingActivity;
import com.youyou.xiaofeibao.version2.mine.tg.MyTgActivity;
import com.youyou.xiaofeibao.version2.mine.yaoqing.YaoqingActivity;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.mycount.MyCountRequsetParam;
import com.youyou.xiaofeibao.version2.request.mycount.MycountRequestObject;
import com.youyou.xiaofeibao.version2.response.mineinfo.MineinfoResponseObject;
import com.youyou.xiaofeibao.version2.response.mycount.MycountResponseObject;

/**
 * 作者：young on 2016/10/4 09:03
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    @ViewInject(R.id.ll_favorite)
    LinearLayout ll_favorite;
    @ViewInject(R.id.iv_img)
    CircularImage iv_img;
    @ViewInject(R.id.tv_nickname)
    TextView tv_nickname;
    @ViewInject(R.id.ll_setting)
    LinearLayout ll_setting;
    @ViewInject(R.id.tv_setting)
    TextView tv_setting;
    @ViewInject(R.id.ll_history)
    LinearLayout ll_history;
    @ViewInject(R.id.tv_order_manage)
    LinearLayout tv_order_manage;
    @ViewInject(R.id.tv_register)
    TextView tv_register;
    @ViewInject(R.id.tv_tuiguang)
    TextView tv_tuiguang;
    @ViewInject(R.id.ll_pl)
    PullToRefreshScrollView ll_pl;

    @ViewInject(R.id.tv_money)
    TextView tv_money;
    @ViewInject(R.id.tv_postmoney)
    TextView tv_postmoney;

    private String money;
    private int type=1;

    @Override
    protected void initData() {
        mineinfo();
        getData();
    }

    @Override
    protected void updateUI() {

    }

    @Override
    public void onResume() {
        super.onResume();

        ImageUtils.display(iv_img, PreferencesConfig.v2_url.get());
        tv_nickname.setText(PreferencesConfig.v2_loginName.get());
        getData();
        if(!"".equals(PreferencesConfig.v2_token.get())){
            type=1;
            mineinfo();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void setListener() {

        ll_favorite.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        ll_history.setOnClickListener(this);
        tv_order_manage.setOnClickListener(this);
        tv_money.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_tuiguang.setOnClickListener(this);
        tv_postmoney.setOnClickListener(this);

        ll_pl.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ll_pl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                type=2;
                mineinfo();
                getData();
            }
        });
    }

    //获取个人信息的接口
    private void mineinfo() {

        ResponseBuilder<EmptyRequestObject, MineinfoResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.MINEINFO,MineinfoResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<MineinfoResponseObject>() {
            @Override
            public void onSuccess(MineinfoResponseObject Object) {
                if (ll_pl.isRefreshing()) {
                    ll_pl.onRefreshComplete();
                }

                ImageUtils.display(iv_img, PreferencesConfig.v2_url.get());

                tv_nickname.setText(PreferencesConfig.v2_loginName.get());
                PreferencesConfig.v2_iswx.set(Object.getData().getPerson().getIsBindWx());
                PreferencesConfig.v2_iszfb.set(Object.getData().getPerson().getIsBindZfb());
            }

            @Override
            public void onFailure(MineinfoResponseObject responseObject) {
                super.onFailure(responseObject);
                if (ll_pl.isRefreshing()) {
                    ll_pl.onRefreshComplete();
                }
                Toast.makeText(getActivity(), responseObject.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }).send();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine_v2;
    }

    @Override
    public void onClick(View v) {
        int code = v.getId();
        switch (code) {
//            case R.id.tv_alliance:
//                //商户入口
//                if (PreferencesConfig.v2_token.get().equals("")) {
//                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
//                } else {
//                    if ("".equals(PreferencesConfig.v2_isshopchecked.get())) {
//                        startActivity(new Intent(getActivity(), IntroduceShopActivity.class));
//                    } else if ("0".equals(PreferencesConfig.v2_isshopchecked.get())) {//待审核
//                        Toast.makeText(getActivity(), "正在审核中", Toast.LENGTH_SHORT).show();
//                    } else if ("1".equals(PreferencesConfig.v2_isshopchecked.get())) {//审核通过
//                        startActivity(new Intent(getActivity(), ShopActivity.class));
//                    } else if ("0".equals(PreferencesConfig.v2_isshopchecked.get())) {//审核不通过
//                        Toast.makeText(getActivity(), "对不起，审核未通过，请重新进行资料填写", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getActivity(), IntroduceShopActivity.class));
//                    }
//                }
//
//                break;

            case R.id.tv_order_manage:
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), OrderListActivity.class));
                }

                break;
            case R.id.ll_favorite:
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), FavoriteActivity.class));
                }

                break;
            case R.id.ll_setting:
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), CountInfoActivity.class));
                }

                break;
            case R.id.tv_setting:
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }
                break;

            case R.id.ll_history:
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), HistoryActivity.class));
                }
                break;
            case R.id.tv_money:
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyCountActivity.class));
                }
                break;
//            case R.id.tv_see:
//                if (PreferencesConfig.v2_url.get().equals("")) {
//                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
//                } else {
//                    String str=tv_money.getText().toString().trim();
//                    if(PreferencesConfig.v2_see.get()){
//                        tv_money.setText("***.**");
//                        PreferencesConfig.v2_see.set(false);
//                    }else {
//                        tv_money.setText(money);
//                        PreferencesConfig.v2_see.set(true);
//                    }
//                }
//            break;
            case R.id.tv_register://注册推广
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
//                    WechatShareHelper helper=new WechatShareHelper(getActivity());
//                    helper.shareToWechat();

                    startActivity(new Intent(getActivity(), YaoqingActivity.class));
                }

                break;
            case R.id.tv_tuiguang://我的推广
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyTgActivity.class));
                }
                break;
            case R.id.tv_postmoney://推广可提现金额
                Intent intent = new Intent(getActivity(), Postal2Activity.class);
                intent.setAction(tv_postmoney.getText().toString().trim());
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
        }
    }

    private void getData() {
        MycountRequestObject requestObject = new MycountRequestObject();
        MyCountRequsetParam param = new MyCountRequsetParam();
        param.setPageNum( "1");
        param.setPageOffset("1");
        requestObject.setParam(param);
        ResponseBuilder<MycountRequestObject, MycountResponseObject> builder = new ResponseBuilder<>(requestObject, Config.MYCOUNT,MycountResponseObject.class);

        builder.setCallBack(new BaseNetCallBack<MycountResponseObject>() {
            @Override
            public void onSuccess(MycountResponseObject Object) {
                if(ll_pl.isRefreshing()){
                    ll_pl.onRefreshComplete();
                }
                money=Object.getData().getBalance();
                tv_postmoney.setText(Object.getData().getSettlementing_money() + "");
                if(PreferencesConfig.v2_see.get()){
                    tv_money.setText(money);
                }else {
                    tv_money.setText("***.**");
                }

                PreferencesConfig.v2_iswx.set(Object.getData().getIsBindWx());
                PreferencesConfig.v2_iszfb.set(Object.getData().getIsBindZfb());
            }
            @Override
            public void onFailure(MycountResponseObject object) {
                if(ll_pl.isRefreshing()){
                    ll_pl.onRefreshComplete();
                }
                    if(object.getCode().equals("-1")){
                        tv_money.setText("未登陆");
                    }
            }
        }).send();
    }
}
