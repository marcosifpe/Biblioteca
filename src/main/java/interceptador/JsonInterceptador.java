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
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
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
    
    protected HttpServletRequest getHttpServletRequest(InvocationContext ic) {
        HttpServletRequest request = null;
        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpServletRequest) {
                request = (HttpServletRequest) parameter;
            }
        }

        return request;
    }

    protected HttpHeaders getHttpHeaders(InvocationContext ic) {
        HttpHeaders headers = null;

        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpHeaders) {
                headers = (HttpHeaders) parameter;
            }
        }

        return headers;
    }
      
    protected HttpServletResponse getHttpServletResponse(InvocationContext ic) {
        HttpServletResponse response = null;
        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpServletResponse) {
                response = (HttpServletResponse) parameter;
            }
        }

        return response;
    }
    
    private Response response(String json) {
        return Response.ok(json).build();
    }

    protected Response getJsonErrorResponse(String chave) {
        RespostaJson respostaJson = new RespostaJson(false, properties.getProperty(chave));
        return response(new Gson().toJson(respostaJson));
    }
    
    protected Response getJsonErrorResponse(String chave, String mensagemComplementar) {       
        RespostaJson respostaJson = new RespostaJson(false, String.format((String) properties.get(chave), mensagemComplementar));
        return response(new Gson().toJson(respostaJson));
    }    

    protected Response getJsonErrorResponse(Throwable causa) {
        RespostaJson respostaJson = new RespostaJson(false, causa.getMessage());
        return response(new Gson().toJson(respostaJson));        
    }
}
