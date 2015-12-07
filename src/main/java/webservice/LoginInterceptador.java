/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import acesso.Papel;
import java.util.logging.Logger;
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

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Object result = null;
        HttpServletRequest servletRequest = null;
        HttpHeaders httpHeaders;
        try {
            servletRequest = getHttpServletRequest(context);
            httpHeaders = getHttpHeaders(context);
            Logger.getGlobal().info("Trying Login...");
            String login = httpHeaders.getHeaderString("login");
            String senha = httpHeaders.getHeaderString("senha");
            Logger.getGlobal().info(login);
            Logger.getGlobal().info(senha);
            servletRequest.login(login, senha);
            Logger.getGlobal().info("Login ok");
            result = context.proceed();
        } catch (ServletException ex) {
            Logger.getGlobal().severe(ex.getMessage());
        } finally {
            servletRequest.logout();
        }

        return result;
    }
}
