/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import acesso.Papel;
import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;
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
public class LoginInterceptador {

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

    private String getResult(String status, String mensage) {
        Gson gson = new Gson();
        Map jsonMap = new HashMap<String, String>();
        jsonMap.put("status", status);
        jsonMap.put("mensagem", mensage);
        return gson.toJson(jsonMap);
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
                        result = getResult("erro", "acesso desautorizado");
                    }

                } catch (ServletException ex) {
                    result = getResult("erro", "falha na tentativa de login");
                }
            } else {
                result = getResult("erro", "credenciais omitidas");
            }
        } finally {
            if (servletRequest != null) {
                servletRequest.getSession().invalidate();
                servletRequest.logout();
            }
        }

        return result;
    }
}
