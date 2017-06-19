package com.youyou.xiaofeibao.version2.home.shop.sy;

import android.content.Intent;
import android.os.Bundle;
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
import com.youyou.xiaofeibao.version2.home.count.BenefitInfoActivity;
import com.youyou.xiaofeibao.version2.request.page.PageRequestObject;
import com.youyou.xiaofeibao.version2.request.page.PageRequestParam;
import com.youyou.xiaofeibao.version2.response.benefit.BenefitResponseObject;
import com.youyou.xiaofeibao.version2.response.benefit.BenefitResponseParam;
import com.youyou.xiaofeibao.version2.response.mycount.MycountResponseParam;

import java.util.List;

/**
 * 作者：young on 2016/12/22 20:28
 */

public class BenefitActivity extends BaseTitleActivity {
    private int PageNum = 1;
    private int PageCount = 10;
    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;

    @ViewInject(R.id.pl_listview)
    PullToRefreshListView pl_listview;

    private BenefitAdapter mAdapter;

    @Override
    protected int getTitleText() {
        return R.string.symx;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_symx;
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new BenefitAdapter(mActivity, R.layout.v2_listview_benefit);
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
                BenefitResponseParam param1=mAdapter.getItem(position - 1);
                MycountResponseParam param = new MycountResponseParam();
                param.setAccount_description(param1.getShop_description());
                param.setCreateyear(param1.getCreateyear());
                param.setCreatedate(param1.getCreatedate());
                param.setCreatetime(param1.getCreatetime());
                param.setGoldnum(param1.getMoney());
                param.setShopName(param1.getTitle());
                param.setTitle(param1.getTitle());
                param.setType(param1.getType());
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", param);
                Intent intent = new Intent(mActivity, BenefitInfoActivity.class);
                intent.putExtras(bundle);
                intent.setAction("1");
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
        ResponseBuilder<PageRequestObject, BenefitResponseObject> builder = new ResponseBuilder<>(requestObject, Config.BENEFIT,BenefitResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BenefitResponseObject>() {
            @Override
            public void onSuccess(BenefitResponseObject Objecet) {
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
                List<BenefitResponseParam> payorderList = Objecet.getData().getShopList();
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
            public void onFailure(BenefitResponseObject responseObject) {
                super.onFailure(responseObject);
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
            }
        }).send();
    }
}
