/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acesso;

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
        senha = "segredo";
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
            
            senha = Base64.getEncoder().encodeToString(digest.digest());            
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
