package com.youyou.xiaofeibao.version2.home.shop.store;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.common.ImageUtils;
import com.youyou.xiaofeibao.common.LayoutUtils;
import com.youyou.xiaofeibao.common.MainThreadPostUtils;
import com.youyou.xiaofeibao.common.StringUtil;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.net.CommonOkHttpClient;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.tool.BitmapUtils;
import com.youyou.xiaofeibao.version2.tool.Progress;

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
 * 作者：young on 2016/12/26 15:25
 */

public class EditShopImgActivity extends BaseTitleActivity {


    public static final String CAMERA_TEMP_PATH = Environment.getExternalStorageDirectory() + "/xfb/pic/temppic" + ".jpg";
    public static final String CAMERA_TEMP_DIR = Environment.getExternalStorageDirectory() + "/xfb/pic/";

    @ViewInject(R.id.iv_img)
    ImageView iv_img;
    private String filePath;
    private String tem_file;

    private String iv_path;

    private TextView tv_camera;
    private TextView tv_gallery;

    private AlertDialog mAlertDialog;

    private RelativeLayout view;

    @Override
    protected int getTitleText() {
        return R.string.edit_shopimg;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_shopimg;
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
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != filePath && !"".equals(filePath)) {
                    Intent intent = new Intent();
                    intent.putExtra("path", filePath);
                    intent.putExtra("tem_path", tem_file);
                    setResult(2, intent);
                    finish();
                }
            }
        });

        view = (RelativeLayout) View.inflate(mActivity, R.layout.v2_alertdialog_selectpic, null);

        tv_camera = (TextView) view.findViewById(R.id.confirmFirstButton);
        tv_gallery = (TextView) view.findViewById(R.id.confirmSecondButton);

        mAlertDialog = new AlertDialog.Builder(mActivity)
                .setTitle("请选择照片:")
                .setView(view)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).setCancelable(true).create();

    }

    @Override
    protected void initData() {
        super.initData();
        setTitle(getIntent().getAction());

        iv_path = getIntent().getStringExtra("path");

        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });

        ImageUtils.display(iv_img, iv_path);

    }

    @Override
    protected void setListener() {
        super.setListener();

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (hasPermission(strings)) {
                    openCamra(2);
                } else {
                    requestPermission(Config.PERMISSION_CAMERA, strings);
                }

                mAlertDialog.hide();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    doPickPhotoFromGallery(1);
                } else {
                    requestPermission(Config.PERMISSION_FILE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                mAlertDialog.hide();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    String filepath_album1 = CAMERA_TEMP_PATH;
                    File desfile = createPicFile(CAMERA_TEMP_DIR);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album1, desfile.getAbsolutePath())) {
                        ImageUtils.display(iv_img, desfile.getAbsolutePath());
                        uploadFile(desfile);
                    } else {
                        if (desfile.exists()) {
                            desfile.delete();
                        }
                    }
                }
                break;
            case 1:
                if (data != null) {
                    Uri uri_album = data.getData();
                    String filepath_album = BitmapUtils.getInstance().getPathByUri(mActivity, uri_album);
                    File desfile = createPicFile(CAMERA_TEMP_DIR);
                    if (Config.PIC_OK == BitmapUtils.getInstance().getimage_return(filepath_album, desfile.getAbsolutePath())) {
                        ImageUtils.display(iv_img, desfile.getAbsolutePath());
                        uploadFile(desfile);
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
//        Uri uri = Uri.fromFile(file);

        Uri uri = FileProvider.getUriForFile(EditShopImgActivity.this, "com.youyou.xiaofeibao.fileprovider", file);//通过FileProvider创建一个content类型的Uri

        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent1, requestcode);
    }

//    //将图片以流的形式进行上传
//    private void uploadFile(final File file) {
//        final Dialog dialog = Progress.createLoadingDialog(this, "");
//        //RequestParams对象是用来存放请求参数的
//        dialog.show();
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("file", file, "image/jpg");//这里才是重点上传文件的参数
//        //HttpUtils网络请求
//        HttpUtils http = new HttpUtils();
//        //发送请求
//        /**
//         * 第一个参数：请求方式
//         *第二个参数：请求地址
//         *第三个参数：请求携带的参数类
//         *第四个参数：网络请求的监听
//         */
//        http.send(HttpRequest.HttpMethod.POST, Config.UPLOAD, params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(final ResponseInfo<String> responseInfo) {
//
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled() {
//                super.onCancelled();
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//
//            }
//        });
//    }


    //文件的上传
    private void uploadFile(final File file) {

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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity,"上传失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                final String strResponse=response.body().string();
                MainThreadPostUtils.post(new Runnable() {
                    @Override
                    public void run() {

                        JSONObject jsonObject = JSONObject.parseObject(strResponse);
                        if (jsonObject != null) {
                            JSONObject param = jsonObject.getJSONObject("data");
                            filePath = param.getString("filePath");
                            tem_file = file.getAbsolutePath();
                        }
                    }
                });
            }
        });

    }



}
