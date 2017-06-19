package com.youyou.xiaofeibao.version2.home.benefit.proxy;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.youyou.xiaofeibao.version2.response.proxydetail.BilllogListBean;
import com.youyou.xiaofeibao.version2.response.proxydetail.ProxyDetailResponseObject;

import java.util.List;

/**
 * 作者：young on 2016/12/22 20:28
 */

public class ProxyBenefitActivity extends BaseTitleActivity {
    private int PageNum = 1;
    private int count = 10;

    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;

    @ViewInject(R.id.pl_listview)
    PullToRefreshListView pl_listview;

    private ProxyBenefitAdapter mAdapter;

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
        mAdapter = new ProxyBenefitAdapter(mActivity, R.layout.v2_listview_benefit);
        submitData();

        pl_listview.setMode(PullToRefreshBase.Mode.BOTH);
        pl_listview.setEmptyView((RelativeLayout) LayoutInflater.from(this).inflate(R.layout.emptyview,null));
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
//                BilllogListBean param1=mAdapter.getItem(position - 1);
//                MycountResponseParam param = new MycountResponseParam();
//                param.setAccount_description(param1.getShop_description());
//                param.setCreateyear(param1.getCreateyear());
//                param.setCreatedate(param1.getCreatedate());
//                param.setCreatetime(param1.getCreatetime());
//                param.setGoldnum(param1.getMoney());
//                param.setShopName(param1.getTitle());
//                param.setTitle(param1.getTitle());
//                param.setType(param1.getType());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", param);
//                Intent intent = new Intent(mActivity, BenefitInfoActivity.class);
//                intent.putExtras(bundle);
//                intent.setAction("1");
//                startActivity(intent);
            }
        });
    }

    private void submitData() {
        PageRequestObject object=new PageRequestObject();
        PageRequestParam param=new PageRequestParam();
        param.setPageNum(PageNum+"");
        param.setPageOffset(count+"");
        object.setParam(param);

        ResponseBuilder<PageRequestObject, ProxyDetailResponseObject> builder = new ResponseBuilder<>(object, Config.MONEYDETAIL,ProxyDetailResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<ProxyDetailResponseObject>() {
            @Override
            public void onSuccess(ProxyDetailResponseObject Object) {
                if(pl_listview.isRefreshing()){
                    pl_listview.onRefreshComplete();
                }

                List<BilllogListBean> listBeanList=Object.getData().getBilllogList();
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
            public void onFailure(ProxyDetailResponseObject responseObject) {
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
