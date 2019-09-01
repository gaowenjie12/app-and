package com.zsoe.businesssharing.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * 该类从ImageLoader中摘取，用于获取缓存文件夹路径
 * 以后如果有用到获取缓存文件夹，可以获取该文件夹路径，方便以后对缓存作统一管理
 * @author myChaoFile
 */
public final class StorageUtils {

    private StorageUtils() {
    }

    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;

        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException var5) {
            externalStorageState = "";
        } catch (IncompatibleClassChangeError var6) {
            externalStorageState = "";
        }

        if(preferExternal && "mounted".equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }

        if(appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }

        if(appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }

        return appCacheDir;
    }

    public static File getIndividualCacheDirectory(Context context) {
        return getIndividualCacheDirectory(context, "uil-images");
    }

    public static File getIndividualCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        if(!individualCacheDir.exists() && !individualCacheDir.mkdir()) {
            individualCacheDir = appCacheDir;
        }

        return individualCacheDir;
    }

    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }

        if(appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    public static File getOwnCacheDirectory(Context context, String cacheDir, boolean preferExternal) {
        File appCacheDir = null;
        if(preferExternal && "mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }

        if(appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if(!appCacheDir.exists()) {
            if(!appCacheDir.mkdirs()) {
                return null;
            }

            try {
                (new File(appCacheDir, ".nomedia")).createNewFile();
            } catch (IOException var4) {
                LogUtil.i("IOException",var4.getMessage());
            }
        }

        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }



    /**
     * 下载路径
     */
    public final static String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/AAA/";

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();
        File file = new File(FILE_PATH + fileName);
        if (file.exists()){
            checker.checkDelete(file.toString());
            if (file.isFile()) {
                try {
                    file.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        }else
            status = false;
        return status;
    }
}
