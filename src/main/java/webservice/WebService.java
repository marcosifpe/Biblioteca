/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import biblioteca.ArquivoDigital;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import biblioteca.Entidade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;
import util.LeitorPropriedades;

/**
 *
 * @author MASC
 * @param <T> Tipo de Entidade
 */
public abstract class WebService<T extends Entidade> {

    private LeitorPropriedades leitorPropriedades;
    @Context 
    protected HttpHeaders httpHeaders;

    public WebService() {
        this.leitorPropriedades = new LeitorPropriedades(new String[]{"Mensagens.properties"});
    }
    
    protected String getMensagemSucesso() {
        return leitorPropriedades.get("mensagem.sucesso");
    }

    protected Response getPdf(ArquivoDigital arquivoDigital) {
        return Response.ok(arquivoDigital.getArquivo(), APPLICATION_OCTET_STREAM).
                header("content-disposition", "attachment; filename=" + arquivoDigital.getNome())
                .build();
    }
    
    private String getTipoResposta() {
        String tipoResposta = httpHeaders.getHeaderString(HttpHeaders.ACCEPT);

        if (tipoResposta == null) {
            tipoResposta = APPLICATION_JSON;
        }  
        
        return tipoResposta;
    }
    
    protected Response getRespostaSucesso() {
        Response resposta = null;

        switch (getTipoResposta()) {
            case APPLICATION_JSON:
                resposta = Response.ok(new Resposta(true, getMensagemSucesso()), MediaType.valueOf(APPLICATION_JSON + ";charset=UTF-8")).build();
                break;
            case APPLICATION_XML:
                resposta = Response.ok(new Resposta(true, getMensagemSucesso()), MediaType.valueOf(APPLICATION_XML + ";charset=UTF-8")).build();
                break;
        }        
        
        return resposta;
    }

    protected Response getResposta(T entidade) {
        Response resposta = null;

        switch (getTipoResposta()) {
            case APPLICATION_JSON:
                resposta = Response.ok(entidade.toJson(), MediaType.valueOf(APPLICATION_JSON + ";charset=UTF-8")).build();
                break;
            case APPLICATION_XML:
                resposta = Response.ok(entidade, MediaType.valueOf(APPLICATION_XML + ";charset=UTF-8")).build();
                break;
        }

        return resposta;
    }

    protected GenericEntity<List<T>> getListaGenerica(List<T> entidades) {
        return null;
    }
    
    protected Response getRespostaLista(List<T> entidades) {
        Response resposta = null;

        switch (getTipoResposta()) {
            case APPLICATION_JSON:
                GsonBuilder builder = new GsonBuilder();
                builder = builder.excludeFieldsWithoutExposeAnnotation();
                builder = builder.setDateFormat("dd/MM/yyyy hh:mm:ss");
                Gson gson = builder.create();
                resposta = Response.ok(getListaGenerica(entidades), MediaType.valueOf(APPLICATION_JSON + ";charset=UTF-8")).build();
                break;
            case APPLICATION_XML:
                resposta = Response.ok(getListaGenerica(entidades), MediaType.valueOf(APPLICATION_XML + ";charset=UTF-8")).build();
                break;
        }

        return resposta;

    }
}
