/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptador;

import acesso.Papel;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author MASC
 */
public class LoginInterceptador extends JsonInterceptador {

    private static final String CHAVE_ACESSO_NAO_AUTORIZADO = "acesso.nao.autorizado";
    private static final String CHAVE_CREDENCIAIS_OMITIDAS = "acesso.credenciais.omitidadas";

    @Resource
    private SessionContext sessionContext;

    private HttpServletRequest getHttpServletRequest(InvocationContext ic) {
        HttpServletRequest request = null;
        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpServletRequest) {
                request = (HttpServletRequest) parameter;
            }
        }

        return request;
    }

    private HttpHeaders getHttpHeaders(InvocationContext ic) {
        HttpHeaders headers = null;

        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpHeaders) {
                headers = (HttpHeaders) parameter;
            }
        }

        return headers;
    }

    private boolean isValid(String value) {
        return value != null && value.trim().length() > 0;
    }

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Object result;
        HttpServletRequest servletRequest = null;
        HttpHeaders httpHeaders;

        try {
            servletRequest = getHttpServletRequest(context);
            httpHeaders = getHttpHeaders(context);

            String login = httpHeaders.getHeaderString("login");
            String senha = httpHeaders.getHeaderString("senha");

            if (isValid(login) && isValid(senha)) {
                try {
                    servletRequest.login(login, senha);
                    servletRequest.getSession(true);

                    if (sessionContext.isCallerInRole(Papel.ADMINISTRADOR)) {
                        result = context.proceed();
                    } else {
                        result = super.getJson(CHAVE_ACESSO_NAO_AUTORIZADO);
                    }

                } catch (ServletException ex) {
                    result = super.getJson(ex.getClass().getName());
                }
            } else {
                result = super.getJson(CHAVE_CREDENCIAIS_OMITIDAS);
            }
        } finally {
            if (servletRequest != null) {
                if (servletRequest.getSession(false) != null) {
                    servletRequest.getSession(false).invalidate();
                }
                servletRequest.logout();
            }
        }

        return result;
    }
}
