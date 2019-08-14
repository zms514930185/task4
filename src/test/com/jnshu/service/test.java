//package com.jnshu.service;
//
//
//import org.apache.commons.codec.binary.Hex;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.Test;
//
//
//import javax.crypto.*;
//import javax.crypto.spec.DESKeySpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.Arrays;
//import java.util.Base64;
//
//
//public class test {
//    private final Logger logger = LogManager.getLogger(this.getClass());
//
//    @Test
//    void desCookie() throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
//        /*密钥*/
//        String userIdKey="12345678";
//        /*要加密的内容*/
//        String data = "ssssssss";
//        //生成Cipher对象,指定其支持的DES算法，用来加密或者解密的。
//        Cipher cipher = Cipher.getInstance("DES");
//        //生成密钥，这个密钥要符合对应的加密算法。什么算法配什么钥匙
//        SecretKeySpec secretKeySpec=new SecretKeySpec(userIdKey.getBytes(),"DES");
//        /*初始化这个对象，这里让他用来加密，就像告诉他我现在准备干嘛，ENCRYPT_MODE是加密，改成DECRYPT_MODE是解密*/
//        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//        /*这里就是正式开始进行加密操作*/
//        byte[] cipherByte=cipher.doFinal(data.getBytes());
//        /*使用Base64转码*/
//        Base64.Encoder encoder = Base64.getEncoder();
//        /*开始转码*/
//        byte[] dataBase64 = encoder.encode(cipherByte);
//        System.out.println("BASE64加密：" + new String(dataBase64));
//        /*开始解码Base64*/
//        Base64.Decoder decoder=Base64.getDecoder();
//        byte[] bytes=decoder.decode(dataBase64);
//
//        /*DES解码，初始化为解码方式，密钥和加密的时候一样*/
//        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
//        /*开始进行解码*/
//        byte[] cipherBy=cipher.doFinal(bytes);
//        System.out.println(new String(cipherBy));
//    }
//
//    @Test
//    void desTest(String src){
//        try {
//            //生成Key
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
//            keyGenerator.init(56);//设置长度
//            SecretKey secretKey = keyGenerator.generateKey();
//            byte[] keyBytes = secretKey.getEncoded();
//
//            //key转换
//            DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
//            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
//            SecretKey generateSecret = secretKeyFactory.generateSecret(desKeySpec);
//
//            //加密
//            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, generateSecret);
//            byte[] result = cipher.doFinal(src.getBytes());
//            //System.out.println(Hex.registerNatives(result));
//
//            //解密
//            cipher.init(Cipher.DECRYPT_MODE,generateSecret);//使用同一个key
//            result = cipher.doFinal(result);
//            System.out.println(new String(result));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//
