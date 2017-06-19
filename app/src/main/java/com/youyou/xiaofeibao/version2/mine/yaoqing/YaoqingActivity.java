package com.youyou.xiaofeibao.version2.mine.yaoqing;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.home.InfoWebView;
import com.youyou.xiaofeibao.version2.request.page.PageRequestObject;
import com.youyou.xiaofeibao.version2.request.page.PageRequestParam;
import com.youyou.xiaofeibao.version2.response.yaoqing.YQResponseParam;
import com.youyou.xiaofeibao.version2.response.yaoqing.YaoQingResponseObject;
import com.youyou.xiaofeibao.wxapi.WeChatUtil;

import java.util.List;

public class YaoqingActivity extends BaseTitleActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_wx)
    ImageView iv_wx;
    @ViewInject(R.id.iv_pyq)
    ImageView iv_pyq;
    @ViewInject(R.id.iv_qr)
    ImageView iv_qr;
    @ViewInject(R.id.tv_rule)
    TextView tv_rule;
    @ViewInject(R.id.rv_yao)
    AutoListView  pl_listview;


    private YaoQingAdapter mAdapter;

    @ViewInject(R.id.tv_money)
    TextView tv_money;
    @ViewInject(R.id.tv_num)
    TextView tv_num;
    @ViewInject(R.id.iv_img)
    ImageView iv_img;
    private String url_link="";


    private int PageNum = 1;
    private int PageCount = 100;


    @Override
    protected int getTitleText() {
        return R.string.title_yaoqing;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_yaoqing;
    }

    @Override
    protected void initView() {
        super.initView();
        RelativeLayout relativeLayout= (RelativeLayout) LayoutInflater.from(mActivity).inflate(R.layout.yaoqing_blank,null);
        pl_listview.setEmptyView(relativeLayout);

        mAdapter = new YaoQingAdapter(mActivity, R.layout.item_yaoqing);
        submitData();

//        pl_listview.setMode(PullToRefreshBase.Mode.BOTH);

        pl_listview.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        iv_wx.setOnClickListener(this);
        iv_pyq.setOnClickListener(this);
        iv_qr.setOnClickListener(this);
        tv_rule.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int code = v.getId();
        switch (code) {
            case R.id.iv_wx:
                WeChatUtil.shareWx(mActivity,SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.iv_pyq:
                WeChatUtil.shareWx(mActivity, SendMessageToWX.Req.WXSceneTimeline);
                break;
            case R.id.iv_qr:
                String url= Config.Base_Url+"web/shopShare?memid="+ PreferencesConfig.v2_uesrid.get();
                Intent intent = new Intent(mActivity, InfoWebView.class);
                intent.putExtra("name", "扫码注册");
                intent.setAction(url);
                startActivity(intent);
                break;
            case R.id.tv_rule:
                Intent intent2 = new Intent(mActivity, InfoWebView.class);
                intent2.putExtra("name", "活动规则");
                intent2.setAction(url_link);
                startActivity(intent2);
                break;
        }
    }

    private void submitData() {
        PageRequestObject requestObject = new PageRequestObject();
        PageRequestParam param = new PageRequestParam();
        param.setPageNum(PageNum + "");
        param.setPageOffset(PageCount + "");
        requestObject.setParam(param);

        ResponseBuilder<PageRequestObject, YaoQingResponseObject> builder = new ResponseBuilder<>(requestObject, Config.INVITATION,YaoQingResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<YaoQingResponseObject>() {
            @Override
            public void onSuccess(YaoQingResponseObject Objecet) {
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
                ImageUtils.display(iv_img,Objecet.getData().getAdimg());


                String str=Objecet.getData().getTotal()+"元";
                SpannableString ss=new SpannableString(str);
                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6244")),0,str.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new AbsoluteSizeSpan(60),0,str.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_money.setText(ss);


                String str2=Objecet.getData().getNum()+"人";
                SpannableString ss2=new SpannableString(str2);
                ss2.setSpan(new ForegroundColorSpan(Color.parseColor("#303030")),0,str2.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss2.setSpan(new AbsoluteSizeSpan(60),0,str2.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_num.setText(ss2);

                url_link=Objecet.getData().getUrl();
                List<YQResponseParam> moneyList =Objecet.getData().getMoneyList();
                if(moneyList.size()!=0){
                    PageNum++;
                }

                if(PageNum==1){
                    mAdapter.setList(moneyList);
                }else{
                    mAdapter.addList(moneyList);
                }
            }

            @Override
            public void onFailure(YaoQingResponseObject responseObject) {
                super.onFailure(responseObject);
                if (pl_listview.isRefreshing()) {
                    pl_listview.onRefreshComplete();
                }
            }
        }).send();
    }
}
