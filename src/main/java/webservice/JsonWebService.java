/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import biblioteca.Entidade;
import com.google.gson.Gson;

/**
 *
 * @author MASC
 * @param <T> Tipo de Entidade
 */
public abstract class JsonWebService<T extends Entidade> {
    
    protected String getRespostaSucesso() {
        return getResposta(true, "Sucesso");
    }
    
    protected String getResposta(boolean sucesso, String mensagem) {
        RespostaJson respostaJson = new RespostaJson(sucesso, mensagem);
        Gson gson = new Gson();
        return gson.toJson(respostaJson);
    }
}
