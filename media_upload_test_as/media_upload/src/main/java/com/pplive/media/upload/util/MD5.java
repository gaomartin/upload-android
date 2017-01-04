package com.pplive.media.upload.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

public class MD5
{

    private static String byte2Hex(byte b)
    {
        int value = (b & 0x7F) + (b < 0 ? 0x80 : 0);
        return (value < 0x10 ? "0" : "") + Integer.toHexString(value).toLowerCase();
    }

    public static String MD5_32(String passwd)
    {
        if (TextUtils.isEmpty(passwd))
        {
            return null;
        }
        try
        {
            return MD5_32(passwd.getBytes(HTTP.UTF_8));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String MD5_32(byte[] passwd)
    {

        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            StringBuffer strbuf = new StringBuffer();

            // md5.update(passwd.getBytes(), 0, passwd.length());
            md5.update(passwd);
            byte[] digest = md5.digest();

            for (int i = 0; i < digest.length; i++)
            {
                strbuf.append(byte2Hex(digest[i]));
            }

            return strbuf.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String MD5_16(String passwd)
    {
        return MD5_32(passwd).subSequence(8, 24).toString();
    }

    public static String MD5_16(byte[] passwd)
    {
        return MD5_32(passwd).subSequence(8, 24).toString();
    }

}
