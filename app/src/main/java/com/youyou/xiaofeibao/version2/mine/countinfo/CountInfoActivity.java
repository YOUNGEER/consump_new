package com.youyou.xiaofeibao.version2.mine.countinfo;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.common.MainThreadPostUtils;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.net.CommonOkHttpClient;
import com.youyou.xiaofeibao.util.CircularImage;
import com.youyou.xiaofeibao.util.ProgressUtils;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.login.ForgetPwdActivity;
import com.youyou.xiaofeibao.version2.request.imgurl.ImgUrlRequestObject;
import com.youyou.xiaofeibao.version2.request.imgurl.ImgUrlRequestParam;
import com.youyou.xiaofeibao.version2.request.unbindwx.UnbindwxRequestObject;
import com.youyou.xiaofeibao.version2.request.unbindwx.UnbindwxRequestParam;
import com.youyou.xiaofeibao.version2.response.BaseResponseObject;
import com.youyou.xiaofeibao.version2.response.namechange.NameRequestObject;
import com.youyou.xiaofeibao.version2.widget.PopupWindows;
import com.youyou.xiaofeibao.version2.zfb.ZfbBandListener;
import com.youyou.xiaofeibao.version2.zfb.ZfbLogin;
import com.youyou.xiaofeibao.wxapi.WechatLogin;

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
 * 作者：young on 2016/12/7 10:05
 */

public class CountInfoActivity extends BaseTitleActivity implements View.OnClickListener,ZfbBandListener {

    @ViewInject(R.id.ll_pwd)
    LinearLayout ll_pwd;
    @ViewInject(R.id.ll_changeimg)
    LinearLayout ll_changeimg;
    @ViewInject(R.id.iv_img)
    CircularImage iv_img;
    @ViewInject(R.id.et_name)
    EditText et_name;
    @ViewInject(R.id.tv_phone)
    TextView tv_phone;
    @ViewInject(R.id.tv_wx)
    TextView tv_wx;
    @ViewInject(R.id.tv_zfb)
    TextView tv_zfb;

    private PopupWindows mPopupWindows;

    // 相机拍照暂存文件
    private File mPictureFromCamera;
    // 裁剪所得暂存文件
    private File mPictureFromCrop;
    // 相机拍照所得文件名
    private final String PICTURE_FROM_CAMERA_FILE_NAME = "pciture_from_camera";
    // 裁剪所得文件名
    private final String PICTURE_FROM_CROP_FILE_NAME = "picture_from_crop";
    // 相机拍照成功返回码
    private final int TAKE_PICTURE_REQUEST_CODE = 0xFF;
    // 手机相册选取照片返回码
    private final int CHOOSE_PICTURE_FROM_DEVICE = 0xFE;
    // 裁剪照片返回码
    private final int CROP_PICTURE_CODE = 0xFD;

    private String pic_url = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private  Dialog mProgressUtils=null;


    @Override

    protected int getTitleText() {
        return R.string.count_info;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_countinfo;
    }

    @Override
    protected void initData() {
        super.initData();

        mPopupWindows = new PopupWindows(this);
        ImageUtils.display(iv_img, PreferencesConfig.v2_url.get());
        et_name.setText(PreferencesConfig.v2_loginName.get());
        String phone= PreferencesConfig.v2_phone.get();

        tv_phone.setText(phone.substring(0,3)+"****"+phone.subSequence(phone.length()-3,phone.length()));

        if(PreferencesConfig.v2_iswx.get().equals("0")){//0表示未绑定
            tv_wx.setText("去绑定");
        }else {
            tv_wx.setText("已绑定");
        }

        if(PreferencesConfig.v2_iszfb.get().equals("0")){
            tv_zfb.setText("去绑定");
        }else {
            tv_zfb.setText("已绑定");
        }

        tv_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PreferencesConfig.v2_iswx.get().equals("1")){
                    showDialog("确认解绑定微信号？","确认解绑");
                }else {
                    showDialog("确认绑定微信号？","确认绑定");
                }
            }
        });

        tv_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PreferencesConfig.v2_iszfb.get().equals("1")){
                    showDialog_zfb("确认解绑定支付宝号？","确认解绑");
                }else {
                    showDialog_zfb("确认绑定支付宝号？","确认绑定");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null!=mProgressUtils&&mProgressUtils.isShowing()){
            mProgressUtils.dismiss();
        }

        if(PreferencesConfig.v2_iswx.get().equals("0")){
            tv_wx.setText("去绑定");
        }else {
            tv_wx.setText("已绑定");
        }


    }



    private Dialog dia;

    private void showDialog(String title,String text){
        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
        dia=builder.setTitle(title)
                .setPositiveButton(text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dia.dismiss();
                        if(PreferencesConfig.v2_iswx.get().equals("1")){
                            unbindwx("wx");
                        }else {
                            showDialog();
                            new WechatLogin(mActivity).bindwx();
                        }
                    }
                }).create();
        dia.show();
    }

    public  void showDialog(){
        if(null==mProgressUtils){
            mProgressUtils= ProgressUtils.createLoadingDialog(mActivity);
        }
        if(!mProgressUtils.isShowing()){
            mProgressUtils.show();
        }
    }


    private Dialog dia_zfb;

    private void showDialog_zfb(String title,String text){
        AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
        dia_zfb=builder.setTitle(title)
                .setPositiveButton(text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dia_zfb.dismiss();
                        if(PreferencesConfig.v2_iszfb.get().equals("1")){
                            unbindwx("zfb");
                        }else {
                            ZfbLogin.getInstrance(mActivity).authV2();
                            ZfbLogin.getInstrance(mActivity).setListener(CountInfoActivity.this);
                        }
                    }
                }).create();
        dia_zfb.show();

    }


    /**
     *解除微信绑定
     */
    private void unbindwx(final String type) {
        UnbindwxRequestObject requestObject = new UnbindwxRequestObject();
        UnbindwxRequestParam param = new UnbindwxRequestParam();
        param.setType(type);
        param.setPhone(PreferencesConfig.v2_phone.get());
        requestObject.setParam(param);
        ResponseBuilder<UnbindwxRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.UNBINDWX,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject baseResponseObject) {
                if(type.equals("wx")){
                    tv_wx.setText("去绑定");
                    PreferencesConfig.v2_iswx.set("0");
                }else{
                    tv_zfb.setText("去绑定");
                    PreferencesConfig.v2_iszfb.set("0");
                }

                Toast.makeText(mActivity,"解绑定成功",Toast.LENGTH_SHORT).show();
            }
        }).send();
    }

    @Override
    protected void setListener() {
        super.setListener();
        ll_pwd.setOnClickListener(this);
        ll_changeimg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pwd:
                startActivity(new Intent(mActivity, ForgetPwdActivity.class));
                break;
            case R.id.ll_changeimg:
                showPopupWindows();
                break;
        }

    }

    private void showPopupWindows() {
        //从底部弹起
        //拍照
        mPopupWindows.setConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                if (hasPermission(strings)) {
                    openCamra();
                } else {
                    requestPermission(Config.PERMISSION_CAMERA, strings);
                }

                mPopupWindows.dismiss();
            }
        });
        //相册
        mPopupWindows.setConfirmSecondClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    openGallery();
                } else {
                    requestPermission(Config.PERMISSION_FILE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                mPopupWindows.dismiss();
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE_REQUEST_CODE:
                    if (createCropFile()) {

                        Uri camera = FileProvider.getUriForFile(mActivity, "com.youyou.xiaofeibao.fileprovider", mPictureFromCamera);//通过FileProvider创建一个content类型的Uri
                        Uri crop = FileProvider.getUriForFile(mActivity, "com.youyou.xiaofeibao.fileprovider", mPictureFromCrop);//通过FileProvider创建一个content类型的Uri


//                        cropImage(Uri.fromFile(mPictureFromCamera),
//
//                                Uri.fromFile(mPictureFromCrop));
                        cropImage(camera, crop);

                    }
                    break;
                case CHOOSE_PICTURE_FROM_DEVICE:
                    if (createCropFile()) {

                        Uri crop = FileProvider.getUriForFile(mActivity, "com.youyou.xiaofeibao.fileprovider", mPictureFromCrop);//通过FileProvider创建一个content类型的Uri

//                        cropImage(data.getData(), Uri.fromFile(mPictureFromCrop));
                        cropImage(data.getData(), crop);
                    }
                    break;
                case CROP_PICTURE_CODE:

                    iv_img.setImageBitmap(BitmapFactory
                            .decodeFile(mPictureFromCrop
                                    .getAbsolutePath()));


                    Bitmap bitmap = BitmapFactory.decodeFile(mPictureFromCrop.getAbsolutePath());

                    uploadFile();
                    break;
            }
        }
    }

    //文件的上传
    private void uploadFile() {

        OkHttpClient client = CommonOkHttpClient.getmOkHttpClient();
        final MediaType MEDIA_TYPE_TEXT = MediaType.parse("image/png");
        MultipartBody multipartBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",mPictureFromCrop.getName(), RequestBody.create(MEDIA_TYPE_TEXT,mPictureFromCrop))
                .build();

        Request request = new Request.Builder()
                .url(Config.UPLOAD)
                .post(multipartBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MainThreadPostUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "网络错误，请重新上传",Toast.LENGTH_SHORT).show();
                    }
});}

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                MainThreadPostUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = JSONObject.parseObject(response.body().string());
                        } catch (IOException e) {
                            Toast.makeText(mActivity, "网络错误，请重新上传",Toast.LENGTH_SHORT).show();
                            return;

                        }
                        if (jsonObject != null) {
                            JSONObject param = jsonObject.getJSONObject("data");
                            String file = param.getString("filePath");
                            saveImgUrl(file);
                        }
                    }
                });
            }
        });

    }

    private void saveImgUrl(String path) {
        ImgUrlRequestObject requestObject = new ImgUrlRequestObject();
        ImgUrlRequestParam param = new ImgUrlRequestParam();
        param.setImgUrl(path);
        requestObject.setParam(param);
        ResponseBuilder<ImgUrlRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.IMGURL,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject baseResponseObject) {
                PreferencesConfig.v2_url.set(mPictureFromCrop.getAbsolutePath());
            }
        }).send();
    }

    public void openCamra() {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        // 相机拍照
        Intent takePicture = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File pictures = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            mPictureFromCamera = new File(pictures,
                    PICTURE_FROM_CAMERA_FILE_NAME);
            if (!mPictureFromCamera.exists()) {
                mPictureFromCamera.createNewFile();
            }

            Uri camera = FileProvider.getUriForFile(mActivity, "com.youyou.xiaofeibao.fileprovider", mPictureFromCamera);//通过FileProvider创建一个content类型的Uri

//            takePicture.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Uri.fromFile(mPictureFromCamera));
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT,
                    camera);
        } catch (IOException e) {
            return;
        }
        startActivityForResult(takePicture,
                TAKE_PICTURE_REQUEST_CODE);
    }

    public void openGallery() {

        // 手机相册
        Intent choosePicture = new Intent(Intent.ACTION_PICK);
        choosePicture.setType("image/*");
        startActivityForResult(choosePicture,
                CHOOSE_PICTURE_FROM_DEVICE);
    }

    public void onConfigurationChanged(Configuration newConfig) {

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Configuration o = newConfig;
            o.orientation = Configuration.ORIENTATION_PORTRAIT;
            newConfig.setTo(o);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 裁剪指定图片
     */
    private void cropImage(Uri source, Uri result) {
        Intent i = new Intent("com.android.camera.action.CROP");
        i.setDataAndType(source, "image/*");
        i.putExtra("aspectX", 1);
        i.putExtra("aspectY", 1);
        i.putExtra("outputX", 640);
        i.putExtra("outputY", 640);
        i.putExtra(MediaStore.EXTRA_OUTPUT, result);
        startActivityForResult(i, CROP_PICTURE_CODE);
    }

    /**
     * 创建保存裁剪所得图片文件
     */
    private boolean createCropFile() {
        if (mPictureFromCrop != null && mPictureFromCrop.exists()) {
            return true;
        }
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return false;
        }
        if (mPictureFromCrop == null) {
            mPictureFromCrop = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    PICTURE_FROM_CROP_FILE_NAME + ".jpg");
        }
        try {
            if (!mPictureFromCrop.exists()) {
                mPictureFromCrop.createNewFile();
            }
        } catch (IOException e) {

            return false;
        }
        return true;
    }



    @Override
    protected void onStop() {
        super.onStop();

        NameRequestObject requestObject = new NameRequestObject();
        NameRequestObject.ParamBean bean = new NameRequestObject.ParamBean();
        bean.setLoginName(et_name.getText().toString().trim());
        requestObject.setParam(bean);

        ResponseBuilder<NameRequestObject, BaseResponseObject> builder = new ResponseBuilder<>(requestObject, Config.NAMECHANGE,BaseResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<BaseResponseObject>() {
            @Override
            public void onSuccess(BaseResponseObject baseResponseObject) {
                PreferencesConfig.v2_loginName.set(et_name.getText().toString().trim());
            }
        }).send();


    }

    @Override
    public void Success() {
        if(PreferencesConfig.v2_iszfb.get().equals("0")){
            tv_zfb.setText("去绑定");
        }else {
            tv_zfb.setText("已绑定");
        }
    }
}
