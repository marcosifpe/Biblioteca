/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excecao;

import excecao.util.MensagemExcecao;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import webservice.RespostaJson;

/**
 *
 * @author MASC
 */
@Provider
public class MapeadorExcecao implements ExceptionMapper<Exception> {
    public RespostaJson getRespostaJson(String mensagem) {
        return new RespostaJson(false, mensagem);
    }

    @Override
    public Response toResponse(Exception excecao) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        RespostaJson respostaJson;
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
        respostaJson = getRespostaJson(mensagemExcecao.getMensagem());        
        return Response.status(status).entity(respostaJson).type(MediaType.valueOf(APPLICATION_JSON + ";charset=UTF-8")).build();
    }
}
