package com.cxh.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加密
 * */
public class Encryption 
{
	/** 
     * DES算法密钥 
     */  
    private static final byte[] DES_KEY = { 115,-122,122,-120,119,-108,107,-106 };  
    /** 
     * 数据加密，算法（DES） 
     * 
     * @param data 
     *            要进行加密的数据 
     * @return 加密后的数据 
     */  
    @SuppressWarnings("restriction")
	public static String encryptBasedDes(String data) 
    {  
        String encryptedData = null;  
        try 
        {  
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(DES_KEY);  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey key = keyFactory.generateSecret(deskey);  
            // 加密对象  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);  
            // 加密，并把字节数组编码成字符串  
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));  
        } 
        catch (Exception e) 
        {  
//            log.error("加密错误，错误信息：", e);  
            throw new RuntimeException("加密错误，错误信息：", e);  
        }  
        return encryptedData;  
    }  
}
