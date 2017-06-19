package com.youyou.xiaofeibao.version2.mine.favorite;

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
import com.youyou.xiaofeibao.version2.request.favorite.FavoriteRequestObject;
import com.youyou.xiaofeibao.version2.request.favorite.FavoriteRequestParam;
import com.youyou.xiaofeibao.version2.response.favorite.FavoriteResponseObject;
import com.youyou.xiaofeibao.version2.response.favorite.FavoriteResponseParam;

import java.util.List;

/**
 * 作者：young on 2016/12/5 15:33
 */

public class FavoriteActivity extends BaseTitleActivity {

    private int pageNum = 1;
    private int pageCount = 10;

    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;
    @ViewInject(R.id.lv_data)
    PullToRefreshListView lv_data;
    private FavoriteAdapter mAdapter;

    @Override
    protected int getTitleText() {
        return R.string.favorite;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_favorite;
    }

    @Override
    protected void initData() {
        super.initData();

        mAdapter = new FavoriteAdapter(mActivity, R.layout.v2_listview_favorite);
        lv_data.setAdapter(mAdapter);

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, SubscribeActivity.class);
                intent.setAction(mAdapter.getList().get(position - 1).getMemid());
                startActivity(intent);
            }
        });


        lv_data.setMode(PullToRefreshBase.Mode.BOTH);
        lv_data.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNum = 1;
                getDataList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getDataList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataList();
    }

    private void getDataList() {
        FavoriteRequestObject requestObject = new FavoriteRequestObject();
        FavoriteRequestParam param = new FavoriteRequestParam();
        param.setPageNum(pageNum + "");
        param.setPageOffset(pageCount + "");

        requestObject.setParam(param);
        ResponseBuilder<FavoriteRequestObject, FavoriteResponseObject> builder = new ResponseBuilder<>(requestObject, Config.FAVORITE,FavoriteResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<FavoriteResponseObject>() {
            @Override
            public void onSuccess(FavoriteResponseObject Object) {
                if (lv_data.isRefreshing()) {
                    lv_data.onRefreshComplete();
                }
                List<FavoriteResponseParam> mList = Object.getData().getCollectionlst();
                if (pageNum == 1) {
                    mAdapter.setList(mList);
                    if (mList.size() == 0) {
                        tv_nodata.setVisibility(View.VISIBLE);
                    } else {
                        pageNum++;
                        tv_nodata.setVisibility(View.GONE);
                    }
                } else {
                    if (mList.size() != 0) {
                        pageNum++;
                    }
                    mAdapter.addList(mList);
                }

            }
        }).send();
    }
}
