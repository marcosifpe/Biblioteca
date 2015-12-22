/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import biblioteca.ArquivoDigital;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import biblioteca.Entidade;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import webservice.util.ContentTypeUtil;
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
    @Context
    protected HttpServletResponse response;

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

    protected Response getRespostaSucesso() {
        new ContentTypeUtil(httpHeaders).setContentType(response);        
        return Response.ok(new Resposta(true, getMensagemSucesso())).build();
    }

    protected Response getResposta(T entidade) {
        new ContentTypeUtil(httpHeaders).setContentType(response);
        return Response.ok(entidade).build();
    }

    protected GenericEntity<List<T>> getListaGenerica(List<T> entidades) {
        return null;
    }

    @Deprecated
    protected Response getRespostaListaOld(List<T> entidades) {
        ContentTypeUtil contentTypeUtil = new ContentTypeUtil(httpHeaders);
        contentTypeUtil.setContentType(response);        
        return Response.ok(getListaGenerica(entidades)).build();
    }
    
    protected Response getRespostaLista(List<T> entidades) {
        ContentTypeUtil contentTypeUtil = new ContentTypeUtil(httpHeaders);
        contentTypeUtil.setContentType(response);        
        return Response.ok(entidades).build();    
    }    
}
