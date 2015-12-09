/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import biblioteca.Entidade;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 *
 * @author MASC
 * @param <T> Tipo de Entidade
 */
public abstract class JsonWebService<T extends Entidade> {

    protected T get(String json, Class<T> clazz) {
        T result = null;
        try {
            Gson gson = new Gson();
            Reader reader = new StringReader(json);
            result = gson.fromJson(reader, clazz);
            reader.close();
        } catch (IOException ex) {
            throw new JsonWebServiceException(ex);
        }
        return result;
    }
    
    protected String getResposta(boolean sucesso, String mensagem) {
        RespostaJson respostaJson = new RespostaJson(sucesso, mensagem);
        Gson gson = new Gson();
        return gson.toJson(respostaJson);
    }
}
