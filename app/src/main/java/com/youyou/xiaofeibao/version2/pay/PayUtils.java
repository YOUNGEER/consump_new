package com.youyou.xiaofeibao.version2.pay;

import com.youyou.xiaofeibao.version2.Config;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 作者：young on 2016/12/19 18:55
 */

public class PayUtils {

    public static String getXfbSign(Map map) {
        ArrayList<String> list = new ArrayList<String>();
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            String keyStr = (String) it.next();
            String value = map.get(keyStr) + "";
            if (null != value && !"".equals(value)
                    && !value.equals("sign")) {
                list.add(keyStr + "=" + value + "&");
            }
        }

        int size = list.size();

        String[] arrayToSort = (String[]) list.toArray(new String[size]);

        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {

            sb.append(arrayToSort[i]);

        }
        String result = sb.toString();

        if (null != Config.XFB_SIGN_KEY) {
            result += "key=" + Config.XFB_SIGN_KEY;
        }

        result = MD5Encode(result, "UTF-8").toUpperCase();

        return result;
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
