package com.gc.utils;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Android 3DES加密工具类
 */
public class AndroidDes3Util {
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 后台服务器密钥   正式服 H5ZLBACzrQARj7FOtpwIpvnP    测试服 gagc#2017ABCDgagc#2017ABCD
     */
    public static String secretKey = "H5ZLBACzrQARj7FOtpwIpvnP ";
    public static String iv = "01234567";

    /**
     * 文件服务器密钥
     */
    public static String secretKeyFile = "H5ZLBACzrQARj7FOtpwIpvnP ";
    public static String ivFile = "01234567";

    /**
     * 升级服务器密钥
     */
    public static String secretKeyUpdate = "H.qo*)z&RDy6'C#hxlXdwE8n";
    public static String ivUpdate = "62039340";

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) {

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
    public static String decode(String encryptText) {
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
     * 3DES加密,升级
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encodeUpdate(String plainText) {
       LogUtil.d(AndroidDes3Util.class, "加密前-》plainText=" + plainText);
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKeyUpdate.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(ivUpdate.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            String temp = Base64.encodeToString(encryptData, Base64.DEFAULT);
            LogUtil.d(AndroidDes3Util.class, "加密后-》plainText=" + temp);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 3DES解密 升级
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decodeUpdate(String encryptText) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKeyUpdate.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(ivUpdate.getBytes());
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
    public static String encodeFile(String plainText) {
        LogUtil.d(AndroidDes3Util.class, "加密前-》plainText=" + plainText);
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKeyFile.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(ivFile.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            String temp = Base64.encodeToString(encryptData, Base64.DEFAULT);
           LogUtil.d(AndroidDes3Util.class, "加密后-》plainText=" + temp);
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
    public static String decodeFile(String encryptText) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKeyFile.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(ivFile.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.DEFAULT));
            return new String(decryptData, encoding);
        } catch (Exception e) {
            return null;
        }

    }

}
