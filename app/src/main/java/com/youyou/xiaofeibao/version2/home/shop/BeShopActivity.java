package com.youyou.xiaofeibao.version2.home.shop;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.IDCard;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.common.MainThreadPostUtils;
import com.youyou.xiaofeibao.common.StringUtil;
import com.youyou.xiaofeibao.framework.activity.KeyBoardAutoDownActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.net.CommonOkHttpClient;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.alliance.adapter.CustomSelectAdapter;
import com.youyou.xiaofeibao.version2.alliance.adapter.CustomSelectBean;
import com.youyou.xiaofeibao.version2.home.shop.store.MapViewActivity;
import com.youyou.xiaofeibao.version2.request.BaseRequestParam;
import com.youyou.xiaofeibao.version2.request.applyshop.ApplyShopRequestObject;
import com.youyou.xiaofeibao.version2.request.applyshop.ApplyShopRequestParam;
import com.youyou.xiaofeibao.version2.request.applyshop.ApplyShopRequsetMember;
import com.youyou.xiaofeibao.version2.request.applyshop.ApplyshopRequestShop;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.shopcategory.ShopCategoryResponseObject;
import com.youyou.xiaofeibao.version2.tool.BitmapUtils;
import com.youyou.xiaofeibao.version2.tool.Progress;
import com.youyou.xiaofeibao.version2.widget.PopupWindows;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：young on 2016/11/30 11:53
 */

public class BeShopActivity extends KeyBoardAutoDownActivity implements View.OnClickListener {

    public static final String CAMERA_TEMP_PATH = Environment.getExternalStorageDirectory() + "/xfb/pic/temppic" + ".jpg";
    public static final String PATH = Environment.getExternalStorageDirectory() + "/xfb/pic";
    public static final String ZHENG_PATH = Environment.getExternalStorageDirectory() + "/xfb/pic/zheng";
    public static final String FAN_PATH = Environment.getExternalStorageDirectory() + "/xfb/pic/fan";

    public static final String YINGYE_PATH = Environment.getExternalStorageDirectory() + "/xfb/pic/yingye";
    public static final String OTHER_PATH = Environment.getExternalStorageDirectory() + "/xfb/pic/other";

    private final int card_zheng_camera = 0x11;
    private final int card_zheng_gallery = 0x12;
    private final int card_fan_camera = 0x21;
    private final int card_fan_gallery = 0x22;
    private final int yingye_camera = 0x31;
    private final int yingye_gallery = 0x32;
    private final int other_camera = 0x41;
    private final int other_gallery = 0x42;

    private final int LOCATION=0x01;
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    private String latitude = "";
    private String longitude = "";


    @ViewInject(R.id.et_name)//真实姓名
    EditText et_name;
    @ViewInject(R.id.et_cdcard)//身份证
    EditText et_cdcard;
    @ViewInject(R.id.et_shopname)//店名
    EditText et_shopname;
    @ViewInject(R.id.et_shopphone)//手机号
    EditText et_shopphone;
    @ViewInject(R.id.et_shopaddr)//店铺地址
    EditText et_shopaddr;
    @ViewInject(R.id.tv_juti_addr)//定位地址
    TextView tv_juti_addr;
    @ViewInject(R.id.tv_category)//经营类型
    TextView tv_category;



    @ViewInject(R.id.tv_sure)
    TextView tv_sure;

    @ViewInject(R.id.iv_zheng)
    ImageView iv_zheng;
    @ViewInject(R.id.iv_fan)
    ImageView iv_fan;
    @ViewInject(R.id.tv_yingye)
    ImageView tv_yingye;
    @ViewInject(R.id.tv_other)
    ImageView tv_other;


    private String cateid = "";
    private String cateName = "";

    private String zhengpath = "";
    private String fanpath = "";
    private String yingyepath = "";
    private String otherpath = "";

    private PopupWindows mPopupWindows;

    private PopupWindow catePop;
    private ListView listView;
    private CustomSelectAdapter adapter;
    private View Partent;


    @Override
    protected int getTitleText() {
        return R.string.shenqing;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_beshop;
    }

    @Override
    protected void initData() {
        super.initData();
        mPopupWindows = new PopupWindows(this);
        View categoryPopupView = View.inflate(mActivity, R.layout.pop_select_money, null);
        catePop = new PopupWindow(categoryPopupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        listView = (ListView) categoryPopupView.findViewById(R.id.listview_pop);
        adapter = new CustomSelectAdapter(mActivity, R.layout.list_select_pop);
        listView.setAdapter(adapter);
        catePop.setBackgroundDrawable(new BitmapDrawable());
        catePop.setOutsideTouchable(false);
        Partent = LayoutInflater.from(mActivity).inflate(R.layout.v2_activity_beshop, null);
        getCategoryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomSelectBean bean = adapter.getItem(position);
                cateid = bean.getCategoryId();
                Log.i("ssssssss", "ewefs" + cateid);
                cateName = bean.getName();
                tv_category.setText(cateName);
                if (catePop.isShowing()) {
                    catePop.dismiss();
                }
            }
        });

    }

    //获取类别信息
    private void getCategoryData() {

        ResponseBuilder<BaseRequestParam, ShopCategoryResponseObject> builder =
                new ResponseBuilder<>(new BaseRequestParam(), Config.CATEGORY,ShopCategoryResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<ShopCategoryResponseObject>() {
            @Override
            public void onSuccess(ShopCategoryResponseObject reponse) {
                adapter.setList(reponse.getData().getCategorys());
            }
        }).send();
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_sure.setOnClickListener(this);
        tv_category.setOnClickListener(this);
        iv_zheng.setOnClickListener(this);
        iv_fan.setOnClickListener(this);
        tv_yingye.setOnClickListener(this);
        tv_other.setOnClickListener(this);
        tv_juti_addr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:

                if (checkData()) {
                    submitDataByUrl();
                }

                break;
            case R.id.tv_category:
                catePop.showAtLocation(Partent, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_zheng:
                showPopupWindows(0);
                break;
            case R.id.iv_fan:
                showPopupWindows(1);
                break;
            case R.id.tv_yingye:
                showPopupWindows(2);
                break;
            case R.id.tv_other:
                showPopupWindows(3);
                break;
            case R.id.tv_juti_addr:
                startActivityForResult(new Intent(mActivity, MapViewActivity.class),LOCATION);
                break;
        }
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(et_name.getText().toString())) {
            Toast.makeText(mActivity, R.string.hint_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!TextUtils.isEmpty(et_cdcard.getText().toString())&&!new IDCard().verify(et_cdcard.getText().toString().trim())) {
            Toast.makeText(mActivity, "身份证格式不正确或不存在该身份证号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_shopname.getText().toString())) {
            Toast.makeText(mActivity, R.string.hint_shopname, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_shopphone.getText().toString())) {
            Toast.makeText(mActivity, R.string.hint_shopphone, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(et_shopaddr.getText().toString())) {
            Toast.makeText(mActivity, R.string.hint_shopaddr, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (latitude.equals("")) {
            Toast.makeText(mActivity, "请完善店铺地址定位信息", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(tv_category.getText().toString())) {
            Toast.makeText(mActivity, R.string.hint_categoiory, Toast.LENGTH_SHORT).show();
            return false;
        }

        if ("".equals(otherpath)) {
            Toast.makeText(mActivity, "请上传店铺首页图", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //提交数据
    private void submitDataByUrl() {
        final android.app.Dialog dialog= Progress.createLoadingDialog(mActivity,"");
        dialog.show();
        final ApplyShopRequestObject requsetObjcet = new ApplyShopRequestObject();
        ApplyShopRequestParam param = new ApplyShopRequestParam();
        final ApplyShopRequsetMember member = new ApplyShopRequsetMember();
        ApplyshopRequestShop shop = new ApplyshopRequestShop();

        member.setName(et_name.getText().toString().trim());
        member.setIdcardno(et_cdcard.getText().toString().trim());
        member.setPhone(et_shopphone.getText().toString().trim());

        shop.setShopname(et_shopname.getText().toString().trim());
        shop.setCategoryid(cateid);
        shop.setAddr(et_shopaddr.getText().toString().trim());
        shop.setLatitude(latitude);
        shop.setLongitude(longitude);
        shop.setDoorimg(otherpath);

        shop.setIdcardnofrontimg(zhengpath);
        shop.setIdcardnobackimg(fanpath);
        shop.setBusinessimg(yingyepath);
//        shop.setLicenseimg(otherpath);

        param.setType("1");
        param.setMember(member);
        param.setShop(shop);
        requsetObjcet.setParam(param);
        ResponseBuilder<ApplyShopRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requsetObjcet, Config.SUPPYLSHOP,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject Object) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(mActivity, Object.getMsg(), Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();
            }

            @Override
            public void onFailure(BaseResponseObject Object) {
                super.onFailure(Object);
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(mActivity, Object.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }).send();

    }

    private void showPopupWindows(final int code) {
        //从底部弹起
        //拍照
        mPopupWindows.setConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                if (hasPermission(strings)) {
                    switch (code) {
                        case 0:
                            openCamra(card_zheng_camera);
                            break;
                        case 1:
                            openCamra(card_fan_camera);
                            break;
                        case 2:
                            openCamra(yingye_camera);
                            break;
                        case 3:
                            openCamra(other_camera);
                            break;
                    }
                    mPopupWindows.dismiss();
                } else {
                    requestPermission(Config.PERMISSION_CAMERA, strings);
                }

            }
        });
        //相册
        mPopupWindows.setConfirmSecondClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    switch (code) {
                        case 0:
                            doPickPhotoFromGallery(card_zheng_gallery);
                            break;
                        case 1:
                            doPickPhotoFromGallery(card_fan_gallery);
                            break;
                        case 2:
                            doPickPhotoFromGallery(yingye_gallery);
                            break;
                        case 3:
                            doPickPhotoFromGallery(other_gallery);
                            break;
                    }
                    mPopupWindows.dismiss();
                } else {
                    requestPermission(Config.PERMISSION_FILE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });

        mPopupWindows.setCancelFirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindows.dismiss();
            }
        });
        mPopupWindows.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOCATION:
                if(resultCode==1&&null!=data){

                    latitude=data.getStringExtra(LATITUDE);
                    longitude=data.getStringExtra(LONGITUDE);

                    if(!latitude.equals("")){
                        tv_juti_addr.setText("定位成功");
                    }else{
                        tv_juti_addr.setText("定位未成功");
                    }
                }
                break;
            case card_zheng_camera:
                if (resultCode == RESULT_OK) {
                    String filepath_album1 = CAMERA_TEMP_PATH;
                    File desfile = createPicFile(ZHENG_PATH);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album1, desfile.getAbsolutePath())) {
                        ImageUtils.display(iv_zheng, desfile.getAbsolutePath());
                        uploadFile(desfile, card_zheng_camera);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
            case card_fan_camera:
                if (resultCode == RESULT_OK) {
                    String filepath_album1 = CAMERA_TEMP_PATH;
                    File desfile = createPicFile(FAN_PATH);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album1, desfile.getAbsolutePath())) {
                        ImageUtils.display(iv_fan, desfile.getAbsolutePath());
                        uploadFile(desfile, card_fan_camera);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
            case yingye_camera:
                if (resultCode == RESULT_OK) {
                    String filepath_album1 = CAMERA_TEMP_PATH;
                    File desfile = createPicFile(YINGYE_PATH);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album1, desfile.getAbsolutePath())) {
                        ImageUtils.display(tv_yingye, desfile.getAbsolutePath());
                        uploadFile(desfile, yingye_camera);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
            case other_camera:
                if (resultCode == RESULT_OK) {
                    String filepath_album1 = CAMERA_TEMP_PATH;
                    File desfile = createPicFile(OTHER_PATH);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album1, desfile.getAbsolutePath())) {
                        ImageUtils.display(tv_other, desfile.getAbsolutePath());
                        uploadFile(desfile, other_camera);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
            case card_zheng_gallery:
                if (data != null) {
                    Uri uri_album = data.getData();
                    String filepath_album = BitmapUtils.getInstance().getPathByUri(mActivity, uri_album);
                    File desfile = createPicFile(ZHENG_PATH);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album, desfile.getAbsolutePath())) {
                        ImageUtils.display(iv_zheng, desfile.getAbsolutePath());
                        uploadFile(desfile, card_zheng_gallery);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
            case card_fan_gallery:
                if (data != null) {
                    Uri uri_album = data.getData();
                    String filepath_album = BitmapUtils.getInstance().getPathByUri(mActivity, uri_album);
                    File desfile = createPicFile(FAN_PATH);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album, desfile.getAbsolutePath())) {
                        ImageUtils.display(iv_fan, desfile.getAbsolutePath());
                        uploadFile(desfile, card_fan_gallery);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
            case yingye_gallery:
                if (data != null) {
                    Uri uri_album = data.getData();
                    String filepath_album = BitmapUtils.getInstance().getPathByUri(mActivity, uri_album);
                    File desfile = createPicFile(YINGYE_PATH);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album, desfile.getAbsolutePath())) {
                        ImageUtils.display(tv_yingye, desfile.getAbsolutePath());
                        uploadFile(desfile, yingye_gallery);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
            case other_gallery:
                if (data != null) {
                    Uri uri_album = data.getData();
                    String filepath_album = BitmapUtils.getInstance().getPathByUri(mActivity, uri_album);
                    File desfile = createPicFile(OTHER_PATH);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album, desfile.getAbsolutePath())) {
                        ImageUtils.display(tv_other, desfile.getAbsolutePath());
                        uploadFile(desfile, other_gallery);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
        }
    }


    /**
     * 请求Gallery相册程序
     */
    public void doPickPhotoFromGallery(int requestcode) {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
            StringUtil.toastStringShort(R.string.toast_picutil_error);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestcode);
    }

    /**
     * open Camera
     */
    private void openCamra(int requestcode) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        // 相机拍照
        // 加载路径
        File file = new File(CAMERA_TEMP_PATH);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Uri uri = FileProvider.getUriForFile(mActivity, "com.youyou.xiaofeibao.fileprovider", file);//通过FileProvider创建一个content类型的Uri

        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent1, requestcode);
    }

    //文件的上传
    private void uploadFile(File file, final int requestCode) {

        //RequestParams对象是用来存放请求参数的
        final android.app.Dialog dialog= Progress.createLoadingDialog(mActivity,"");
        dialog.show();

        OkHttpClient client = CommonOkHttpClient.getmOkHttpClient();
        final MediaType MEDIA_TYPE_TEXT = MediaType.parse("image/png");
//        File file=new File("wy.png");
        MultipartBody multipartBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(), RequestBody.create(MEDIA_TYPE_TEXT,file))
                .build();

        Request request = new Request.Builder()
                .url(Config.UPLOAD)
                .post(multipartBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mActivity, "网络错误，请重新上传",Toast.LENGTH_SHORT).show();
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                final String strResponse=response.body().string();
                MainThreadPostUtils.post(new Runnable() {
                    @Override
                    public void run() {

                        JSONObject jsonObject = JSONObject.parseObject(strResponse);
                        if (jsonObject != null) {
                            JSONObject param = jsonObject.getJSONObject("data");
                            String filepath = param.getString("filePath");
                            Log.i("sssssss", "onFailure: 上传成功");
                            switch (requestCode) {
                                case card_zheng_camera:
                                case card_zheng_gallery:
                                    zhengpath = filepath;
                                    break;
                                case card_fan_camera:
                                case card_fan_gallery:
                                    fanpath = filepath;
                                    break;
                                case yingye_camera:
                                case yingye_gallery:
                                    yingyepath = filepath;
                                    break;
                                case other_camera:
                                case other_gallery:
                                    otherpath = filepath;
                                    break;
                            }
                        }
                    }
                });
            }
        });

    }

    /**
     * new File
     */
    public File createPicFile(String path) {
        File file = new File(path, System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        File file = new File(PATH);
        if (file.exists() || file.isDirectory()) {
            deleteFile(file);
        }
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
