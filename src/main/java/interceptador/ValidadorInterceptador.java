/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptador;

import biblioteca.Entidade;
import excecao.ExcecaoSistema;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author MASC
 */
public class ValidadorInterceptador {

    private List<Entidade> getEntidades(InvocationContext contexto) {
        List<Entidade> entidades = new ArrayList<Entidade>();
        for (Object objeto : contexto.getParameters()) {
            if (objeto instanceof Entidade) {
                entidades.add((Entidade) objeto);
            }
        }

        return entidades;
    }

    private HttpServletRequest getHttpServletRequest(InvocationContext contexto) {
        HttpServletRequest request = null;
        for (Object parameter : contexto.getParameters()) {
            if (parameter instanceof HttpServletRequest) {
                request = (HttpServletRequest) parameter;
            }
        }

        return request;
    }
    
    private HttpServletResponse getHttpServletResponse(InvocationContext contexto) {
        HttpServletResponse response = null;
        for (Object parameter : contexto.getParameters()) {
            if (parameter instanceof HttpServletResponse) {
                response = (HttpServletResponse) parameter;
            }
        }

        return response;
    }    

    private HttpHeaders getHttpHeaders(InvocationContext contexto) {
        HttpHeaders headers = null;

        for (Object parameter : contexto.getParameters()) {
            if (parameter instanceof HttpHeaders) {
                headers = (HttpHeaders) parameter;
            }
        }

        return headers;
    }

    @AroundInvoke
    public Object interceptar(InvocationContext contexto) throws Exception {
        List<Entidade> entidades = getEntidades(contexto);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        for (Entidade entidade : entidades) {
            Set<ConstraintViolation<Entidade>> violations = validator.validate(entidade);

            if (!violations.isEmpty()) {
                throw new Exception(new ConstraintViolationException(violations));
            }
        }

        return contexto.proceed();
    }
}
