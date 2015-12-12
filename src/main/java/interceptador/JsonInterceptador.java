/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import webservice.RespostaJson;

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
    
    protected Response response(String json) {
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    protected Response getJson(String chave) {
        RespostaJson respostaJson = new RespostaJson(false, properties.getProperty(chave));
        Gson gson = new Gson();
        return response(gson.toJson(respostaJson));
    }
    
    protected Response getJson(String chave, String mensagemComplementar) {       
        RespostaJson respostaJson = new RespostaJson(false, String.format((String) properties.get(chave), mensagemComplementar));
        Gson gson = new Gson();
        return response(gson.toJson(respostaJson));
    }    

    protected Response getJson(Throwable causa) {
        RespostaJson respostaJson = new RespostaJson(false, causa.getMessage());
        Gson gson = new Gson();
        return response(gson.toJson(respostaJson));        
    }
}
