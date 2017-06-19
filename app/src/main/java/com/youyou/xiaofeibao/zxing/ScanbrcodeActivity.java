package com.youyou.xiaofeibao.zxing;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.Dialog;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;

import com.youyou.xiaofeibao.version2.pay.PayActivityPre;
import com.youyou.xiaofeibao.version2.pay.pwd.PayActivityPre2;
import com.youyou.xiaofeibao.zxing.camera.CameraManager;
import com.youyou.xiaofeibao.zxing.decoding.CaptureActivityHandler;
import com.youyou.xiaofeibao.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by yun on 16/3/23.
 */
public class ScanbrcodeActivity extends BaseTitleActivity {
    private CaptureActivityHandler handler;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private static final long VIBRATE_DURATION = 200L;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private boolean hasSurface;
    private String resultString;

    @ViewInject(R.id.viewfinder_view)
    private ViewfinderView viewfinderView;

    @Override
    protected int getTitleText() {
        return R.string.home_scan;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_code_scanning;
    }
    private SurfaceHolder surfaceHolder;

    SurfaceHolder.Callback mCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (!hasSurface) {
                hasSurface = true;
                initCamera(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            CameraManager.get().surfaceChanged(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            hasSurface = false;
            CameraManager.get().stopPreview();
            CameraManager.get().closeDriver();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CameraManager.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) this.findViewById(R.id.preview_view);
        surfaceHolder = surfaceView.getHolder();
        initScan();

    }

    private void initScan() {
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(mCallBack);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        AudioManager audioService = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
        }
        vibrate = true;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (Exception ioe) {//权限拒绝或者是相机初始化失败
            new AlertDialog.Builder(mActivity)
                    .setTitle("温馨提示")
                    .setMessage("无法打开相机，请检查是否授予摄像头权限")
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    }).show();

            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        playBeepSoundAndVibrate();
        resultString = result.getText();

        if(resultString.startsWith("http://www.xftb168.com/web/paytomem")){
            String[] strings=resultString.split("=");
            if(strings.length==2){
                String memid=strings[1];
                Intent intent=new Intent(mActivity, PayActivityPre2.class);
                intent.setAction(memid);
                startActivity(intent);
                finish();
            }else {
                showDialog();
            }
        }else {
            showDialog();
        }
    }

    private void showDialog(){
        final Dialog mDialog=new Dialog(mActivity);
        mDialog.setTitle("温馨提示");
        mDialog.setMessage("该二维码不是智惠返商户付款码，请重新尝试其他");
        mDialog.setConfirmText("重新扫描");
        mDialog.setCancelable(false);
        mDialog.setConfirmClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.dismiss();
                handler=null;//添加这句话后可以重新扫描
                initScan();
            }
        });
        mDialog.setCancelClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.dismiss();
                handler=null;//添加这句话后可以重新扫描
                initScan();
            }
        });
        mDialog.show();

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    private void playBeepSoundAndVibrate() {
        if (vibrate) {
            Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public Handler getHandler() {
        return handler;
    }

}
