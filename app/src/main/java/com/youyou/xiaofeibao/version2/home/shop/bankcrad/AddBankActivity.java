package com.youyou.xiaofeibao.version2.home.shop.bankcrad;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.pay.pwd.GridPasswordView;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.bankadd.BankaddRequestObject;
import com.youyou.xiaofeibao.version2.request.bankadd.BankaddRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.banklist.BanklistResponseObject;
import com.youyou.xiaofeibao.version2.settings.ChangeTongPwdActivty;
import com.youyou.xiaofeibao.version2.tool.MD5;

/**
 * 作者：young on 2016/12/27 10:37
 */

public class AddBankActivity extends BaseTitleActivity {
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.et_cardnum)
    EditText et_cardnum;
    @ViewInject(R.id.tv_bank)
    TextView tv_bank;
    @ViewInject(R.id.et_addr)
    EditText et_addr;
    @ViewInject(R.id.tv_sure)
    TextView tv_sure;


    private BankListAdapter madapter;
    private AlertDialog mDialog;//银行列表
    private ListView mListview;

    private PopupWindow pwdPop;
    private GridPasswordView pwdview;
    private View mParent;
    private TextView mBack;

    @Override
    protected int getTitleText() {
        return R.string.add_bank;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_acitivity_addbank;
    }

    @Override
    protected void initView() {
        super.initView();

        tv_name.setText(PreferencesConfig.v2_name.get());

        mListview= (ListView) LayoutInflater.from(mActivity).inflate(R.layout.v2_dialog_banklist,null);
        mDialog=new AlertDialog.Builder(mActivity)
                .setTitle("选择银行")
                .setCancelable(false)
                .setView(mListview)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mDialog.isShowing()){
                            mDialog.hide();
                        }
                    }
                })
                .create();

        mParent=(ScrollView) getLayoutInflater().inflate(R.layout.v2_acitivity_addbank,null);
        LinearLayout linearLayout= (LinearLayout) getLayoutInflater().inflate(R.layout.pop_pwd,null);
        pwdview= (GridPasswordView) linearLayout.findViewById(R.id.pwd_view);
        mBack= (TextView) linearLayout.findViewById(R.id.tv_back);

        pwdPop=new PopupWindow(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        pwdPop.setTouchable(true);
        pwdPop.setOutsideTouchable(true);
        pwdPop.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        pwdPop.setAnimationStyle(R.style.popupAnimation);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=pwdPop&&pwdPop.isShowing()){
                    pwdPop.dismiss();
                    pwdview.clearPassword();
                }
            }
        });

        /**
         * 监听消失
         * */
        pwdPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(null!=pwdview){
                    pwdview.clearPassword();
                }

            }
        });


        pwdview.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
            }
            @Override
            public void onInputFinish(String psw) {
                submitData(pwdview.getPassWord());
                pwdview.clearPassword();
                if(pwdPop.isShowing()){
                    pwdPop.dismiss();
                }
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        madapter=new BankListAdapter(mActivity, R.layout.list_select_pop);
        mListview.setAdapter(madapter);
        requestList();
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();

            }
        });

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_bank.setText(madapter.getList().get(position).getBank_name());
                if(mDialog.isShowing()){
                    mDialog.hide();
                }
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){
                    pwdPop.showAtLocation(mParent, Gravity.BOTTOM,0,0);
                }
            }
        });
    }

    private boolean checkData() {
        if(TextUtils.isEmpty(et_cardnum.getText().toString().trim())){
            Toast.makeText(mActivity,"请完善相关信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void requestList() {
        ResponseBuilder<EmptyRequestObject, BanklistResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.BANKLIST,BanklistResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BanklistResponseObject>() {
            @Override
            public void onSuccess(BanklistResponseObject object) {
                madapter.setList(object.getData().getBankList());
            }
        }).send();
    }

    private void submitData(String pwd) {
        BankaddRequestObject requsetObject = new BankaddRequestObject();
        BankaddRequestParam param = new BankaddRequestParam();
        param.setBankno(et_cardnum.getText().toString().trim());
        param.setPayPwd(MD5.getCode(pwd));
        param.setBankaddr(et_addr.getText().toString().trim());
        param.setBankname(tv_bank.getText().toString().trim());
        requsetObject.setParam(param);
        ResponseBuilder<BankaddRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requsetObject, Config.ADDBANK,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject object) {
                Toast.makeText(mActivity,"添加成功", Toast.LENGTH_SHORT).show();
                if(null!=getIntent()&&null!=getIntent().getAction()){
                    if("1".equals(getIntent().getAction())){
                        setResult(1);
                    }
                }
                finish();
            }

            @Override
            public void onFailure(BaseResponseObject responseObject) {
                super.onFailure(responseObject);
                if(responseObject.getMsg().equals("NO_PAYPWD!")){
                    startActivity(new Intent(mActivity, ChangeTongPwdActivty.class));
                }else{
                    Toast.makeText(mActivity,responseObject.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }).send();
    }
}
