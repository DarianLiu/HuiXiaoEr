package com.geek.huixiaoer.common.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作工具类
 */
public class FileUtils {

    public static void saveBitmap(Bitmap bm, String picName) {
//        System.out.println("-----------------------------");
        try {
            if (!isFileExist("")) {
//                System.out.println("创建文件");
                File tempf = createSDDir("");
            }
            File f = new File(Constants.PATH_IMAGE, picName + ".png");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(Constants.BASE_PATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

//            System.out.println("createSDDir:" + dir.getAbsolutePath());
//            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(Constants.BASE_PATH + fileName);
        file.isFile();
//        System.out.println(file.exists());
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(Constants.BASE_PATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    public static void deleteDir() {
        File dir = new File(Constants.BASE_PATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDir();
        }
        dir.delete();
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

}  