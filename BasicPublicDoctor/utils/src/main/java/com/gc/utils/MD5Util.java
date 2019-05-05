package com.gc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/1/22/022.
 */

public class MD5Util {
    public static String getMD5(String val) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }


    public static String getMd5ByFile(File file) {
        if(!file.exists() || !file.isFile()){
            return "";
        }

        byte[] buffer = new byte[2048];
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            while(true){
                int len = in.read(buffer,0,2048);
                if(len != -1){
                    digest.update(buffer, 0, len);
                }else{
                    break;
                }
            }
            in.close();

            byte[] md5Bytes  = digest.digest();
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();

            //String hash = new BigInteger(1,digest.digest()).toString(16);
            //return hash;

        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

      /*  //创建文件

        String value = "";
        try {
            FileInputStream in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
      //  return value;
    }

}
