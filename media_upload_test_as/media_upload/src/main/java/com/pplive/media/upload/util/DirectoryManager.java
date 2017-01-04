package com.pplive.media.upload.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

public class DirectoryManager {
    private static Context sAppContext;

    public static void init(Context context) {
        sAppContext = context.getApplicationContext();
    }

    private final static String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private final static String SD_APP_PATH = SD_PATH + "/ibo";

    public static String getPrivateCachePath() {
        return sAppContext.getCacheDir().getAbsolutePath();
    }

    public static String getPrivateFilesPath() {
        return sAppContext.getFilesDir().getAbsolutePath();
    }

    public static String getCachePath() {
        if (hasExternalStorage()) {
            return sAppContext.getExternalCacheDir().getAbsolutePath();
        } else {
            return getPrivateCachePath();
        }
    }

    public static String getFilesPath() {
        if (hasExternalStorage()) {
            return sAppContext.getExternalFilesDir(null).getAbsolutePath();
        } else {
            return getPrivateFilesPath();
        }
    }

    public static String getAppPath() {
        if (hasExternalStorage()) {
            return SD_APP_PATH;
        } else {
            return getPrivateFilesPath();
        }
    }

    public static String getShareCachePath() {
        return getCachePath() + "/share";
    }

    public static String getImageCachePath() {
        return getAppPath() + "/image";
    }

    public static String getLogCachePath() {
        return getCachePath() + "/log";
    }

    public static String getCrashCachePath() {
        return getCachePath() + "/crash";
    }

    public static String getDownloadPath() {
        return getAppPath() + "/download";
    }
    
	public static String getRecordPath() {
		String path = getAppPath() + "/videos";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}

    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    
	public static float getAvailableFilePercent() {
		if (hasExternalStorage()) {
			long usableSpace = Environment.getExternalStorageDirectory()
					.getUsableSpace();
			long totalSpace = Environment.getExternalStorageDirectory()
					.getTotalSpace();

			return 1 - new BigDecimal(usableSpace).divide(
					new BigDecimal(totalSpace), 2, RoundingMode.HALF_DOWN)
					.floatValue();
		} else {
			long usableSpace = sAppContext.getFilesDir().getUsableSpace();
			long totalSpace = sAppContext.getFilesDir().getTotalSpace();

			return 1 - new BigDecimal(usableSpace).divide(
					new BigDecimal(totalSpace), 2, RoundingMode.HALF_DOWN)
					.floatValue();
		}
	}

	public static long getAvailableFileSize() {
		if (hasExternalStorage()) {
			return Environment.getExternalStorageDirectory().getUsableSpace();
		} else {
			return sAppContext.getFilesDir().getUsableSpace();
		}
	}

	public static long getVideoFileSize(String path) {
		File file = new File(path);
		if (file.exists())
			return file.length();
		else {
			return 0;
		}
	}

	public static boolean deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.delete();
		} else {
			return false;
		}
	}

    public static boolean checkPath(String path) {

        if (TextUtils.isEmpty(path))
            return false;
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return file.isDirectory();
        }
    }

}
