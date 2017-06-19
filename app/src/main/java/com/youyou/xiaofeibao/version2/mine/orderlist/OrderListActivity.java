package com.youyou.xiaofeibao.version2.mine.orderlist;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
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
import com.youyou.xiaofeibao.version2.home.hot.SubscribeActivity;
import com.youyou.xiaofeibao.version2.request.page.PageRequestObject;
import com.youyou.xiaofeibao.version2.request.page.PageRequestParam;
import com.youyou.xiaofeibao.version2.response.orderlist.OrderListResponseObject;
import com.youyou.xiaofeibao.version2.response.orderlist.OrderListResponseParam;

import java.util.List;

/**
 * 作者：young on 2016/10/19 10:20
 */

public class OrderListActivity extends BaseTitleActivity {

    private OrderlistAdapter mAdapter;
    private int pageCount = 10;
    private int pageNum = 1;

    @ViewInject(R.id.ll_listview)
    PullToRefreshListView mListView;
    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;


    @Override
    protected int getTitleText() {
        return R.string.title_orderlist;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_ordermanage;
    }


    @Override
    protected void initData() {
        super.initData();
        mAdapter = new OrderlistAdapter(mActivity, R.layout.v2_listview_orderlist);
        mListView.setAdapter(mAdapter);

        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNum = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, SubscribeActivity.class);
                intent.setAction(mAdapter.getList().get(position - 1).getShopId());
                startActivity(intent);
            }
        });

        getData();
    }

    private void getData() {
        PageRequestObject requestObject = new PageRequestObject();
        PageRequestParam param = new PageRequestParam();
        param.setPageNum(pageNum + "");
        param.setPageOffset(pageCount + "");
        requestObject.setParam(param);


        ResponseBuilder<PageRequestObject, OrderListResponseObject> builder = new ResponseBuilder<>(requestObject, Config.ORDERLIST,OrderListResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<OrderListResponseObject>() {
            @Override
            public void onSuccess(OrderListResponseObject Object) {
                if (mListView.isRefreshing()) {
                    mListView.onRefreshComplete();
                }

                List<OrderListResponseParam> orderList = Object.getData().getOrderList();

                if (pageNum == 1) {
                    if (orderList.size() == 0) {
                        tv_nodata.setVisibility(View.VISIBLE);
                    } else {
                        tv_nodata.setVisibility(View.GONE);
                        mAdapter.setList(orderList);
                        pageNum++;
                    }
                } else {
                    if (orderList.size() != 0) {
                        pageNum++;
                    }
                    mAdapter.addList(orderList);
                }
            }

            @Override
            public void onFailure(OrderListResponseObject responseObject) {
                super.onFailure(responseObject);
                if (mListView.isRefreshing()) {
                    mListView.onRefreshComplete();
                }
            }
        }).send();


    }
}
