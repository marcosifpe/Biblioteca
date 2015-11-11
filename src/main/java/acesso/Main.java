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
import java.util.Base64;


/**
 *
 * @author MASC
 */
public class Main {
    private static String senha;
    public static void main(String[] args) {
        senha = "john123";
        criptografar();
        System.out.println(senha);
        senha = "johnjohn123";
        criptografar();
        System.out.println(senha);        
    }
    
    public static void criptografar() {
        gerarHash();
    }
    
    private static void gerarHash() {
        try { 
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(senha.getBytes(Charset.forName("UTF-8")));     
            
            byte[] bytes = digest.digest();
            BigInteger bigInt = new BigInteger(1, bytes);
            senha = bigInt.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
