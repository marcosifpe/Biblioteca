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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.EXPECTATION_FAILED;
import static javax.ws.rs.core.Response.Status.FOUND;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
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

    private int getStatusCode(Response.Status status) {
        return status.getStatusCode();
    }

    @Override
    public Response toResponse(Throwable excecao) {
        int status = getStatusCode(INTERNAL_SERVER_ERROR);
        Resposta resposta;
        Throwable causa = excecao;

        while (true) {
            if (causa instanceof NoResultException) {
                status = getStatusCode(NOT_FOUND);
                break;
            } else if (causa instanceof EntityExistsException) {
                status = getStatusCode(FOUND);
                break;
            } else if (causa instanceof ExcecaoSistema) {
                status = getStatusCode(INTERNAL_SERVER_ERROR);
                break;
            } else if (causa instanceof ExcecaoNegocio) {
                if (((ExcecaoNegocio) causa).isAutorizacao()) {
                    status = getStatusCode(UNAUTHORIZED);
                } else {
                    status = getStatusCode(EXPECTATION_FAILED);
                }

                break;
            } else if (causa instanceof ConstraintViolationException) {
                status = getStatusCode(BAD_REQUEST);
                break;
            } else if (causa instanceof MalformedJsonException) {
                status = getStatusCode(BAD_REQUEST);
                break;
            } else if (causa instanceof WebApplicationException) {
                status = ((WebApplicationException) causa).getResponse().getStatus();
                break;
            }

            if (causa.getCause() != null) {
                causa = causa.getCause();
            } else {
                break;
            }
        }

        MensagemExcecao mensagemExcecao = new MensagemExcecao(causa);
        resposta = getResposta(mensagemExcecao.getMensagem());
        ContentTypeUtil contentTypeUtil = new ContentTypeUtil(httpHeaders);
        return Response.status(status).entity(resposta).type(MediaType.valueOf(contentTypeUtil.getContentType())).build();
    }
}
