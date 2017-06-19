package com.youyou.xiaofeibao.zxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.youyou.xiaofeibao.view.ScreenUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * @author zhaoyunhai on 16/3/23.
 */
public class ZxingUtils {



    /**
     * 正方形二维码宽度
     */
    private static final int CODE_WIDTH = 440;
    /**
     * LOGO宽度值,最大不能大于二维码20%宽度值,大于可能会导致二维码信息失效
     */
    private static final int LOGO_WIDTH_MAX = CODE_WIDTH / 5;
    /**
     *LOGO宽度值,最小不能小鱼二维码10%宽度值,小于影响Logo与二维码的整体搭配
     */
    private static final int LOGO_WIDTH_MIN = CODE_WIDTH / 10;

//    public ZxingUtils(int width, int height) {
//        this.width = width;
//        this.height = height;
//    }

    /**
     * 生成带LOGO的二维码
     */
    public static Bitmap createCode(Context context,String content, Bitmap logoBitmap)
            throws WriterException {

        int ScreenWidth=ScreenUtils.getScreenWidth(context);

        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

//        int logoHaleWidth = logoWidth >= CODE_WIDTH ? LOGO_WIDTH_MIN
//                : LOGO_WIDTH_MAX;
//        int logoHaleHeight = logoHeight >= CODE_WIDTH ? LOGO_WIDTH_MIN
//                : LOGO_WIDTH_MAX;

        int logoHaleWidth = ScreenWidth/12;
        int logoHaleHeight=logoHaleWidth;


        // 将logo图片按martix设置的信息缩放
        Matrix m = new Matrix();
        float sx = (float) 2 * logoHaleWidth / logoWidth;
        float sy = (float) 2 * logoHaleHeight / logoHeight;

        m.setScale(sx, sy);// 设置缩放信息
        Log.i("ssssdddddd",sx+"       "+sy+"    "+logoWidth+"    "+logoHeight);

        Bitmap newLogoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0, logoWidth,
                logoHeight, m, false);
        int newLogoWidth = newLogoBitmap.getWidth();
        int newLogoHeight = newLogoBitmap.getHeight();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置容错级别,H为最高
//        hints.put(EncodeHintType.MAX_SIZE, LOGO_WIDTH_MAX);// 设置图片的最大值
//        hints.put(EncodeHintType.MIN_SIZE, LOGO_WIDTH_MIN);// 设置图片的最小值
        hints.put(EncodeHintType.MAX_SIZE, logoHaleWidth);// 设置图片的最大值
        hints.put(EncodeHintType.MIN_SIZE, logoHaleHeight);// 设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 2);//设置白色边距值
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败

        int width= ScreenUtils.getScreenWidth(context)*4/5;

        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, width, hints);
        int height=width;
        int halfW = width / 2;
        int halfH = height / 2;

        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > halfW - newLogoWidth / 2 && x < halfW + newLogoWidth / 2
                        && y > halfH - newLogoHeight / 2 && y < halfH + newLogoHeight / 2) {// 该位置用于存放图片信息
                    pixels[y * width + x] = newLogoBitmap.getPixel(
                            x - halfW + newLogoWidth / 2, y - halfH + newLogoHeight / 2);
                } else {
                    pixels[y * width + x] = matrix.get(x, y) ? BLACK: WHITE;// 设置信息
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 用字符串生成二维码
     *
     * @param str
     * @return
     * @throws WriterException
     * @author zhouzhe@lenovo-cw.com
     */
    public static Bitmap create2DCode(String str) throws WriterException {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 900, 900);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /*//绘制二维码
    public Bitmap bitmap(String s) throws Exception{
        //二维码QR_CODE
        BarcodeFormat fomt=BarcodeFormat.QR_CODE;
        //编码转换
        String a=new String(s.getBytes("utf-8"),"ISO-8859-1");
        BitMatrix matrix=new MultiFormatWriter().encode(a, fomt, width, height);
        int width=matrix.getWidth();
        int height=matrix.getHeight();
        int[] pixel=new int[width*height];
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(matrix.get(j,i))
                    pixel[i*width+j]=0xff000000;
            }
        }
        Bitmap bmap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        bmap.setPixels(pixel, 0, width, 0, 0, width, height);
        return bmap;
    }*/
    //绘制条形码
    public static Bitmap bitmap1(String ss) throws Exception {
        //    BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 900, 900);
        //条形码CODE_128
        //    BarcodeFormat fomt=BarcodeFormat.CODE_128;
        BitMatrix matrix = new MultiFormatWriter().encode(ss, BarcodeFormat.CODE_128, 1000, 300);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixel = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix.get(j, i))
                    pixel[i * width + j] = 0xff000000;
            }
        }
        Bitmap bmapp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmapp.setPixels(pixel, 0, width, 0, 0, width, height);
        return bmapp;
    }
}
