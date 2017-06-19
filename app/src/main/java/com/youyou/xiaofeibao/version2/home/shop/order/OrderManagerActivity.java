package com.youyou.xiaofeibao.version2.home.shop.order;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
import com.youyou.xiaofeibao.version2.response.shoporders.ShopOrderResponseParam;
import com.youyou.xiaofeibao.version2.response.shoporders.ShopOrdersResponseObject;

import java.util.List;

/**
 * 作者：young on 2016/12/12 10:31
 */

public class OrderManagerActivity extends BaseTitleActivity {

    private int PageNum = 1;
    private int PageCount = 10;

    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;

    @ViewInject(R.id.pl_listview)
    PullToRefreshListView pl_listview;

    private OrderManageAdapter mOrderManageAdapter;

    @Override
    protected int getTitleText() {
        return R.string.order_manage;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_shopmanager;
    }


    @Override
    protected void initData() {
        super.initData();
        mOrderManageAdapter = new OrderManageAdapter(mActivity, R.layout.v2_listview_shopmanage);
        submitData();

        pl_listview.setMode(PullToRefreshBase.Mode.BOTH);

        pl_listview.setAdapter(mOrderManageAdapter);

        pl_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                PageNum = 1;
                submitData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                submitData();
            }
        });
    }

    private void submitData() {
        PageRequestObject requestObject = new PageRequestObject();
        PageRequestParam param = new PageRequestParam();
        param.setPageNum(PageNum + "");
        param.setPageOffset(PageCount + "");
        requestObject.setParam(param);
        ResponseBuilder<PageRequestObject, ShopOrdersResponseObject> builder = new ResponseBuilder<>(requestObject, Config.SHOPORDERS,ShopOrdersResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<ShopOrdersResponseObject>() {
            @Override
            public void onSuccess(ShopOrdersResponseObject Objecet) {
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
                List<ShopOrderResponseParam> payorderList = Objecet.getData().getPayorderList();
                if (PageNum == 1) {
                    if (payorderList.size() == 0) {
                        tv_nodata.setVisibility(View.VISIBLE);
                    } else {
                        tv_nodata.setVisibility(View.GONE);
                        mOrderManageAdapter.setList(payorderList);
                        PageNum++;
                    }
                } else {
                    if (payorderList.size() != 0) {
                        PageNum++;
                        mOrderManageAdapter.addList(payorderList);
                    }
                }
            }

            @Override
            public void onFailure(ShopOrdersResponseObject responseObject) {
                super.onFailure(responseObject);
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
            }
        }).send();
    }
}

