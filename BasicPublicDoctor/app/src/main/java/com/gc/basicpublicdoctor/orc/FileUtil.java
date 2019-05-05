package com.gc.basicpublicdoctor.orc;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public static File getSaveEnvFile(){
        File file = new File(Environment.getExternalStorageDirectory() + "/pic.jpg");
        return file;
    }

    public static File getSaveFile11(Context context){
        //File file = new File(context.getFilesDir(),"pic.jpg");
        File file = new File(Environment.getExternalStorageDirectory() + "/pic.jpg");
        return file;
    }

    public static File getSignFile(Context context){
        File file = new File(Environment.getExternalStorageDirectory() + "/sign.png");
//        File file = new File(context.getFilesDir(),"sign.png");
        return file;
    }

    public static void saveBitMap(Bitmap bitmap, String path) {

        File avaterFile = new File(path);//设置文件名称
        if (avaterFile.exists()) {
            avaterFile.delete();
        }
        try {
            avaterFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(avaterFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
