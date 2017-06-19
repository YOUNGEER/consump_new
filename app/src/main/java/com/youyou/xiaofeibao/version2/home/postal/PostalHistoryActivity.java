package com.youyou.xiaofeibao.version2.home.postal;

import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.page.PageRequestObject;
import com.youyou.xiaofeibao.version2.request.page.PageRequestParam;
import com.youyou.xiaofeibao.version2.response.postalhistory.PostalHistoryResponseObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 作者：young on 2017/2/15 17:40
 */

public class PostalHistoryActivity extends BaseTitleActivity {
    @ViewInject(R.id.lv)
    PullToRefreshListView mListView;

    private int page=1;
    private int count=10;

    private PostalHistoryAdapter mAdapter;

    @Override
    protected int getTitleText() {
        return R.string.postal_history;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_postalhistory;
    }

    @Override
    protected void initData() {
        super.initData();

        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        RelativeLayout rl= (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.emptyview,null);
        mListView.setEmptyView(rl);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getDataByUrl();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getDataByUrl();
            }
        });

        mAdapter=new PostalHistoryAdapter(mActivity,R.layout.v2_postal_item);
        mListView.setAdapter(mAdapter);


        getDataByUrl();

    }

    private void getDataByUrl() {
        PageRequestObject requestObject=new PageRequestObject();
        PageRequestParam param=new PageRequestParam();
        param.setPageNum(page+"");
        param.setPageOffset(count+"");
        requestObject.setParam(param);
        ResponseBuilder<PageRequestObject, PostalHistoryResponseObject> builder = new ResponseBuilder<>(requestObject, Config.POSTALHISTORY,PostalHistoryResponseObject.class);
        builder.setCallBack(new NetCallBack<PostalHistoryResponseObject>() {
            @Override
            public void onSuccess(PostalHistoryResponseObject reponse) {
                if(mListView.isRefreshing()){
                    mListView.onRefreshComplete();
                }

                if(page==1){
                    mAdapter.setList(reponse.getData());
                }else{
                    mAdapter.addList(reponse.getData());
                }

                if(reponse.getData().size()!=0){
                    page++;
                }
            }

            @Override
            public void onNetFailure(String str) {
                if(mListView.isRefreshing()){
                    mListView.onRefreshComplete();
                }
            }

            @Override
            public void onServerFailure(String str) {
                if(mListView.isRefreshing()){
                    mListView.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(PostalHistoryResponseObject postalHistoryResponseObject) {
                if(mListView.isRefreshing()){
                    mListView.onRefreshComplete();
                }
            }
        }).send();
    }
}
