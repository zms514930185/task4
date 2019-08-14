//package com.jnshu.uitl;
//
//import io.jsonwebtoken.Claims;
//import org.junit.jupiter.api.Test;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JjwtImplTest {
//
//    @Test
//    void jjwtTokenDe() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
//        JjwtImpl jjwt=new JjwtImpl();
//        String token="eyJ0eXBlIjoiand0dCIsImFsZyI6IkhTNTEyIn0.eyJkYXRlIjoid1Zjczd5Zy9jNGJJbmZJS3Z6dDRaUVx1MDAzZFx1MDAzZCIsImlkIjoiMXlBbEtVeUFCWEFcdTAwM2QifQ.SDjkpiLn3ECE3fsxvlHQTjkxWVcCTQ7YXfhe2XIgQQQ09zMlPSPv-yklwZ6WCaBmbUbNPsYl3a2drMEK7_qYpg";
//        String date= (String) jjwt.jjwtTokenDe(token).get("date");
//        String id= (String) jjwt.jjwtTokenDe(token).get("id");
//        DesUitlImpl desUitl=new DesUitlImpl();
//        String userId=desUitl.decrypt(id);
//        long uId = Long.parseLong(userId);
//        System.out.println(uId);
//
//    }
//}