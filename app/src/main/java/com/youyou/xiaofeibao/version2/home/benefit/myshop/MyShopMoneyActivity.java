package com.youyou.xiaofeibao.version2.home.benefit.myshop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
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
import com.youyou.xiaofeibao.version2.request.fund.ShopFundRequestObject;
import com.youyou.xiaofeibao.version2.request.fund.ShopFundRequestParam;
import com.youyou.xiaofeibao.version2.response.fund.FundResponseObject;
import com.youyou.xiaofeibao.version2.response.fund.FundResponseParam;
import com.youyou.xiaofeibao.version2.tool.SeletetimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 作者：young on 2016/12/24 10:24
 */

public class MyShopMoneyActivity extends BaseTitleActivity {
    private int PageNum = 1;
    private int PageCount = 10;

    private String memid="";

    private static String FLAG="flag";



    private FundAdapter mAdapter;

    @ViewInject(R.id.pl_listview)
    PullToRefreshListView pl_listview;
    @ViewInject(R.id.tv_start)
    TextView tv_start;
    @ViewInject(R.id.tv_end)
    TextView tv_end;
    @ViewInject(R.id.tv_search)
    TextView tv_search;

    private int year_s;
    private int month_s;
    private int day_s;

    private int year_e;
    private int month_e;
    private int day_e;


    @Override
    protected int getTitleText() {
        return R.string.mingxi;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_myshopmoney;
    }

    public static void getData(Context context, String memid){
        Intent intent=new Intent(context,MyShopMoneyActivity.class);
        intent.putExtra(FLAG,memid);
        context.startActivity(intent);
    }


    @Override
    protected void initData() {
        super.initData();
        memid=getIntent().getStringExtra(FLAG);

        mAdapter = new FundAdapter(mActivity, R.layout.v2_listview_detail);
        submitData();

        RelativeLayout rl= (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.emptyview,null);
        pl_listview.setMode(PullToRefreshBase.Mode.BOTH);
        pl_listview.setEmptyView(rl);
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

    }

    @Override
    protected void setListener() {
        super.setListener();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();

        tv_end.setText(simpleDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.MONTH,+1);
        year_e=calendar.get(Calendar.YEAR);
        month_e=calendar.get(Calendar.MONTH);
        day_e=calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH,-4);
        tv_start.setText(simpleDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.MONTH,+1);
        year_s=calendar.get(Calendar.YEAR);
        month_s=calendar.get(Calendar.MONTH);
        day_s=calendar.get(Calendar.DAY_OF_MONTH);


        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SeletetimeUtils().getSeleteTime(mActivity, new SeletetimeUtils.GetSeleteTimeListener() {
                    @Override
                    public void seleteTimeListener(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        year_s=year;
                        month_s=monthOfYear;
                        day_s=dayOfMonth;
                        tv_start.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                },year_s,month_s,day_s);
            }
        });

        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SeletetimeUtils().getSeleteTime(mActivity, new SeletetimeUtils.GetSeleteTimeListener() {
                    @Override
                    public void seleteTimeListener(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        year_e=year;
                        month_e=monthOfYear;
                        day_e=dayOfMonth;
                        Log.i("ssssssssss2",year_e+"   "+month_e+"   "+day_e);
                        tv_end.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                },year_e,month_e,day_e);
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageNum=1;
                submitData();
            }
        });
    }

    private void submitData() {
        ShopFundRequestObject requestObject = new ShopFundRequestObject();
        final ShopFundRequestParam param = new ShopFundRequestParam();
        param.setPageNum(PageNum + "");
        param.setPageOffset(PageCount + "");
        param.setMemid(memid);
        param.setStarttime(tv_start.getText().toString().trim());
        param.setEndtime(tv_end.getText().toString().trim());

        requestObject.setParam(param);
        ResponseBuilder<ShopFundRequestObject, FundResponseObject> builder = new ResponseBuilder<>(requestObject, Config.PROXYSHOPFUND,FundResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<FundResponseObject>() {
            @Override
            public void onSuccess(FundResponseObject Objecet) {
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }

                List<FundResponseParam> payorderList = Objecet.getData().getShopList();

                if (PageNum == 1) {
                        mAdapter.setList(payorderList);
                        PageNum++;
                } else {
                    if (payorderList.size() != 0) {
                        PageNum++;
                        mAdapter.addList(payorderList);
                    }
                }
            }

            @Override
            public void onFailure(FundResponseObject responseObject) {
                super.onFailure(responseObject);
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
            }
        }).send();
    }

}
