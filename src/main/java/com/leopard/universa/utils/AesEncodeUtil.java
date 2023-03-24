package com.leopard.universa.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AesEncodeUtil {
    // 定义加密算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    private static final String AESKEY = "dkeifjfvjaslk_33";

    public static String Encrypt(String org) {
        //加密
        try {
            return new String(aesEncrypt(org, AESKEY));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String Decrypt(String encrptText) {

        try {
            return new String(aesDecrypt(encrptText, AESKEY));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * AES加密并转为 Base64 编码
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] encryptBytes = cipher.doFinal(content.getBytes("utf-8"));
        return Base64.encodeBase64String(encryptBytes);
    }

    /**
     * 对 Base64 编码的密文进行AES解密
     *
     * @param encryptStr 待解密的密文
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        if (StringUtils.isBlank(encryptStr)) {
            return null;
        }

        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static void main(String[] args) throws Exception {
        String content = "{\"type\":\"success\",\"message\":\"成功\"}";
        System.out.println();
        System.out.println("加密前：" + content);
        String encrypt = Encrypt(content);
        System.out.println("加密后：" + encrypt);
        String decrypt = Decrypt(encrypt);
        System.out.println("解密后：" + decrypt);
    }


}
