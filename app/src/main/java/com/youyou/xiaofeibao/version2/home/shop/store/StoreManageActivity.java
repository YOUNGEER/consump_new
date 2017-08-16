package com.youyou.xiaofeibao.version2.home.shop.store;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.common.LayoutUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.home.shop.beshop.RateSeletorUtils;
import com.youyou.xiaofeibao.version2.home.shop.beshop.SeletorUtils;
import com.youyou.xiaofeibao.version2.request.BaseRequestParam;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.request.applyshop.ApplyShopRequestObject;
import com.youyou.xiaofeibao.version2.request.applyshop.ApplyShopRequestParam;
import com.youyou.xiaofeibao.version2.request.applyshop.ApplyShopRequsetMember;
import com.youyou.xiaofeibao.version2.request.applyshop.ApplyshopRequestShop;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.allcategory.AddressTypeList;
import com.youyou.xiaofeibao.version2.response.allcategory.AllCategoryResponseObjecet;
import com.youyou.xiaofeibao.version2.response.allcategory.BusinessList;
import com.youyou.xiaofeibao.version2.response.allcategory.CategoryList;
import com.youyou.xiaofeibao.version2.response.allcategory.ContactTypeList;
import com.youyou.xiaofeibao.version2.response.querytemp.QueryTempResponseData;
import com.youyou.xiaofeibao.version2.response.querytemp.QueryTempResponseObjecet;
import com.youyou.xiaofeibao.version2.tool.Progress;

import java.io.File;


/**
 * 作者：young on 2016/12/12 15:33
 */

public class StoreManageActivity extends BaseTitleActivity implements View.OnClickListener {

    //view start
    @ViewInject(R.id.iv_doorimg)//店铺首页图
    ImageView iv_doorimg;
    @ViewInject(R.id.et_realname)    //真实姓名
            EditText et_realname;
    @ViewInject(R.id.et_idcard)//身份证号码
            EditText et_idcard;
    @ViewInject(R.id.et_shopname)//店铺名称
    EditText et_shopname;
    @ViewInject(R.id.et_shopphone)//店铺电话
            EditText et_shopphone;
    @ViewInject(R.id.et_shopaddr)//店铺地址
            EditText et_shopaddr;
    @ViewInject(R.id.et_communityphone)//社区代理电话
            EditText et_communityphone;
    @ViewInject(R.id.et_email)//联系人邮箱
            EditText et_email;
    @ViewInject(R.id.et_servicephone)//客服电话
            EditText et_servicephone;
    @ViewInject(R.id.et_shortname)//店铺简称
            EditText et_shortname;
    @ViewInject(R.id.tv_location_addr)//定位地址
            TextView tv_location_addr;
    @ViewInject(R.id.tv_category_type)//经营类别
            TextView tv_category_type;
    @ViewInject(R.id.tv_contact_type)//联系人类型
            TextView tv_contact_type;
    @ViewInject(R.id.tv_address_type)//地址类型
            TextView tv_address_type;
    @ViewInject(R.id.tv_shopreturnrate)//返币比例
            TextView tv_shopreturnrate;
    @ViewInject(R.id.tv_introduce)//商家介绍
            TextView tv_introduce;
    @ViewInject(R.id.et_businessnum)//营业执照编号
            TextView et_businessnum;
    @ViewInject(R.id.tv_licens_type)//营业执照类型
            TextView tv_licens_type;
    @ViewInject(R.id.et_bankcradid)//银行卡号码
            EditText et_bankcradid;
    @ViewInject(R.id.et_bankcradname)//持卡人姓名
            EditText et_bankcradname;
    @ViewInject(R.id.tv_start_time)//开始经营时间
    TextView tv_start_time;
    @ViewInject(R.id.tv_end_time)//结束经营时间
    TextView tv_end_time;
    @ViewInject(R.id.tv_yingye)//营业执照照片
    TextView tv_yingye;
    @ViewInject(R.id.tv_xuke)//经营许可证
    TextView tv_xuke;
    @ViewInject(R.id.tv_zheng)//身份证正面
    TextView tv_zheng;
    @ViewInject(R.id.tv_fan)//身份证反面
    TextView tv_fan;

    private AlertDialog dialog;

    //请求相关的参数

    private String latitude = "";
    private String longitude = "";
    private String introduction = "";

    private String doorimg="";//门店
    private String businessimg="";//营业执照
    private String licenseimg="";//许可证
    private String idcardnofrontimg="";//身份证正面
    private String idcardnobackimg="";//身份证背面

    private String doorimg_file="";//门店
    private String businessimg_file="";//营业执照
    private String licenseimg_file="";//许可证
    private String idcardnofrontimg_file="";//身份证正面
    private String idcardnobackimg_file="";//身份证背面

    public static final String ADDRESS = "address";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";

    private View Partent;
    private LayoutInflater mInflater;

    private RateSeletorUtils reteSelect;//返币比例选择窗
    private SeletorUtils mAddressType;
    private SeletorUtils mLincesType;
    private SeletorUtils mContactType;
    private SeletorUtils mCategoryType;

    private String categoryid = "";
    private String contactid = "";
    private String addressid = "";
    private String licensid = "";

    @Override
    protected int getTitleText() {
        return R.string.shop_manage;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_merchant_particulars;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        super.initView();
        /**rightView*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        int rightMargin = LayoutUtils.dip2px(8);
        layoutParams.setMargins(0, 0, rightMargin, 0);
        TextView tv = new TextView(this);
        tv.setClickable(true);
        tv.setBackground(getResources().getDrawable(R.mipmap.ok));
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(Color.WHITE);
        setRightTitleView(tv, layoutParams);
        tv.setOnClickListener(v -> {
            if (checkData()) {
                submitData();
            }
        });

        mInflater = LayoutInflater.from(mActivity);
        Partent = mInflater.inflate(R.layout.v2_activity_merchant_particulars, null);

        reteSelect = new RateSeletorUtils(Partent, mActivity, R.layout.pop_select_money) {

            @Override
            protected void setAdapterItem(String str) {
                tv_shopreturnrate.setText(str);
            }
        };

        mAddressType = new SeletorUtils(Partent, mInflater, mActivity, 0) {

            @Override
            public void setAdapterItem(Object bean) {
                AddressTypeList data = ((AddressTypeList) bean);
                tv_address_type.setText(data.getVal());
                addressid = data.getAddressType();
            }
        };
        mLincesType = new SeletorUtils(Partent, mInflater, mActivity, 1) {
            @Override
            public void setAdapterItem(Object bean) {
                BusinessList data = ((BusinessList) bean);
                tv_licens_type.setText(data.getLicenseName());
                licensid = data.getLicenseId();
            }
        };
        mContactType = new SeletorUtils(Partent, mInflater, mActivity, 2) {
            @Override
            public void setAdapterItem(Object bean) {
                ContactTypeList data = ((ContactTypeList) bean);
                tv_contact_type.setText(data.getTypeName());
                contactid = data.getTypeId();
            }
        };
        mCategoryType = new SeletorUtils(Partent, mInflater, mActivity, 3) {
            @Override
            public void setAdapterItem(Object bean) {
                CategoryList data = ((CategoryList) bean);
                categoryid = data.getCategoryId();
                tv_category_type.setText(data.getName());

            }
        };
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
        getCategoryData();
    }

    //获取类别信息
    private void getCategoryData() {

        ResponseBuilder<BaseRequestParam, AllCategoryResponseObjecet> builder =
                new ResponseBuilder<>(new BaseRequestParam(), Config.SUPPLY_PRE, AllCategoryResponseObjecet.class);
        builder.setCallBack(new BaseNetCallBack<AllCategoryResponseObjecet>() {
            @Override
            public void onSuccess(AllCategoryResponseObjecet reponse) {
                mAddressType.setData(reponse.getData().getAddressTypeList());
                mLincesType.setData(reponse.getData().getBusinessList());
                mContactType.setData(reponse.getData().getContactTypeList());
                mCategoryType.setData(reponse.getData().getList());
            }
        }).send();
    }

    //提交数据
    private void submitData() {
        final Dialog dialog = Progress.createLoadingDialog(this, "");
        dialog.show();

        final ApplyShopRequestObject requsetObjcet = new ApplyShopRequestObject();
        ApplyShopRequestParam param = new ApplyShopRequestParam();
        final ApplyShopRequsetMember member = new ApplyShopRequsetMember();
        ApplyshopRequestShop shop = new ApplyshopRequestShop();

        //姓名，身份证，邮箱
        member.setName(et_realname.getText().toString().trim());
        member.setIdcardno(et_idcard.getText().toString().trim());
        member.setEmail(et_email.getText().toString().trim());

        //店名，电话，地址，社区电话代理，客服电话，
        shop.setShopname(et_shopname.getText().toString().trim());
        shop.setShopphone(et_shopphone.getText().toString().trim());
        shop.setAddr(et_shopaddr.getText().toString().trim());
        shop.setShopRefreePhone(et_communityphone.getText().toString().trim());
        shop.setServicePhone(et_servicephone.getText().toString().trim());
        //店铺简称，定位，营业范围，联系人类型,地址类型
        shop.setAliasName(et_shortname.getText().toString().trim());
        shop.setLatitude(latitude);
        shop.setLongitude(longitude);
        shop.setCategoryid(categoryid);
        shop.setContactType(contactid);
        shop.setAddressType(addressid);
        //返币比例，商家介绍，营业执照编号，营业执照类型，银行卡号，银行卡持卡人姓名
        shop.setShopreturnrate(tv_shopreturnrate.getText().toString().trim());
        shop.setIntroduction(tv_introduce.getText().toString().trim());
        shop.setBusinessLicense(et_businessnum.getText().toString().trim());
        shop.setBusinessLicenseType(licensid);
        shop.setCardNo(et_bankcradid.getText().toString().trim());
        shop.setCardName(et_bankcradname.getText().toString().trim());

        //开始时间，结束时间
        shop.setStartbusinesstime(tv_start_time.getText().toString().trim());
        shop.setEndbusinesstime(tv_end_time.getText().toString().trim());

        //店铺首页图，营业执照，经营许可证，身份证正面，身份证反面
        if(doorimg.startsWith("http:")){
            shop.setDoorimg("");
        }else{
            shop.setDoorimg(doorimg);
        }
        if(businessimg.startsWith("http:")){
            shop.setBusinessimg("");
        }else{
            shop.setBusinessimg(businessimg);
        }
        if(licenseimg.startsWith("http:")){
            shop.setLicenseimg("");
        }else{
            shop.setLicenseimg(licenseimg);
        }
        if(idcardnofrontimg.startsWith("http:")){
            shop.setIdcardnofrontimg("");
        }else{
            shop.setIdcardnofrontimg(idcardnofrontimg);
        }
        if(idcardnobackimg.startsWith("http:")){
            shop.setIdcardnobackimg("");
        }else{
            shop.setIdcardnobackimg(idcardnobackimg);
        }

        param.setType("2");
        param.setMember(member);
        param.setShop(shop);
        requsetObjcet.setParam(param);

        ResponseBuilder<ApplyShopRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requsetObjcet, Config.SUPPYLSHOP, BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject baseResponseObject) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(mActivity, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(BaseResponseObject responseObject) {
                super.onFailure(responseObject);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(mActivity, responseObject.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onServerFailure(String str) {
                super.onServerFailure(str);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }).send();

    }

    //检查数据是否都进行了合法的填写
    private boolean checkData() {
        if (TextUtils.isEmpty(et_realname.getText().toString().trim())) {
            Toast.makeText(mActivity, "请填写真实姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_shopname.getText().toString().trim())) {
            Toast.makeText(mActivity, "请填写店铺名称", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_shopaddr.getText().toString().trim())) {
            Toast.makeText(mActivity, "请完善店铺地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (latitude.equals("")) {
            Toast.makeText(mActivity, "请完善店铺地址定位信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_shopphone.getText().toString().trim())) {
            Toast.makeText(mActivity, "请填写店铺联系方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(doorimg)) {
            Toast.makeText(mActivity, "请上传店铺首页图", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(tv_start_time.getText().toString().trim())) {
            Toast.makeText(mActivity, "请填写开始营业时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(tv_start_time.getText().toString().trim())) {
            Toast.makeText(mActivity, "请填写结束营业时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
            Toast.makeText(mActivity, "请填写联系人邮箱", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void setListener() {
        super.setListener();
        iv_doorimg.setOnClickListener(this);
        et_shopaddr.setOnClickListener(this);
        tv_yingye.setOnClickListener(this);
        tv_xuke.setOnClickListener(this);
        tv_zheng.setOnClickListener(this);
        tv_fan.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_introduce.setOnClickListener(this);
        tv_location_addr.setOnClickListener(this);

        tv_address_type.setOnClickListener(this);
        tv_licens_type.setOnClickListener(this);
        tv_category_type.setOnClickListener(this);
        tv_contact_type.setOnClickListener(this);

        tv_shopreturnrate.setOnClickListener(this);

    }

    //获取商家详情数据
    private void getData() {
        ResponseBuilder<EmptyRequestObject, QueryTempResponseObjecet> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.QUERYTEMP,QueryTempResponseObjecet.class);
        builder.setCallBack(new BaseNetCallBack<QueryTempResponseObjecet>() {
            @Override
            public void onSuccess(QueryTempResponseObjecet Objecet) {
                saveData(Objecet.getData());
            }
        }).send();
    }

    private void saveData(QueryTempResponseData data) {

        if (!"".equals(data.getDoorimg())) {
            doorimg_file = data.getDoorimg();
            doorimg = data.getDoorimg();
            ImageUtils.display(iv_doorimg, doorimg_file);
        }
        et_realname.setText(data.getName());
        et_idcard.setText(data.getIdcardno());
        et_shopname.setText(data.getShopname());
        et_shopphone.setText(data.getShopPhone());
        et_shopaddr.setText(data.getAddr());
        et_communityphone.setText(data.getInvitecode());
        et_email.setText(data.getEmail());
        et_servicephone.setText(data.getServicephone());
        et_shortname.setText(data.getAliasname());

        if (!latitude.equals("")) {
            tv_location_addr.setText("定位成功");
        } else {
            tv_location_addr.setText("定位未成功");
        }

        tv_category_type.setText(data.getCategorynanme());
        categoryid = data.getCategoryid();
        tv_contact_type.setText(data.getContactname());
        contactid = data.getContacttype();
        tv_address_type.setText(data.getAddressname());
        addressid = data.getAddresstype();
        String discount=data.getDiscount();

        try {
            Double dd=Double.parseDouble(discount);
            Double sub=1-dd;
            tv_shopreturnrate.setText(sub + "");
        }catch (Exception e){
        }

        tv_introduce.setText(data.getIntroduction());
        et_businessnum.setText(data.getBusinesslicensename());
        licensid = data.getBusinesslicensetype();
        et_bankcradid.setText(data.getCardno());
        et_bankcradname.setText(data.getCardname());

        tv_start_time.setText(data.getStartbusinesstime());
        tv_end_time.setText(data.getEndbusinesstime());

        longitude=data.getLongitude();
        latitude=data.getLatitude();

        if ("".equals(data.getBusinessimg())) {
            tv_yingye.setText("未上传");
        } else {
            businessimg_file=data.getBusinessimg();
            businessimg = data.getBusinessimg();
            tv_yingye.setText("上传成功");
        }
        if ("".equals(data.getLicenseimg())) {
            tv_xuke.setText("未上传");
        } else {
            licenseimg_file = data.getLicenseimg();
            licenseimg=data.getLicenseimg();
            tv_xuke.setText("上传成功");
        }
        if ("".equals(data.getIdcardnofrontimg())) {
            tv_zheng.setText("未上传");
        } else {
            idcardnofrontimg = data.getIdcardnofrontimg();
           idcardnofrontimg_file=data.getIdcardnofrontimg();
            tv_zheng.setText("上传成功");
        }
        if ("".equals(data.getIdcardnobackimg())) {
            tv_fan.setText("未上传");
        } else {
            idcardnobackimg_file=data.getIdcardnobackimg();
            idcardnobackimg = data.getIdcardnobackimg();
            tv_fan.setText("上传成功");
        }
    }

    /**
     * 弹出日期时间选择框方法
     */
    public void dateTimePicKDialog(Context context, final TextView tv_time) {
        Log.i("wymmmm", "ssss");
        LinearLayout dateTimeLayout = (LinearLayout) View.inflate(context, R.layout.dialog_seletetime, null);
        final NumberPicker hourPicker = (NumberPicker) dateTimeLayout.findViewById(R.id.hourpicker);
        hourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        final NumberPicker minutePicker = (NumberPicker) dateTimeLayout.findViewById(R.id.minuteicker);
        minutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);
        hourPicker.setValue(12);

        minutePicker.setMaxValue(60);
        minutePicker.setMinValue(0);
        minutePicker.setValue(30);

        dialog = new AlertDialog.Builder(context)
                .setTitle("请选择时间:")
                .setView(dateTimeLayout)
                .setPositiveButton("确定", (dialog1, whichButton) -> {
                    dialog1.dismiss();
                    String hour;
                    String min;
                    String second;
                    if (hourPicker.getValue() < 10) {
                        hour = "0" + hourPicker.getValue();
                    } else {
                        hour = hourPicker.getValue() + "";
                    }
                    if (minutePicker.getValue() < 10) {
                        min = "0" + minutePicker.getValue();
                    } else {
                        min = minutePicker.getValue() + "";
                    }

                    tv_time.setText(" " + hour + ":" + min + ":00");

                }).setCancelable(false).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_doorimg:
                Intent intent11 = new Intent(mActivity, EditShopImgActivity.class);
                intent11.setAction("店铺首图");
                intent11.putExtra("path", doorimg_file);
                startActivityForResult(intent11, 11);
                break;
            case R.id.tv_yingye:
                Intent intent12 = new Intent(mActivity, EditShopImgActivity.class);
                intent12.setAction("营业执照");
                intent12.putExtra("path", businessimg_file);
                startActivityForResult(intent12, 12);
                break;
            case R.id.tv_xuke:
                Intent intent13 = new Intent(mActivity, EditShopImgActivity.class);
                intent13.setAction("经营许可证");
                intent13.putExtra("path", licenseimg_file);
                startActivityForResult(intent13, 13);
                break;
            case R.id.tv_zheng:
                Intent intent14 = new Intent(mActivity, EditShopImgActivity.class);
                intent14.setAction("身份证正面照");
                intent14.putExtra("path", idcardnofrontimg_file);
                startActivityForResult(intent14, 14);
                break;
            case R.id.tv_fan:
                Intent intent15 = new Intent(mActivity, EditShopImgActivity.class);
                intent15.setAction("身份证反面照");
                intent15.putExtra("path", idcardnobackimg_file);
                startActivityForResult(intent15, 15);
                break;
            case R.id.tv_location_addr:
                startActivityForResult(new Intent(mActivity, MapViewActivity.class),3);
                break;
            case R.id.tv_start_time:
                dateTimePicKDialog(mActivity, tv_start_time);
                break;
            case R.id.tv_end_time:
                dateTimePicKDialog(mActivity, tv_end_time);
                break;
            case R.id.tv_introduce:
                Intent intent = new Intent(mActivity, EditShopInfoActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_address_type:
                mAddressType.showPopUpwindow();
                break;
            case R.id.tv_licens_type:
                mLincesType.showPopUpwindow();
                break;
            case R.id.tv_category_type:
                mCategoryType.showPopUpwindow();
                break;
            case R.id.tv_contact_type:
                mContactType.showPopUpwindow();
                break;
            case R.id.tv_shopreturnrate://返币比例弹出选择
                reteSelect.showPopUpwindow();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 1) {
            introduction = data.getStringExtra("data");
            tv_introduce.setText(introduction);
        }
        if (resultCode == 2 && null != data) {
            switch (requestCode) {
                case 11:
                    doorimg = data.getStringExtra("path");
                    doorimg_file=data.getStringExtra("tem_path");
                    ImageUtils.display(iv_doorimg, doorimg_file);
                    break;
                case 12:
                    businessimg = data.getStringExtra("path");
                    businessimg_file=data.getStringExtra("tem_path");
                    tv_yingye.setText("已上传");
                    break;
                case 13:
                    licenseimg = data.getStringExtra("path");
                    licenseimg_file=data.getStringExtra("tem_path");
                    tv_xuke.setText("已上传");
                    break;
                case 14:
                    idcardnofrontimg = data.getStringExtra("path");
                    idcardnofrontimg_file=data.getStringExtra("tem_path");
                    tv_zheng.setText("已上传");
                    break;
                case 15:
                    idcardnobackimg = data.getStringExtra("path");
                    idcardnobackimg_file=data.getStringExtra("tem_path");
                    tv_fan.setText("已上传");
                    break;
            }
        }

        if(requestCode==3&&resultCode==1&&null!=data){
            latitude=data.getStringExtra(LATITUDE);
            longitude=data.getStringExtra(LONGITUDE);
//            tv_addr.setText(data.getStringExtra(ADDRESS));

            if(!latitude.equals("")){
                tv_location_addr.setText("定位成功");
            }else{
                tv_location_addr.setText("定位未成功");
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteFile(new File(EditShopImgActivity.CAMERA_TEMP_DIR));

    }

    private void deleteFile(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteFile(file); // 递归
            }
        }
        dir.delete();
    }
}
