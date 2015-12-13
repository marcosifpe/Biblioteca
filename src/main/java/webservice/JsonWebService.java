/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import biblioteca.Entidade;
import com.google.gson.Gson;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.LeitorPropriedades;

/**
 *
 * @author MASC
 * @param <T> Tipo de Entidade
 */
public abstract class JsonWebService<T extends Entidade> {
    private LeitorPropriedades leitorPropriedades;
    
    public JsonWebService() {
        this.leitorPropriedades = new LeitorPropriedades(new String[]{"Mensagens.properties"});
    }

    protected String sucesso() {
        return getResposta(true, leitorPropriedades.get("mensagem.sucesso"));
    }

    private String getResposta(boolean sucesso, String mensagem) {
        RespostaJson respostaJson = new RespostaJson(sucesso, mensagem);
        Gson gson = new Gson();
        return gson.toJson(respostaJson);
    }

    protected Response getPdfResponse(byte[] bytes, String fileName) {
        return Response.ok(bytes, APPLICATION_OCTET_STREAM).
                header("content-disposition", "attachment; filename=" + fileName)
                .build();
    }

    protected String getErrorMessage(String chave) {
        RespostaJson respostaJson = new RespostaJson(false, leitorPropriedades.get(chave));
        return new Gson().toJson(respostaJson);
    }

    protected Response getJsonResponse(T entidade) {
        return getJsonResponse(entidade.toJson());
    }

    protected Response getJsonResponse(String json) {
        return Response.ok(json, MediaType.valueOf(APPLICATION_JSON + ";charset=UTF-8")).build();
    }
}
