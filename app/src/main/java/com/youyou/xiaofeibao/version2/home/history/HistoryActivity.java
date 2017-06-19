package com.youyou.xiaofeibao.version2.home.history;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.net.SimpleNetCallBack;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.home.hot.SubscribeActivity;
import com.youyou.xiaofeibao.version2.request.browse.BrowseRequestObject;
import com.youyou.xiaofeibao.version2.request.browse.BrowseRequestParam;
import com.youyou.xiaofeibao.version2.response.browse.BrowseResponseDate;
import com.youyou.xiaofeibao.version2.response.browse.BrowseResponseObject;
import com.youyou.xiaofeibao.version2.response.browse.BrowseResponseParam;
import com.youyou.xiaofeibao.version2.response.browse.BrowseResponseTransData;
import com.youyou.xiaofeibao.version2.widget.RefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * 作者：young on 2016/10/17 11:51
 */

public class HistoryActivity extends BaseTitleActivity {
    private int pagecount = 12;
    private int pageNum = 1;

    private int temp1 = 0;
    private int temp2 = 0;

    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;
    @ViewInject(R.id.refresh)
    RefreshLayout refresh;

    private HistoryAdapter mHistoryAdapter;
    @ViewInject(R.id.list)
    PinnedSectionListView mListView;

    @Override
    protected int getTitleText() {
        return R.string.home_history;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_history;
    }

    @Override
    protected void initView() {
        super.initView();
        mHistoryAdapter = new HistoryAdapter(mActivity);
        mListView.setAdapter(mHistoryAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BrowseResponseTransData data = mHistoryAdapter.getData().get(position);
                if (data.getType() == data.ITEM) {
                    Intent intent = new Intent(mActivity, SubscribeActivity.class);
                    intent.setAction(data.getObject().getMemid());
                    startActivity(intent);
                }
            }
        });

        /**下拉刷新*/
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                temp2 = temp1 = 0;
                reqestData();
            }
        });

        /**上拉加载*/
        refresh.setOnLoadListener(new RefreshLayout.ILoadListener() {

            @Override
            public void onLoad() {
                reqestData();

            }
        });
    }

    @Override
    protected void initData() {

        super.initData();

        reqestData();

    }

    private void reqestData() {
        BrowseRequestObject requestObject = new BrowseRequestObject();
        BrowseRequestParam param = new BrowseRequestParam();
        param.setPageOffset((pagecount * pageNum) + "");
        param.setPageNum("1");

        requestObject.setParam(param);
        ResponseBuilder<BrowseRequestObject, BrowseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.BROWSEHISTORY,BrowseResponseObject.class);

        builder.setCallBack(new SimpleNetCallBack<BrowseResponseObject>() {
            @Override
            public void onNetFail() {

            }

            @Override
            public void onSuccess(BrowseResponseObject reponse) {
                if (refresh.isRefreshing()) {
                    refresh.setRefreshing(false);
                }
                refresh.setLoadDone();
                List<BrowseResponseTransData> translist = new ArrayList<>();
                List<BrowseResponseDate> data = reponse.getData().getMapList();

                for (int i = 0; i < data.size(); i++) {
                    List<BrowseResponseParam> param = data.get(i).getMlist();
                    BrowseResponseParam time = new BrowseResponseParam();
                    time.setShopName(data.get(i).getTime());
                    translist.add(new BrowseResponseTransData(BrowseResponseTransData.SECTION, time));
                    for (int j = 0; j < param.size(); j++) {
                        translist.add(new BrowseResponseTransData(BrowseResponseTransData.ITEM, param.get(j)));
                    }
                }
                if (translist.size() == 0) {
                    tv_nodata.setVisibility(View.VISIBLE);
                } else {
                    tv_nodata.setVisibility(View.GONE);
                    mHistoryAdapter.setData(translist);

                }

                temp1 = translist.size();

                if (temp2 < temp1) {//表示数据增加了
                    temp2 = temp1;
                    pageNum++;
                } else if (temp2 == temp1) {
                    temp2++;
                    refresh.setLoadunDone();
                    Toast.makeText(mActivity, "没有更多浏览记录了。。", Toast.LENGTH_SHORT).show();
                }
            }
        }).send();
    }
}
