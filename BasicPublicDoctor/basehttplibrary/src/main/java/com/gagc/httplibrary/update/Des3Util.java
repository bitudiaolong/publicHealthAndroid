package com.gagc.httplibrary.update;

import android.util.Base64;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Android 3DES加密工具类
 */
public class Des3Util {

    private static String TAG = "GAGC_SECRET";

    // 密钥 长度不得小于24
    private final static String secretKey ="H.qo*)z&RDy6'C#hxlXdwE8n";
    // 向量 可有可无 终端后台也要约定
    private final static String iv = "62039340";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) {
        Log.e(TAG, "加密前-》plainText=" + plainText);
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            String temp = Base64.encodeToString(encryptData, Base64.DEFAULT);
            Log.e(TAG, "加密后-》plainText=" + temp);
            temp = temp.replaceAll("\n","").replaceAll(" ","");
            Log.e(TAG, "加密后去除换行-》plainText=["+temp+"]");
            return temp;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText)  {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.DEFAULT));
            return new String(decryptData, encoding);
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encodeUpdate(String plainText) {
        Log.e(TAG, "加密前-》plainText=" + plainText);
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            String temp = Base64.encodeToString(encryptData, Base64.DEFAULT);
            Log.e(TAG, "加密后-》plainText=" + temp);
            temp = temp.replaceAll("\n","").replaceAll(" ","");
            Log.e(TAG, "加密后去除换行-》plainText=["+temp+"]");
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decodeUpdate(String encryptText) throws Exception {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.DEFAULT));
            return new String(decryptData, encoding);
        } catch (Exception e) {
            return null;
        }

    }

}
