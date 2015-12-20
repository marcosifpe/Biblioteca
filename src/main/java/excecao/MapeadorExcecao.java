/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excecao;

import excecao.util.MensagemExcecao;
import java.util.Set;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.security.auth.login.LoginException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import util.LeitorPropriedades;
import webservice.RespostaJson;

/**
 *
 * @author MASC
 */
@Provider
public class MapeadorExcecao implements ExceptionMapper<Exception> {

    private LeitorPropriedades leitor;

    public MapeadorExcecao() {
        this.leitor = new LeitorPropriedades(new String[]{"Exception.properties", "Mensagens.properties"});
    }

    public RespostaJson getRespostaJson(String mensagem) {
        return new RespostaJson(false, mensagem);
    }

    @Override
    public Response toResponse(Exception excecao) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        RespostaJson respostaJson;
        Throwable causa = excecao;

        while (causa != null) {
            if (causa instanceof ExcecaoLogin) {
                status = Response.Status.UNAUTHORIZED;
                break;
            } else if (causa instanceof NoResultException) {
                status = Response.Status.NOT_FOUND;
                break;
            } else if (causa instanceof EntityExistsException) {
                status = Response.Status.FOUND;
                break;
            } else if (causa instanceof ExcecaoSistema) {
                status = Response.Status.INTERNAL_SERVER_ERROR;
                break;
            } else if (causa instanceof ExcecaoNegocio) {
                status = Response.Status.EXPECTATION_FAILED;
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
