package com.pplive.upload.test.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weihu on 2016/5/25.
 */
public class StringUtil {
    public final static String EMPTY_STRING = "";

    public final static String NULL_STRING = "null";

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals(NULL_STRING) || str.trim().equals(EMPTY_STRING);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals(EMPTY_STRING);
    }

    public static String safeString(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static String newGuid() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }

    public static int getFirstNumber(String src) {
        if (src == null)
            return -1;

        StringBuilder str = new StringBuilder();
        for (int i = 0; i <= src.length() - 1; i++) {
            if (Character.isDigit(src.charAt(i))) {
                str.append(src.charAt(i));
            }
        }
        try {
            int num = Integer.parseInt(str.toString());
            return num;
        } catch (Exception e) {
            e.toString();
        }
        return -1;
    }

    public static String getViewers(int vv) {
        if (vv >= 10000) {
            return vv / 1000 + "k";
        } else if (vv < 0) {
            return String.valueOf(0);
        } else {
            return String.valueOf(vv);
        }
    }

    public static boolean isPhone(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static String getMD5(String s) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(s.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        }
        catch (NoSuchAlgorithmException e) {
            return "";
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
