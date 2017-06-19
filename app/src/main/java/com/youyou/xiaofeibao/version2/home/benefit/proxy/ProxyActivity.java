package com.youyou.xiaofeibao.version2.home.benefit.proxy;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.LayoutUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.page.PageRequestObject;
import com.youyou.xiaofeibao.version2.request.page.PageRequestParam;
import com.youyou.xiaofeibao.version2.response.zhaoshang.MyRecommendProxyListBean;
import com.youyou.xiaofeibao.version2.response.zhaoshang.ZhaoShangResponseObject;
import com.youyou.xiaofeibao.view.AutoHeightListView;

import java.util.List;


/**
 * 作者：binjun on 2017/5/24 17:17
 */

public class ProxyActivity extends BaseTitleActivity {

    @ViewInject(R.id.pl_refresh)
    PullToRefreshScrollView pl_refresh;
    @ViewInject(R.id.lv_data)
    AutoHeightListView lv_data;
    @ViewInject(R.id.tv_money_count)
    TextView tv_money_count;
    @ViewInject(R.id.tv_supply)
    TextView tv_supply;
    private ProxyAdapter mAdapter;

    private int pageNum=1;
    private int count=10;
    private String money;

    @Override
    protected int getTitleText() {
        return R.string.zhaoshang;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_proxy_activty;
    }

    @Override
    protected void initView() {
        super.initView();
        /**rightView*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        int rightMargin = LayoutUtils.dip2px(8);
        layoutParams.setMargins(0, 0, rightMargin, 0);
        TextView tv = new TextView(this);
        tv.setClickable(true);
        tv.setText(R.string.mingxi);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(Color.WHITE);
        setRightTitleView(tv, layoutParams);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,ProxyBenefitActivity.class));
            }
        });

        pl_refresh.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    protected void initData() {
        super.initData();

        lv_data.setEmptyView((RelativeLayout) LayoutInflater.from(this).inflate(R.layout.emptyview,null));
        mAdapter=new ProxyAdapter(mActivity,R.layout.listview_proxy);
        lv_data.setAdapter(mAdapter);

        getData();

    }

    @Override
    protected void setListener() {
        super.setListener();

        pl_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                pageNum=1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getData();
            }
        });

        tv_supply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity,ZhaoshangPostalActivity.class);
                intent.setAction(money);
                startActivity(intent);
            }
        });
    }


    private void getData() {
        PageRequestObject object=new PageRequestObject();
        PageRequestParam param=new PageRequestParam();
        param.setPageNum(pageNum+"");
        param.setPageOffset(count+"");
        object.setParam(param);

        ResponseBuilder<PageRequestObject, ZhaoShangResponseObject> builder = new ResponseBuilder<>(object, Config.ZHAOSHANG,ZhaoShangResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<ZhaoShangResponseObject>() {
            @Override
            public void onSuccess(ZhaoShangResponseObject Object) {
                if(pl_refresh.isRefreshing()){
                    pl_refresh.onRefreshComplete();
                }

                money=Object.getData().getBalance();
                tv_money_count.setText("余额："+money);

                List<MyRecommendProxyListBean> listBeanList=Object.getData().getMyRecommendProxyList();
                if(pageNum==1){
                    mAdapter.setList(listBeanList);
                }else {
                    mAdapter.addList(listBeanList);
                }

                if(listBeanList.size()!=0){
                    pageNum++;
                }
            }

            @Override
            public void onFailure(ZhaoShangResponseObject responseObject) {
                if(pl_refresh.isRefreshing()){
                    pl_refresh.onRefreshComplete();
                }

            }

            @Override
            public void onServerFailure(String str) {
                super.onServerFailure(str);
                if(pl_refresh.isRefreshing()){
                    pl_refresh.onRefreshComplete();
                }

                Log.i("wyonServerFailure",str);
            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if(pl_refresh.isRefreshing()){
                    pl_refresh.onRefreshComplete();
                }

                Log.i("wyonNetFailure",str);

            }
        }).send();
    }

}
