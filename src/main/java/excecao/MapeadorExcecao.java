/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excecao;

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
        Response.Status status = null;
        RespostaJson respostaJson = null;
        Throwable causa = excecao;

        while (causa != null) {
            if (causa instanceof LoginException) {
                status = Response.Status.UNAUTHORIZED;
                respostaJson = getRespostaJson(causa.getMessage());
                break;
            } else if (causa instanceof NoResultException) {
                status = Response.Status.NOT_FOUND;
                respostaJson = getRespostaJson(leitor.get(causa.getClass().getName()));
                break;
            } else if (causa instanceof EntityExistsException) {
                status = Response.Status.FOUND;
                respostaJson = getRespostaJson(leitor.get(causa.getClass().getName()));
                break;
            } else if (causa instanceof ExcecaoSistema) {
                status = Response.Status.INTERNAL_SERVER_ERROR;
                respostaJson = getRespostaJson(leitor.get(causa.getClass().getName()));
                break;
            } else if (causa instanceof ExcecaoNegocio) {
                status = Response.Status.EXPECTATION_FAILED;
                respostaJson = getRespostaJson(causa.getMessage());
                break;
            } else if (causa instanceof ConstraintViolationException) {
                ConstraintViolationException violacoes = (ConstraintViolationException) causa;
                StringBuilder builder = new StringBuilder();
                Set<ConstraintViolation<?>> constraintViolations = violacoes.getConstraintViolations();

                for (ConstraintViolation violation : constraintViolations) {
                    if (builder.length() != 0) {
                        builder.append("; ");
                    }

                    builder.append(violation.getPropertyPath());
                    builder.append(" ");
                    builder.append(violation.getMessage());
                }

                status = Response.Status.EXPECTATION_FAILED;
                respostaJson = getRespostaJson(String.format(leitor.get(causa.getClass().getName()), builder.toString()));
                break;
            }
            
            causa = causa.getCause();
        }

        /*
         * Mensagem padrão
         */
        if (respostaJson == null) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            respostaJson = getRespostaJson(leitor.get(Exception.class.getName()));
        }

        return Response.status(status).entity(respostaJson).type(MediaType.valueOf(APPLICATION_JSON + ";charset=UTF-8")).build();
    }
}
