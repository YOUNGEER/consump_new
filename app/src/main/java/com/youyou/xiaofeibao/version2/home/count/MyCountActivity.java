package com.youyou.xiaofeibao.version2.home.count;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.login.LoginOrRegisterActivity;
import com.youyou.xiaofeibao.version2.request.mycount.MyCountRequsetParam;
import com.youyou.xiaofeibao.version2.request.mycount.MycountRequestObject;
import com.youyou.xiaofeibao.version2.response.mycount.MycountResponseObject;
import com.youyou.xiaofeibao.version2.response.mycount.MycountResponseParam;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 作者：young on 2016/10/20 16:03
 */

public class MyCountActivity extends BaseTitleActivity {
    @ViewInject(R.id.lv_bene)
    PullToRefreshListView lv_bene;
    @ViewInject(R.id.tv_money_count)
    TextView tv_money_count;
    @ViewInject(R.id.tv_tishi)
    TextView tv_tishi;
    @ViewInject(R.id.tv_supply)
    TextView tv_supply;

    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;

    private BebefitAdapter mBebefitAdapter;

    private int pageCount = 10;
    private int pageNun = 1;


    @Override
    protected int getTitleText() {
        return R.string.home_count;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_mycount;
    }

    @Override
    protected void initData() {
        super.initData();
        mBebefitAdapter = new BebefitAdapter(mActivity, R.layout.v2_listview_favorite_);
        lv_bene.setAdapter(mBebefitAdapter);
        lv_bene.setMode(PullToRefreshBase.Mode.BOTH);
        getData();
    }


    @Override
    protected void setListener() {
        super.setListener();
        tv_supply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lv_bene.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNun = 1;
                getData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData();
            }
        });

        lv_bene.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MycountResponseParam param = mBebefitAdapter.getItem(position - 1);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", param);
                Intent intent = new Intent(mActivity, BenefitInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        final MycountRequestObject requestObject = new MycountRequestObject();
        MyCountRequsetParam param = new MyCountRequsetParam();
        param.setPageNum(pageNun + "");
        param.setPageOffset(pageCount + "");
        requestObject.setParam(param);
        ResponseBuilder<MycountRequestObject, MycountResponseObject> builder = new ResponseBuilder<>(requestObject, Config.MYCOUNT,MycountResponseObject.class);

        builder.setCallBack(new BaseNetCallBack<MycountResponseObject>() {
            @Override
            public void onSuccess(MycountResponseObject Object) {
                if (lv_bene.isRefreshing()) {
                    lv_bene.onRefreshComplete();
                }
                tv_money_count.setText(Object.getData().getBalance());
                tv_tishi.setText("可提现金额" + Object.getData().getBalance());
                List<MycountResponseParam> list = Object.getData().getList();
                if (pageNun == 1) {
                    if (list.size() == 0) {
                        tv_nodata.setVisibility(View.VISIBLE);
                    } else {
                        pageNun++;
                        tv_nodata.setVisibility(View.GONE);
                    }
                    mBebefitAdapter.setList(list);
                } else {
                    if (list.size() != 0) {
                        pageNun++;
                    }
                    mBebefitAdapter.addList(list);
                }
            }

            @Override
            public void onFailure(MycountResponseObject responseObject) {
                super.onFailure(responseObject);
                if (lv_bene.isRefreshing()) {
                    lv_bene.onRefreshComplete();
                }

                if(responseObject.getCode().equals("-1")){
                    Log.i("ssssssssssssssssss","Myacoount");
                    startActivity(new Intent(mActivity, LoginOrRegisterActivity.class));
                }
            }
        }).send();
    }

}
