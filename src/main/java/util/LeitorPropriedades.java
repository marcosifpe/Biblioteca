/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import excecao.ExcecaoSistema;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author MASC
 */
public class LeitorPropriedades {

    private Properties propriedades;

    public LeitorPropriedades(String[] nomesArquivo) {
        propriedades = new Properties();
        InputStream is;

        for (String nome : nomesArquivo) {
            is = this.getClass().getClassLoader().getResourceAsStream(nome);

            if (is != null) {
                try {
                    propriedades.load(is);
                    is.close();
                } catch (IOException ex) {
                    throw new ExcecaoSistema(ex);
                }
            }
        }
    }
    
    public String get(String chave) {
        return propriedades.getProperty(chave);
    }
        
    public synchronized void adicionar(String chave, String valor) {
        propriedades.put(chave, valor);
    }
}
