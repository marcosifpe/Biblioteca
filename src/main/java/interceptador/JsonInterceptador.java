/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptador;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.google.gson.Gson;
import java.util.logging.Logger;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.LeitorPropriedades;
import webservice.RespostaJson;

/**
 *
 * @author MASC
 */
public abstract class JsonInterceptador {
    protected static final Logger LOGGER = Logger.getLogger(JsonInterceptador.class.getName()); 
    protected LeitorPropriedades leitorPropriedades;
    
    public JsonInterceptador() {
        this.leitorPropriedades = new LeitorPropriedades(new String[]{"Exception.properties", "Mensagens.properties"});
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
    
    private Response response(String json) {
        return Response.ok(json, MediaType.valueOf(APPLICATION_JSON + ";charset=UTF-8")).build();
    }

    protected Response getJsonErrorResponse(String chave) {
        RespostaJson respostaJson = new RespostaJson(false, leitorPropriedades.get(chave));
        return response(new Gson().toJson(respostaJson));
    }
    
    protected Response getJsonErrorResponse(String chave, String mensagemComplementar) {       
        RespostaJson respostaJson = new RespostaJson(false, String.format(leitorPropriedades.get(chave), mensagemComplementar));
        return response(new Gson().toJson(respostaJson));
    }    

    protected Response getJsonErrorResponse(Throwable causa) {
        RespostaJson respostaJson = new RespostaJson(false, causa.getMessage());
        return response(new Gson().toJson(respostaJson));        
    }
}
