package com.youyou.xiaofeibao.version2.home.partner;

import android.os.Bundle;
import android.widget.ListView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseFragment;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.alliancemarket.AllianceMarketRequestObject;
import com.youyou.xiaofeibao.version2.request.alliancemarket.AllianceMarketRequestParam;
import com.youyou.xiaofeibao.version2.response.alliancemarket.AllianceMarketResponseObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 作者：young on 2016/10/20 20:24
 */

public class ItemMarketFragment extends BaseFragment {

    @ViewInject(R.id.listview)
    PullToRefreshListView listview;

    private AllianceMarketAdapter mAdapter;

    private String areaId = "";
    private int page = 1;


    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        areaId = bundle.getString("id");
        initDataList();
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new AllianceMarketAdapter(getActivity(), R.layout.v2_item_listview_alliacnmarket);
        listview.setAdapter(mAdapter);
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void setListener() {

        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                initDataList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                initDataList();
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.v2_item_shop_fragment;
    }

    private void initDataList() {

        AllianceMarketRequestObject requestObject = new AllianceMarketRequestObject();
        final AllianceMarketRequestParam param = new AllianceMarketRequestParam();
//        param.setArea(areaId);
        param.setArea("");
        param.setShopcitycode("");
        param.setType("");
        param.setPageCur(page + "");
        requestObject.setParam(param);
        ResponseBuilder<AllianceMarketRequestObject, AllianceMarketResponseObject> builder = new ResponseBuilder<>(requestObject, Config.SUPERMAKETLIST,AllianceMarketResponseObject.class);
        builder.setCallBack(new NetCallBack<AllianceMarketResponseObject>() {
            @Override
            public void onSuccess(AllianceMarketResponseObject reponse) {
                if (listview.isRefreshing()) {
                    listview.onRefreshComplete();
                }
                if (page == 1) {
                    mAdapter.setList(reponse.getData().getSupmaketlist());
                } else {
                    mAdapter.addList(reponse.getData().getSupmaketlist());
                }

                if (reponse.getData().getSupmaketlist().size() > 0) {
                    page++;
                }
            }

            @Override
            public void onNetFailure(String str) {

            }

            @Override
            public void onServerFailure(String str) {

            }

            @Override
            public void onFailure(AllianceMarketResponseObject allianceMarketResponseObject) {
                if (listview.isRefreshing()) {
                    listview.onRefreshComplete();
                }
            }
        }).send();

    }
}
