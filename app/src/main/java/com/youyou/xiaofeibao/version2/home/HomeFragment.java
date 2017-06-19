package com.youyou.xiaofeibao.version2.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.youyou.xiaofeibao.ConsumApplication;
import com.youyou.xiaofeibao.MainActivity;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.framework.Dialog;
import com.youyou.xiaofeibao.framework.activity.BaseFragment;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.net.SimpleNetCallBack;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.baidumap.LocationService;
import com.youyou.xiaofeibao.version2.banner.BannerView;
import com.youyou.xiaofeibao.version2.home.benefit.AgentActivity;
import com.youyou.xiaofeibao.version2.home.benefit.AgentPreActivity;
import com.youyou.xiaofeibao.version2.home.city.CityActivity;
import com.youyou.xiaofeibao.version2.home.count.MyCountActivity;
import com.youyou.xiaofeibao.version2.home.history.HistoryActivity;
import com.youyou.xiaofeibao.version2.home.hot.HotAdapter;
import com.youyou.xiaofeibao.version2.home.hot.SubscribeActivity;
import com.youyou.xiaofeibao.version2.home.shop.IntroduceShopActivity;
import com.youyou.xiaofeibao.version2.home.shop.ShopActivity;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.mine.favorite.FavoriteActivity;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.homehot.HotRequestObject;
import com.youyou.xiaofeibao.version2.request.homehot.HotRequestParam;
import com.youyou.xiaofeibao.version2.request.recommand.RecommandRequestObject;
import com.youyou.xiaofeibao.version2.request.recommand.RecommandRequstParam;
import com.youyou.xiaofeibao.version2.response.homehot.HomeHotResponseObject;
import com.youyou.xiaofeibao.version2.response.indexpage.IndexPageList;
import com.youyou.xiaofeibao.version2.response.indexpage.IndexPageResponseObject;
import com.youyou.xiaofeibao.version2.response.mineinfo.MineinfoResponseObject;
import com.youyou.xiaofeibao.version2.response.recommand.RecommandResponseList;
import com.youyou.xiaofeibao.version2.response.recommand.RecommandResponseObject;
import com.youyou.xiaofeibao.version2.update.MyUpdateDialog;
import com.youyou.xiaofeibao.view.AutoHeightListView;
import com.youyou.xiaofeibao.zxing.ScanbrcodeActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 作者：young on 2016/10/4 09:02
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private LocationService locationService;

    @ViewInject(R.id.sv_pull)
    PullToRefreshScrollView sv_pull;
    @ViewInject(R.id.tv_scan)
    TextView tv_scan;
    @ViewInject(R.id.tv_count)
    TextView tv_count;
    @ViewInject(R.id.tv_history)
    TextView tv_history;
    @ViewInject(R.id.tv_benifit)
    TextView tv_benifit;
    @ViewInject(R.id.ll_recommand)
    LinearLayout ll_recommand;
    @ViewInject(R.id.ll_hot)
    LinearLayout ll_hot;


    @ViewInject(R.id.tv_market)
    TextView tv_market;
    @ViewInject(R.id.line_market)
    LinearLayout line_market;

    @ViewInject(R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(R.id.linear1)
    LinearLayout mLinearLayout1;

    @ViewInject(R.id.line_shop)
    LinearLayout line_shop;
    @ViewInject(R.id.iv_shop)
    ImageView iv_shop;

    @ViewInject(R.id.banner_1)//广告页
            BannerView mBannerView;

    @ViewInject(R.id.hot_listview)//热门商户
            AutoHeightListView hot_listview;
    @ViewInject(R.id.recommand_listview)//推荐商家
            AutoHeightListView recommand_listview;

    private LinearLayout toolbar_1;
    private TextView mLocation;

    private HotAdapter mHotAdapter;
    private RecommandAdapter mRecommandAdapter;

    private   String[] strings = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void initData() {
        locationService = ConsumApplication.getInstance().locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的

        toolbar_1 = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_home_1, null);
        mLocation = (TextView) toolbar_1.findViewById(R.id.location);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mToolbar.addView(toolbar_1, layoutParams);

        mToolbar.getBackground().setAlpha(255);
        toolbar_1.getBackground().setAlpha(255);

        mHotAdapter = new HotAdapter(getActivity(), R.layout.v2_hothome_listview);
        hot_listview.setAdapter(mHotAdapter);
        mRecommandAdapter = new RecommandAdapter(getActivity(), R.layout.v2_recommand_listview);

        recommand_listview.setAdapter(mRecommandAdapter);
        sv_pull.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        getRecommandShop();
        getIndexPage();
        gethotShop();

        mLocation.setText(PreferencesConfig.v2_cityname.get());

        MyUpdateDialog.checkVersion(getActivity(), 0);

    }

    @Override
    public void onStart() {
        super.onStart();
        // -----------location config ------------
        locationService.registerListener(mlistener);
        if (((MainActivity)getActivity()).hasPermission(strings)) {
            locationService.start();// 定位SDK
        } else {
            ((MainActivity)getActivity()).requestPermission(Config.LOCATION, strings);
        }


    }



    private BDLocationListener mlistener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int code = bdLocation.getLocType();

            if (code == 161) {
                locationService.stop();

                if (PreferencesConfig.v2_isfirst.get().equals("true")) {
                    mLocation.setText(bdLocation.getCity());
                    PreferencesConfig.v2_cityname.set(bdLocation.getCity());
                    PreferencesConfig.v2_citycode.set(bdLocation.getCityCode());
                    PreferencesConfig.v2_lat.set(bdLocation.getLatitude() + "");
                    PreferencesConfig.v2_longt.set(bdLocation.getLongitude() + "");

                    PreferencesConfig.v2_isfirst.set("false");
                } else {
                    if (!PreferencesConfig.v2_cityname.get().equals(bdLocation.getCity())) {
                        showDialog(bdLocation.getCity(), bdLocation.getCityCode(), bdLocation.getLatitude() + "", bdLocation.getLongitude() + "");
                    } else {
                        mLocation.setText(bdLocation.getCity());

                        PreferencesConfig.v2_cityname.set(bdLocation.getCity());
                        PreferencesConfig.v2_citycode.set(bdLocation.getCityCode());
                        PreferencesConfig.v2_lat.set(bdLocation.getLatitude() + "");
                        PreferencesConfig.v2_longt.set(bdLocation.getLongitude() + "");
                    }
                }

            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                Toast.makeText(getActivity(), R.string.TypeCriteriaException, Toast.LENGTH_SHORT).show();
                locationService.stop();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (!"".equals(PreferencesConfig.v2_token.get())) {
            mineinfo();
        }
    }
    private Dialog dialog;

    private void showDialog(final String city, final String citycode, final String lat, final String longt) {

        if(dialog==null){
            dialog = new Dialog(getActivity());
        }else{
            return;
        }
        dialog.setTitle("温馨提示");
        dialog.setMessage("当前定位城市为" + city + "是否进行切换？");
        dialog.setConfirmText("确定切换");
        dialog.setConfirmClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mLocation.setText(city);
                PreferencesConfig.v2_cityname.set(city);
                PreferencesConfig.v2_citycode.set(citycode);
                PreferencesConfig.v2_lat.set(lat);
                PreferencesConfig.v2_longt.set(longt);
                gethotShop();
            }
        });
        dialog.show();
    }

    @Override
    protected void updateUI() {

    }

    //点击事件
    @Override
    protected void setListener() {
        tv_scan.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        tv_history.setOnClickListener(this);
        tv_benifit.setOnClickListener(this);
        tv_market.setOnClickListener(this);
        line_market.setOnClickListener(this);
        iv_shop.setOnClickListener(this);
        line_shop.setOnClickListener(this);
        ll_recommand.setOnClickListener(this);
        ll_hot.setOnClickListener(this);
        mLocation.setOnClickListener(this);

        sv_pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

                getRecommandShop();
                getIndexPage();
                gethotShop();
                if (!"".equals(PreferencesConfig.v2_token.get())) {
                    mineinfo();
                }
            }
        });

        hot_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SubscribeActivity.class);
                intent.setAction(mHotAdapter.getList().get(position).getMemid());
                startActivity(intent);
            }
        });

        recommand_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SubscribeActivity.class);
                intent.setAction(mRecommandAdapter.getList().get(position).getMemid());
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_v2;
    }

    //控件的点击事件
    @Override
    public void onClick(View v) {
        int code = v.getId();
        switch (code) {
            case R.id.location:
                startActivityForResult(new Intent(getActivity(), CityActivity.class), CityActivity.CITYRESULT);
                break;
            case R.id.tv_scan:
                //付款,扫一扫
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                        if (((MainActivity)getActivity()).hasPermission(Manifest.permission.CAMERA)) {
                        startActivity(new Intent(getActivity(), ScanbrcodeActivity.class));
                    } else {
                            ((MainActivity)getActivity()).requestPermission(11, Manifest.permission.CAMERA);
                    }
                }

                break;
            case R.id.tv_count:
                //我的账户
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyCountActivity.class));
                }

                break;
            case R.id.tv_history:
                //浏览记录
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), HistoryActivity.class));
                }

                break;
            case R.id.tv_benifit:
                //店铺关注
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), FavoriteActivity.class));
                }
                break;
            case R.id.line_market:
            case R.id.tv_market:
                //代理入口
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    if ("".equals(PreferencesConfig.v2_isproxychecked.get())) {
                        startActivity(new Intent(getActivity(), AgentPreActivity.class));
                    } else if ("0".equals(PreferencesConfig.v2_isproxychecked.get())) {//待审核
                        Toast.makeText(getActivity(), "正在审核中", Toast.LENGTH_SHORT).show();
                    } else if ("1".equals(PreferencesConfig.v2_isproxychecked.get())) {//审核通过
                        startActivity(new Intent(getActivity(), AgentActivity.class));
                    } else if ("0".equals(PreferencesConfig.v2_isproxychecked.get())) {//审核不通过
                        Toast.makeText(getActivity(), "对不起，审核未通过，请重新进行资料填写", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), AgentPreActivity.class));
                    }
                }
                break;
            case R.id.iv_shop:
            case R.id.line_shop:
                //商户入口
                if (PreferencesConfig.v2_token.get().equals("")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                } else {
                    if ("".equals(PreferencesConfig.v2_isshopchecked.get())) {
                        startActivity(new Intent(getActivity(), IntroduceShopActivity.class));
                    } else if ("0".equals(PreferencesConfig.v2_isshopchecked.get())) {//待审核
                        Toast.makeText(getActivity(), "正在审核中", Toast.LENGTH_SHORT).show();
                    } else if ("1".equals(PreferencesConfig.v2_isshopchecked.get())) {//审核通过
                        startActivity(new Intent(getActivity(), ShopActivity.class));
                    } else if ("0".equals(PreferencesConfig.v2_isshopchecked.get())) {//审核不通过
                        Toast.makeText(getActivity(), "对不起，审核未通过，请重新进行资料填写", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), IntroduceShopActivity.class));
                    }
//                    startActivity(new Intent(getActivity(), IntroduceShopActivity.class));
                }
                break;

            case R.id.ll_recommand:
            case R.id.ll_hot:
                //推荐，热门商家
                ((MainActivity) getActivity()).setPagePosition(1);
                break;
        }
    }

    //获取推荐商家
    private void getRecommandShop() {
        RecommandRequestObject requestObject = new RecommandRequestObject();
        RecommandRequstParam param = new RecommandRequstParam();
        param.setLatitude(PreferencesConfig.v2_lat.get());
        param.setLongitude(PreferencesConfig.v2_longt.get());
        param.setCityCode(PreferencesConfig.v2_citycode.get());
        requestObject.setParam(param);

        ResponseBuilder<RecommandRequestObject, RecommandResponseObject> builder = new ResponseBuilder<>(requestObject, Config.RECOMMAND,RecommandResponseObject.class);
        builder.setCallBack(new SimpleNetCallBack<RecommandResponseObject>() {
            @Override
            public void onNetFail() {
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(RecommandResponseObject recommentResponseObject) {
                super.onFailure(recommentResponseObject);
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
            }

            @Override
            public void onSuccess(RecommandResponseObject reponse) {
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
                List<RecommandResponseList> memList = reponse.getData().getMemList();
                mRecommandAdapter.setList(memList);
            }
        }).send();

    }

    //获取广告页数据
    private void getIndexPage() {

        ResponseBuilder<EmptyRequestObject, IndexPageResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.indexPage,IndexPageResponseObject.class);
        builder.setCallBack(new SimpleNetCallBack<IndexPageResponseObject>() {
            @Override
            public void onNetFail() {
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(IndexPageResponseObject indexPageResponseObject) {
                super.onFailure(indexPageResponseObject);
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
            }

            @Override
            public void onSuccess(IndexPageResponseObject reponse) {
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
                int num = reponse.getData().getAdList().size();

                mBannerView.setpageSize(num);
                for (int i = 0; i < num; i++) {
                    final IndexPageList param = reponse.getData().getAdList().get(i);
                    ImageView img = (ImageView) mBannerView.getViews().get(i).findViewById(R.id.viewpage1);
                    ImageUtils.display(img, param.getAdimg());
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), InfoWebView.class);
                            intent.putExtra("name", param.getTitle());
                            intent.setAction(param.getContent());
                            startActivity(intent);
                        }
                    });
                }
            }
        }).send();
    }

    //获取热门商户
    private void gethotShop() {
        HotRequestParam param = new HotRequestParam();
        param.setCityCode(PreferencesConfig.v2_citycode.get());
        param.setLatitude(PreferencesConfig.v2_lat.get());
        param.setLongitude(PreferencesConfig.v2_longt.get());
        HotRequestObject requestObject = new HotRequestObject();
        requestObject.setParam(param);
        ResponseBuilder<HotRequestObject, HomeHotResponseObject> builder = new ResponseBuilder<>(requestObject, Config.hotshop,HomeHotResponseObject.class);
        builder.setCallBack(new SimpleNetCallBack<HomeHotResponseObject>() {
            @Override
            public void onNetFail() {
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(HomeHotResponseObject homeHotResponseObject) {
                super.onFailure(homeHotResponseObject);
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
            }

            @Override
            public void onSuccess(HomeHotResponseObject reponse) {
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
                mHotAdapter.setList(reponse.getData().getMemberPojoList());

            }

        }).send();
    }

    //获取个人信息的接口
    private void mineinfo() {

        ResponseBuilder<EmptyRequestObject, MineinfoResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.MINEINFO,MineinfoResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<MineinfoResponseObject>() {
            @Override
            public void onSuccess(MineinfoResponseObject Object) {
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }
                PreferencesConfig.v2_isshopchecked.set(Object.getData().getPerson().getIsshopchecked());
                PreferencesConfig.v2_isproxychecked.set(Object.getData().getPerson().getIsproxychecked());
                PreferencesConfig.v2_name.set(Object.getData().getPerson().getName());
            }

            @Override
            public void onFailure(MineinfoResponseObject responseObject) {
                super.onFailure(responseObject);
                if (sv_pull.isRefreshing()) {
                    sv_pull.onRefreshComplete();
                }

                Toast.makeText(getActivity(), responseObject.getMsg(), Toast.LENGTH_SHORT).show();

                if (responseObject.getCode().equals("-1")) {
                    startActivity(new Intent(getActivity(), LoginOrRegisterActivity.class));
                }
            }
        }).send();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CityActivity.CITYRESULT && resultCode == CityActivity.CITYRESULT) {
            mLocation.setText(PreferencesConfig.v2_cityname.get());
            getRecommandShop();
            getIndexPage();
            gethotShop();
            ((MainActivity)getActivity()).cityChange();
        }
    }

    @Override
    public void onStop() {
        locationService.unregisterListener(mlistener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==Config.LOCATION){
            locationService.start();
        }
        if(requestCode==11){
            startActivity(new Intent(getActivity(), ScanbrcodeActivity.class));
        }
    }
}
