/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.excecao;

import com.google.gson.stream.MalformedJsonException;
import excecao.ExcecaoNegocio;
import excecao.ExcecaoSistema;
import excecao.MensagemExcecao;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import webservice.util.ContentTypeUtil;
import webservice.Resposta;

/**
 *
 * @author MASC
 */
@Provider
public class MapeadorExcecao implements ExceptionMapper<Throwable> {

    @Context
    protected HttpHeaders httpHeaders;
    @Context
    protected HttpServletResponse response;

    public Resposta getResposta(String mensagem) {
        return new Resposta(false, mensagem);
    }

    @Override
    public Response toResponse(Throwable excecao) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        Resposta resposta;
        Throwable causa = excecao;

        while (true) {
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
                status = Response.Status.BAD_REQUEST;
                break;
            } else if (causa instanceof NotFoundException) {
                status = Response.Status.NOT_FOUND;
                break;
            } else if (causa instanceof MalformedJsonException) {
                status = Response.Status.BAD_REQUEST;
                break;
            } else if (causa instanceof NotSupportedException) {
                status = Response.Status.BAD_REQUEST;
                break;                
            }

            if (causa.getCause() != null)
                causa = causa.getCause();
            else
                break;
        }

        MensagemExcecao mensagemExcecao = new MensagemExcecao(causa);
        resposta = getResposta(mensagemExcecao.getMensagem());
        ContentTypeUtil contentTypeUtil = new ContentTypeUtil(httpHeaders);
        return Response.status(status).entity(resposta).type(MediaType.valueOf(contentTypeUtil.getContentType())).build();
    }
}
