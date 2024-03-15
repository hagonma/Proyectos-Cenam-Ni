/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.data;

  
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author Diego Lopez
 */
public class DecryptCryptoJs {
    public static final byte[] KEY = {'C', 'L', 'A', 'R', 'O', 'C', 'O', 'B', 'E', 'R', 'T', 'U', 'R', 'I', 'F', 'I'};

    private static Cipher ecipher;
    private static Cipher dcipher;

    static {
        try {
            ecipher = Cipher.getInstance("AES");
            SecretKeySpec eSpec = new SecretKeySpec(KEY, "AES");
            ecipher.init(Cipher.ENCRYPT_MODE, eSpec);
        } catch (Exception e) {
            Logger.getLogger(DecryptCryptoJs.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }


        try {
            dcipher = Cipher.getInstance("AES");
            SecretKeySpec dSpec = new SecretKeySpec(KEY, "AES");
            dcipher.init(Cipher.DECRYPT_MODE, dSpec);
        } catch (Exception e) {
            Logger.getLogger(DecryptCryptoJs.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String value) {
        byte[] b1 = value.getBytes();
        byte[] encryptedValue;
        try {
            encryptedValue = ecipher.doFinal(b1);
            return Base64.getEncoder().encodeToString(encryptedValue).replace("/", "_");
        } catch (Exception e) {
            Logger.getLogger(DecryptCryptoJs.class.getName()).log(Level.SEVERE, null, e);
            throw new IllegalArgumentException(e);
        }
    }

    public static String decrypt(String encryptedValue) {
        encryptedValue=encryptedValue.replace("_", "/");
        byte[] decryptedValue = Base64.getDecoder().decode(encryptedValue.getBytes());
        byte[] decValue;
        try {
            decValue = dcipher.doFinal(decryptedValue);
            return new String(decValue);
        } catch (Exception e) {
            Logger.getLogger(DecryptCryptoJs.class.getName()).log(Level.SEVERE, null, e);
            throw new IllegalArgumentException(e);
        }
    }
}
