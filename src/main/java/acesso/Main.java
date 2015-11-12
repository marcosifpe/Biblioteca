/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acesso;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


/**
 *
 * @author MASC
 */
public class Main {
    private static String senha;
    private static String salt;
    public static void main(String[] args) {
        senha = "teste123";
        criptografar();
        System.out.println("-----------");
        System.out.println(salt.length());
        System.out.println(salt);
        System.out.println(senha);
        senha = "teste123";
        criptografar();
        System.out.println("-----------");        
        System.out.println(salt.length());
        System.out.println(salt);        
        System.out.println(senha);        
    }
    
    public static void criptografar() {
        gerarHash();
    }
    
    private static void gerarHash() {
        try { 
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] randomBytes = new byte[32];
            secureRandom.nextBytes(randomBytes);
            salt = Base64.getEncoder().encodeToString(randomBytes);
            senha = salt + senha;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");            
            digest.update(senha.getBytes(Charset.forName("UTF-8")));                 
            senha = Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
