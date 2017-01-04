package com.pplive.media.upload.util;
import android.content.Context;
import android.content.pm.PackageInfo;

/** PackageManager相关工具类 */
public class PackageUtils
{
	private static String versionName;
	
    /** 版本号name */
    public static String getVersionName(Context context)
    {
        if (versionName == null && context != null)
        {
            try
            {
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                versionName = info.versionName;
            }
            catch (Exception e)
            {
            	LogUtils.error(e.toString(), e);
            }
        }
        return versionName;
    }
    
    private static int versionCode = 0;

    /** 版本号code */
    public static int getVersionCode(Context context)
    {
    	if (versionCode == 0 && context != null)
        {
            try
            {
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                versionCode = info.versionCode;
            }
            catch (Exception e)
            {
            	LogUtils.error(e.toString(), e);
            }
        }
        return versionCode;
    }
}
