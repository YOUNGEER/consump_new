package com.youyou.xiaofeibao.version2.home.benefit.proxy;

import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.page.PageRequestObject;
import com.youyou.xiaofeibao.version2.request.page.PageRequestParam;
import com.youyou.xiaofeibao.version2.response.proxyhistory.ProxyHistoryResponseObject;
import com.youyou.xiaofeibao.version2.response.proxyhistory.ProxyHistoryResponseParam;

import java.util.List;

/**
 * 作者：young on 2017/5/26 15:58
 */

public class ProxyHistory extends BaseTitleActivity {

    @ViewInject(R.id.pl_listview)
    PullToRefreshListView pl_listview;

    private int PageNum = 1;
    private int count = 10;

    private ProxyHistoryAdapter mAdapter;

    @Override
    protected int getTitleText() {
        return R.string.postal_history;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_symx;
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new ProxyHistoryAdapter(mActivity, R.layout.listview_proxyhistory);
        pl_listview.setMode(PullToRefreshBase.Mode.BOTH);
        pl_listview.setAdapter(mAdapter);
        pl_listview.setEmptyView((RelativeLayout) LayoutInflater.from(this).inflate(R.layout.emptyview,null));

    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    @Override
    protected void setListener() {
        super.setListener();

        pl_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                PageNum = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData();
            }
        });

    }

    private void getData(){
        PageRequestObject object=new PageRequestObject();
        PageRequestParam param=new PageRequestParam();
        param.setPageNum(PageNum+"");
        param.setPageOffset(count+"");
        object.setParam(param);

        ResponseBuilder<PageRequestObject, ProxyHistoryResponseObject> builder = new ResponseBuilder<>(object, Config.GETWITHDRAWRECORD,ProxyHistoryResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<ProxyHistoryResponseObject>() {
            @Override
            public void onSuccess(ProxyHistoryResponseObject Object) {
                if(pl_listview.isRefreshing()){
                    pl_listview.onRefreshComplete();
                }

                List<ProxyHistoryResponseParam> listBeanList=Object.getData().getAppliWithdrawallist();
                if(PageNum==1){
                    mAdapter.setList(listBeanList);
                }else {
                    mAdapter.addList(listBeanList);
                }

                if(listBeanList.size()!=0){
                    PageNum++;
                }
            }

            @Override
            public void onFailure(ProxyHistoryResponseObject responseObject) {
                if(pl_listview.isRefreshing()){
                    pl_listview.onRefreshComplete();
                }
            }

            @Override
            public void onServerFailure(String str) {
                super.onServerFailure(str);
                if(pl_listview.isRefreshing()){
                    pl_listview.onRefreshComplete();
                }
            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if(pl_listview.isRefreshing()){
                    pl_listview.onRefreshComplete();
                }
            }
        }).send();
    }
}
