package com.youyou.xiaofeibao.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ImageTools {

    /**
     * 图片缩放
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }

    public static BitmapDrawable zoomBitmap(BitmapDrawable bitmapd, float w, float h) {
        Bitmap bitmap = DrawableToBitmap(bitmapd);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = (w / width);
        float scaleHeight = (h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return BitmapToDrawable(newbmp);
    }

    public static BitmapDrawable zoomBitmapW(Drawable bitmapd, int w) {
        Bitmap bitmap = DrawableToBitmap(bitmapd);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int h = w * height / width;
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        BitmapDrawable bd = BitmapToDrawable(newbmp);
        return bd;
    }

    public static Bitmap zoomBitmapWB(Drawable bitmapd, int w) {
        Bitmap bitmap = DrawableToBitmap(bitmapd);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int h = w * height / width;
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }

    /**
     * 将Bitmap图片转换为Drawable
     *
     * @param img
     * @return
     */
    public static BitmapDrawable BitmapToDrawable(Bitmap img) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(img);
        return bitmapDrawable;
    }

    /**
     * 将Drawablex转换为Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap DrawableToBitmap(BitmapDrawable d) {
        return d.getBitmap();
    }

    /**
     * 将Drawablex转换为Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap DrawableToBitmap(Drawable d) {
        return ((BitmapDrawable) d).getBitmap();
    }

    /**
     * 把图片变成圆角的图片
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        // 根据原来图片大小画一个矩形
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        // 圆角弧度参数,数值越大圆角越大,甚至可以画圆形
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // 画出一个圆角的矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        // 取两层绘制交集,显示上层
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // 显示图片
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // 返回Bitmap对象
        return output;
    }

    /**
     * 将文字转换为图片
     *
     * @param txt
     * @param txtSize
     * @return
     */
    public static Bitmap createTxtImage(String txt, int txtSize) {
        Bitmap mbmpTest = Bitmap.createBitmap(txt.length() * txtSize + 4, txtSize + 4, Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(mbmpTest);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
        p.setTextSize(txtSize);
        canvasTemp.drawText(txt, 2, txtSize - 2, p);
        return mbmpTest;
    }

    /**
     * 将Bitmap图片转换为字节数组
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 图片旋转
     *
     * @param bmp    要旋转的图片
     * @param degree 图片旋转的角度，负值为逆时针旋转，正值为顺时针旋转
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bmp, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    /**
     * 图片缩放
     *
     * @param bm
     * @param scale 值小于则为缩小，否则为放大
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bm, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    }

    public static Bitmap resizeBitmap(Bitmap bm, int w, int h) {
        Bitmap BitmapOrg = bm;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
    }

    /**
     * 图片反转
     *
     * @param bm
     * @param flag 0为水平反转，1为垂直反转
     * @return
     */
    public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
        float[] floats = null;
        switch (flag) {
            case 0: // 水平反转
                floats = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
                break;
            case 1: // 垂直反转
                floats = new float[]{1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
                break;
        }
        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }
        return null;
    }

    /**
     * 图片加入倒影
     *
     * @param originalImage
     * @return
     */
    public static Bitmap createReflectedImage(Bitmap originalImage) {
        // The gap we want between the reflection and the original image
        final int reflectionGap = 0;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // This will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        // Create a Bitmap with the flip matrix applied to it.
        // We only want the bottom half of the image
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);

        // Create a new bitmap with same width but taller to fit reflection
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);

        // Create a new Canvas with the bitmap that's big enough for
        // the image plus gap plus reflection
        Canvas canvas = new Canvas(bitmapWithReflection);
        // Draw in the original image
        canvas.drawBitmap(originalImage, 0, 0, null);
        // Draw in the gap
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        // Draw in the reflection
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        // Create a shader that is a linear gradient that covers the reflection
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                TileMode.CLAMP);
        // Set the paint to use this shader (linear gradient)
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 将下载的图片保存到SD卡上
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String fileName) throws IOException {
        if (bm == null || fileName == null)
            return null;
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File dirFile = new File("");
            if (!dirFile.exists()) {
                dirFile.mkdirs();
                dirFile.createNewFile();
            }
            File cacheRoot = new File("/" + fileName + ".jpg");
            cacheRoot.createNewFile();

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("/" + fileName + ".jpg"));
            bm.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            bos.flush();
            bos.close();
            return "/" + fileName + ".jpg";
        }
        return null;
    }

    public static File saveFileFile(Bitmap bm, String fileName) throws IOException {
        if (bm == null || fileName == null)
            return null;
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File dirFile = new File(Environment.getExternalStorageDirectory().getPath() + "/uni2uni/");
            if (!dirFile.exists()) {
                dirFile.mkdirs();
                dirFile.createNewFile();
            }
            File cacheRoot = new File("/" + fileName + ".jpg");
            cacheRoot.createNewFile();

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("/" + fileName + ".jpg"));
            bm.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            bos.flush();
            bos.close();
            return cacheRoot;
        }
        return null;
    }

    public static Bitmap readFile(String fileName) {
        String ALBUM_PATH = Environment.getExternalStorageDirectory().getPath() + "/leapinfo/webedu";
        return BitmapFactory.decodeFile(ALBUM_PATH + "/" + fileName + ".jpg");
    }

    public static BitmapDrawable readFileDrawable(String fileName) {
        return BitmapToDrawable(BitmapFactory.decodeFile(fileName));
    }

    /**
     * 模糊效果
     *
     * @param bmp
     * @return
     */
    public static Bitmap blurImage(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int newColor = 0;

        int[][] colors = new int[9][3];
        for (int i = 1, length = width - 1; i < length; i++) {
            for (int k = 1, len = height - 1; k < len; k++) {
                for (int m = 0; m < 9; m++) {
                    int s = 0;
                    int p = 0;
                    switch (m) {
                        case 0:
                            s = i - 1;
                            p = k - 1;
                            break;
                        case 1:
                            s = i;
                            p = k - 1;
                            break;
                        case 2:
                            s = i + 1;
                            p = k - 1;
                            break;
                        case 3:
                            s = i + 1;
                            p = k;
                            break;
                        case 4:
                            s = i + 1;
                            p = k + 1;
                            break;
                        case 5:
                            s = i;
                            p = k + 1;
                            break;
                        case 6:
                            s = i - 1;
                            p = k + 1;
                            break;
                        case 7:
                            s = i - 1;
                            p = k;
                            break;
                        case 8:
                            s = i;
                            p = k;
                    }
                    pixColor = bmp.getPixel(s, p);
                    colors[m][0] = Color.red(pixColor);
                    colors[m][1] = Color.green(pixColor);
                    colors[m][2] = Color.blue(pixColor);
                }

                for (int m = 0; m < 9; m++) {
                    newR += colors[m][0];
                    newG += colors[m][1];
                    newB += colors[m][2];
                }

                newR = (int) (newR / 9F);
                newG = (int) (newG / 9F);
                newB = (int) (newB / 9F);

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                newColor = Color.argb(255, newR, newG, newB);
                bitmap.setPixel(i, k, newColor);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        return bitmap;
    }

    /**
     * 柔化效果(高斯模糊)(优化后比上面快三倍)
     *
     * @param bmp
     * @return
     */
    public static Bitmap blurImageAmeliorate(Bitmap bmp) {
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 16; // 值越小图片会越亮，越大则越暗

        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + pixR * gauss[idx];
                        newG = newG + pixG * gauss[idx];
                        newB = newB + pixB * gauss[idx];
                        idx++;
                    }
                }

                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap reDrawBitMap(Activity ac, Bitmap bitmap) {
        DisplayMetrics dm = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // int rHeight = dm.heightPixels;
        // int rWidth = dm.widthPixels;
        float rHeight = dm.heightPixels / dm.density + 0.5f;
        float rWidth = dm.widthPixels / dm.density + 0.5f;
        int height = bitmap.getScaledHeight(dm);
        int width = bitmap.getScaledWidth(dm);
        // int height = bitmap.getHeight();
        // int width = bitmap.getWidth();
        float zoomScale;
        if (rWidth / rHeight > width / height) {// 以高为准
            zoomScale = ((float) rHeight) / height;
        } else { // if(rWidth/rHeight<width/height)//以宽为准
            zoomScale = ((float) rWidth) / width;
        }
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(zoomScale, zoomScale);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
}
