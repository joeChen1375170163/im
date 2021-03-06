package com.cxh.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.util.Base64Utils;

/**
 * 解密
 */
public class Decryption
{
    /**
     * DES算法密钥
     */
    private static final byte[] DES_KEY = { 115, -122, 122, -120, 119, -108, 107, -106 };

    /**
     * 数据解密，算法（DES）
     * 
     * @param cryptData
     *            加密数据
     * @return 解密后的数据
     */
    public static String decryptBasedDes(String cryptData)
    {
        String decryptedData = null;
        try
        {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");// TODO 此处有BUG，DES加解密已经不是安全的了，建议使用AES，但目前没有追踪到加密是否使用DES，暂不改动
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            // 把字符串解码为字节数组，并解密
            decryptedData = new String(cipher.doFinal(Base64Utils.decodeFromString(cryptData)));
        }
        catch (Exception e)
        {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }

    public static void main(String[] args)
    {
        String str = Decryption.decryptBasedDes("OosPUqMWThwSyVeV/uiaeg==");
        System.out.println(str);
    }
}
