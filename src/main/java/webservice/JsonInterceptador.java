/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MASC
 */
public abstract class JsonInterceptador {
    protected Properties properties;
    protected static final Logger LOGGER = Logger.getLogger(JsonInterceptador.class.getName());    
    
    public JsonInterceptador() {
        properties = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("Exception.properties");
        
        if (is != null) {
            try {
                properties.load(is);
                is.close();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }      
        
        is = this.getClass().getClassLoader().getResourceAsStream("ValidationMessages_pt_BR.properties");
        
        if (is != null) {
            try {
                properties.load(is);
                is.close();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }      
        
    }    

    protected String getJson(String chave) {
        RespostaJson respostaJson = new RespostaJson(false, properties.getProperty(chave));
        Gson gson = new Gson();
        return gson.toJson(respostaJson);
    }
    
    protected String getJson(String chave, String mensagemComplementar) {       
        RespostaJson respostaJson = new RespostaJson(false, String.format((String) properties.get(chave), mensagemComplementar));
        Gson gson = new Gson();
        return gson.toJson(respostaJson);
    }    

    Object getJson(Throwable causa) {
        RespostaJson respostaJson = new RespostaJson(false, causa.getMessage());
        Gson gson = new Gson();
        return gson.toJson(respostaJson);        
    }
}
