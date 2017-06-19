package com.youyou.xiaofeibao.version2.home.benefit.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.home.count.BenefitInfoActivity;
import com.youyou.xiaofeibao.version2.request.page.PageRequestObject;
import com.youyou.xiaofeibao.version2.request.page.PageRequestParam;
import com.youyou.xiaofeibao.version2.response.detail.DetailResponseObjecte;
import com.youyou.xiaofeibao.version2.response.detail.DetailResponseParam;
import com.youyou.xiaofeibao.version2.response.mycount.MycountResponseParam;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 作者：young on 2016/12/24 11:14
 */

public class DetailsActivity extends BaseTitleActivity {

    private int PageNum = 1;
    private int PageCount = 10;

    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;

    @ViewInject(R.id.pl_listview)
    PullToRefreshListView pl_listview;

    private DetailAdapter mAdapter;

    @Override
    protected int getTitleText() {
        return R.string.mingxi;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_shopmanager;
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new DetailAdapter(mActivity, R.layout.v2_listview_detail);
        submitData();

        pl_listview.setMode(PullToRefreshBase.Mode.BOTH);

        pl_listview.setAdapter(mAdapter);

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


        pl_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailResponseParam param1=mAdapter.getItem(position - 1);
                MycountResponseParam param = new MycountResponseParam();
                param.setAccount_description(param1.getBill_description());
                param.setCreatedate(param1.getCreatedate());
                param.setCreateyear(param1.getCreateyear());
                param.setCreatetime(param1.getCreatetime());
                param.setGoldnum(param1.getMoney());
                param.setShopName(param1.getTitle());
                param.setTitle(param1.getTitle());
                param.setType(param1.getType());
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", param);
                Intent intent = new Intent(mActivity, BenefitInfoActivity.class);
                intent.setAction("1");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void submitData() {
        PageRequestObject requestObject = new PageRequestObject();
        PageRequestParam param = new PageRequestParam();
        param.setPageNum(PageNum + "");
        param.setPageOffset(PageCount + "");
        requestObject.setParam(param);
        ResponseBuilder<PageRequestObject, DetailResponseObjecte> builder = new ResponseBuilder<>(requestObject, Config.DETAIL,DetailResponseObjecte.class);
        builder.setCallBack(new BaseNetCallBack<DetailResponseObjecte>() {
            @Override
            public void onSuccess(DetailResponseObjecte Objecet) {
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
                List<DetailResponseParam> payorderList = Objecet.getData().getBilllogList();


                if (PageNum == 1) {
                    if (payorderList.size() == 0) {
                        tv_nodata.setVisibility(View.VISIBLE);
                    } else {
                        tv_nodata.setVisibility(View.GONE);
                        mAdapter.setList(payorderList);
                        PageNum++;
                    }
                } else {
                    if (payorderList.size() != 0) {
                        PageNum++;
                        mAdapter.addList(payorderList);
                    }
                }
            }

            @Override
            public void onFailure(DetailResponseObjecte responseObject) {
                super.onFailure(responseObject);
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
            }
        }).send();
    }
}
