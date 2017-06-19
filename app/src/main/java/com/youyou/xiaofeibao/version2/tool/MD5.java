package com.youyou.xiaofeibao.version2.tool;

import android.util.Base64;

import com.youyou.xiaofeibao.version2.Config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */
public class MD5 {

    public static String getCode(String str) {
        String md5 = MD5.getMD5(Base64.encodeToString((MD5.getMD5(str)
                + Config.PRIVATE_KEY).trim().getBytes(), Base64.NO_WRAP));
        return md5;
    }

    public static String getMD5(String value) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
