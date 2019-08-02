package com.jnshu.uitl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class DesUitlImpl implements DesUtil {
    /*密钥*/
    public static final String USER_ID_KEY ="12345678";
    @Override
    public String encryption(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //生成Cipher对象,指定其支持的DES算法，用来加密或者解密的。
        Cipher cipher = Cipher.getInstance("DES");
        //生成密钥，这个密钥要符合对应的加密算法。什么算法配什么钥匙
        SecretKeySpec secretKeySpec=new SecretKeySpec(USER_ID_KEY.getBytes(),"DES");
        /*初始化这个对象，这里让他用来加密，就像告诉他我现在准备干嘛，ENCRYPT_MODE是加密，改成DECRYPT_MODE是解密*/
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        /*这里就是正式开始进行加密操作*/
        byte[] cipherByte=cipher.doFinal(data.getBytes());
        /*使用Base64转码*/
        Base64.Encoder encoder = Base64.getEncoder();
        /*开始转码*/
        byte[] dataBase64 = encoder.encode(cipherByte);
        /*返回加密后的内容*/
        return new String(dataBase64);
    }

    @Override
    public String decrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        /*开始解码Base64*/
        Base64.Decoder decoder=Base64.getDecoder();
        byte[] bytes=decoder.decode(data);
        //生成Cipher对象,指定其支持的DES算法，用来加密或者解密的。
        Cipher cipher = Cipher.getInstance("DES");
        //生成密钥，这个密钥要符合对应的加密算法。什么算法配什么钥匙
        SecretKeySpec secretKeySpec=new SecretKeySpec(USER_ID_KEY.getBytes(),"DES");
        /*DES解码，初始化为解码方式，密钥和加密的时候一样*/
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        /*开始进行解码*/
        byte[] cipherBy=cipher.doFinal(bytes);
        return new String(cipherBy);
    }
}
