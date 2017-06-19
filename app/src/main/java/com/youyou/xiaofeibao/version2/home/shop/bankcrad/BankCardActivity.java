package com.youyou.xiaofeibao.version2.home.shop.bankcrad;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.LayoutUtils;
import com.youyou.xiaofeibao.framework.Dialog;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.deletebank.DeleteBankRequestObject;
import com.youyou.xiaofeibao.version2.request.deletebank.DeleteBankRequestParam;
import com.youyou.xiaofeibao.version2.response.mybank.MybankResponseObjecet;
import com.youyou.xiaofeibao.version2.response.mybank.MybankResponselist;
import com.youyou.xiaofeibao.view.ScreenUtils;

import java.util.List;


/**
 * 作者：young on 2016/12/27 10:31
 */

public class BankCardActivity extends BaseTitleActivity implements MybankAdapter.DeleteListner {
    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;
    @ViewInject(R.id.lv_banklist)
    ListView lv_banklist;

    private MybankAdapter mAdapter;



    @Override
    protected int getTitleText() {
        return R.string.nyyhk;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_bankcrad;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        super.initView();
        /**rightView*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int rightMargin = LayoutUtils.dip2px(8);
        layoutParams.setMargins(0, 0, rightMargin, 0);
        TextView tv = new TextView(this);
        tv.setWidth(ScreenUtils.dp2px(mActivity, 15f));
        tv.setHeight(ScreenUtils.dp2px(mActivity, 15f));
        tv.setClickable(true);
        tv.setBackground(getResources().getDrawable(R.mipmap.add));
//        tv.setCompoundDrawables(getResources().getDrawable(R.mipmap.add),null,null,null);
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(Color.WHITE);

        setRightTitleView(tv, layoutParams);
        tv.setTextSize(0f);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AddBankActivity.class));
            }
        });

    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_nodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AddBankActivity.class));
            }
        });

        lv_banklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(null!=getIntent()&&null!=getIntent().getAction()&&getIntent().getAction().equals("2")){//表示从提现界面跳转过来的
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("data",mAdapter.getList().get(position));
                    intent.putExtras(bundle);
                    setResult(2,intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getBankList();
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter=new MybankAdapter(mActivity, R.layout.v2_listivew_mybank);
        mAdapter.setListner(this);
        lv_banklist.setAdapter(mAdapter);

        getBankList();
    }

    private void getBankList() {

        ResponseBuilder<EmptyRequestObject, MybankResponseObjecet> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.MYBANK,MybankResponseObjecet.class);
        builder.setCallBack(new BaseNetCallBack<MybankResponseObjecet>() {
            @Override
            public void onSuccess(MybankResponseObjecet object) {
                List<MybankResponselist> bkList=object.getData().getBkList();
                if(bkList.size()==0){
                    tv_nodata.setVisibility(View.VISIBLE);
                }else {
                    tv_nodata.setVisibility(View.GONE);
                    mAdapter.setList(object.getData().getBkList());

                }
            }
        }).send();
    }

    @Override
    public void delete(final int pos) {
        if(null!=getIntent()&&null!=getIntent().getAction()&&getIntent().getAction().equals("2")) {//表示从提现界面跳转过来的
            return;
        }

        Dialog dialog=new Dialog(mActivity);
        dialog.setTitle("温馨提示：");
        dialog.setMessage("确定要删除该银行卡吗？");
        dialog.setConfirmText("确定删除");
        dialog.setConfirmClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteBankRequestObject requestObject=new DeleteBankRequestObject();
                DeleteBankRequestParam param=new DeleteBankRequestParam();
                param.setId(mAdapter.getList().get(pos).getId()+"");
                requestObject.setParam(param);
                ResponseBuilder<DeleteBankRequestObject, MybankResponseObjecet> builder = new ResponseBuilder<>(requestObject, Config.DELETECRAD,MybankResponseObjecet.class);
                builder.setCallBack(new BaseNetCallBack<MybankResponseObjecet>() {
                    @Override
                    public void onSuccess(MybankResponseObjecet mybankResponseObjecet) {
                        mAdapter.getList().remove(pos);
                        mAdapter.notifyDataSetChanged();
                    }
                }).send();
            }
        });
        dialog.show();
    }
}
