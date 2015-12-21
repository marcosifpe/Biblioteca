/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excecao;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import util.ContentTypeUtil;
import webservice.Resposta;

/**
 *
 * @author MASC
 */
@Provider
public class MapeadorExcecao implements ExceptionMapper<Exception> {

    @Context
    protected HttpHeaders httpHeaders;
    @Context
    protected HttpServletResponse response;

    public Resposta getResposta(String mensagem) {
        return new Resposta(false, mensagem);
    }

    @Override
    public Response toResponse(Exception excecao) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        Resposta resposta;
        Throwable causa = excecao;

        while (causa != null) {
            if (causa instanceof NoResultException) {
                status = Response.Status.NOT_FOUND;
                break;
            } else if (causa instanceof EntityExistsException) {
                status = Response.Status.FOUND;
                break;
            } else if (causa instanceof ExcecaoSistema) {
                status = Response.Status.INTERNAL_SERVER_ERROR;
                break;
            } else if (causa instanceof ExcecaoNegocio) {
                if (((ExcecaoNegocio) causa).isAutorizacao()) {
                    status = Response.Status.UNAUTHORIZED;
                } else {
                    status = Response.Status.EXPECTATION_FAILED;
                }

                break;
            } else if (causa instanceof ConstraintViolationException) {
                status = Response.Status.EXPECTATION_FAILED;
                break;
            }

            causa = causa.getCause();
        }

        MensagemExcecao mensagemExcecao = new MensagemExcecao(causa);
        resposta = getResposta(mensagemExcecao.getMensagem());  
        new ContentTypeUtil().setContentType(httpHeaders, response);
        return Response.status(status).entity(resposta).build();
    }
}
