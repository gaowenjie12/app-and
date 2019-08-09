package com.zsoe.businesssharing.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class AppFileUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/formats/";

    public static void saveBitmap(Bitmap bm, String picName) {
        Log.e("", "保存图片");
        try {
            if (EmptyUtil.isEmpty(bm)) {
                ToastUtils.showShort("存储失败");
                return;
            }
            if (!picName.endsWith("jpg")) {
                picName = picName + ".JPEG";
            }
            File f = new File(picName);

            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

//            System.out.println("createSDDir:" + dir.getAbsolutePath());
//            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }


    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static File getCacheDir() {
        return DApplication.getInstance().getExternalCacheDir();
    }

    public static File getDir(String type) {
        return DApplication.getInstance().getExternalFilesDir(type);
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */

    public static boolean copyFile(String oldPath, String newPath) {
        boolean isok = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            } else {
                isok = false;
            }
        } catch (Exception e) {
            // System.out.println("复制单个文件操作出错");
            // e.printStackTrace();
            isok = false;
        }
        return isok;

    }


    public final static File EaseCacheDir = StorageUtils.getOwnCacheDirectory(DApplication.getInstance(), Config.CHAT_IMAGE_CACHE);


    public static String GetFileSize(String Path) {
        return GetFileSize(new File(Path));
    }

    public static String GetFileSize(File file) {
        String size = "";
        if (file.exists() && file.isFile()) {
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = df.format((double) fileS) + "BT";
            } else if (fileS < 1048576) {
                size = df.format((double) fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                size = df.format((double) fileS / 1048576) + "MB";
            } else {
                size = df.format((double) fileS / 1073741824) + "GB";
            }
        } else if (file.exists() && file.isDirectory()) {
            size = "";
        } else {
            size = "0BT";
        }
        return size;
    }
}
