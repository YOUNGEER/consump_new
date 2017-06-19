package com.youyou.xiaofeibao.version2.home.benefit.myshop;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.LayoutUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.page.PageRequestObject;
import com.youyou.xiaofeibao.version2.request.page.PageRequestParam;
import com.youyou.xiaofeibao.version2.response.myshop.MyshopResponseObject;
import com.youyou.xiaofeibao.version2.response.myshop.MyshopResponseParam;
import com.youyou.xiaofeibao.view.ScreenUtils;

import java.util.List;

/**
 * 作者：young on 2016/12/24 10:24
 */

public class MyShopActivity extends BaseTitleActivity {
    private int PageNum = 1;
    private int PageCount = 10;

    private MyShopAdapter mAdapter;

    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;

    @ViewInject(R.id.pl_listview)
    PullToRefreshListView pl_listview;

    @Override
    protected int getTitleText() {
        return R.string.myshop;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_myshop;
    }

    @Override
    protected void initData() {
        super.initData();
        /**rightView*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int rightMargin = LayoutUtils.dip2px(8);
        layoutParams.setMargins(0, 0, rightMargin, 0);
        TextView tv = new TextView(this);
        tv.setWidth(ScreenUtils.dp2px(mActivity, 15f));
        tv.setHeight(ScreenUtils.dp2px(mActivity, 15f));
        tv.setClickable(true);
        tv.setBackground(getResources().getDrawable(R.mipmap.search));
//        tv.setCompoundDrawables(getResources().getDrawable(R.mipmap.add),null,null,null);
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(Color.WHITE);

        setRightTitleView(tv, layoutParams);
        tv.setTextSize(0f);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, SearchShopActivity.class));
            }
        });


        mAdapter = new MyShopAdapter(mActivity, R.layout.v2_listview_myshop);
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
                MyshopResponseParam param=mAdapter.getItem(position-1);
                String memid=param.getMemid();
                MyShopMoneyActivity.getData(mActivity,memid);
            }
        });
    }

    private void submitData() {
        PageRequestObject requestObject = new PageRequestObject();
        final PageRequestParam param = new PageRequestParam();
        param.setPageNum(PageNum + "");
        param.setPageOffset(PageCount + "");
        requestObject.setParam(param);
        ResponseBuilder<PageRequestObject, MyshopResponseObject> builder = new ResponseBuilder<>(requestObject, Config.MYSHOP,MyshopResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<MyshopResponseObject>() {
            @Override
            public void onSuccess(MyshopResponseObject Objecet) {
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
                List<MyshopResponseParam> payorderList = Objecet.getData().getShopList();

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
            public void onFailure(MyshopResponseObject responseObject) {
                super.onFailure(responseObject);
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
            }
        }).send();
    }

}
