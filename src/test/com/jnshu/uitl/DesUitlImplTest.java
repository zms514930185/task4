package com.jnshu.uitl;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class DesUitlImplTest {

    @Test
    void encryption() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        DesUitlImpl desUitl=new DesUitlImpl();
        String data="11111111111";
        System.out.println(desUitl.encryption(data));
    }

    @Test
    void decrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        DesUitlImpl desUitl=new DesUitlImpl();
        String data="2+/+xd8XknpbKcGvTI8DDA==";
        System.out.println(desUitl.decrypt(data));
    }
}