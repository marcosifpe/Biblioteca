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
import javax.security.auth.login.LoginException;
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
    private static final String CHAVE_LOGIN_INVALIDO = "acesso.login.invalido";
    
    @Resource
    private SessionContext contexto;

    private boolean valido(String valor) {
        return valor != null && valor.trim().length() > 0;
    }

    @AroundInvoke
    public Object interceptar(InvocationContext ic) throws Exception {
        Object resultado;
        HttpServletRequest request = null;
        HttpHeaders headers;

        try {
            request = getHttpServletRequest(ic);
            headers = getHttpHeaders(ic);

            String login = headers.getHeaderString("login");
            String senha = headers.getHeaderString("senha");

            if (valido(login) && valido(senha)) {
                try {
                    request.login(login, senha);
                    request.getSession(true);

                    if (contexto.isCallerInRole(Papel.ADMINISTRADOR)) {
                        resultado = ic.proceed();
                    } else {
                        throw new LoginException(getMensagem(CHAVE_ACESSO_NAO_AUTORIZADO));
                    }

                } catch (ServletException ex) {
                    throw new LoginException(getMensagem(CHAVE_LOGIN_INVALIDO));
                }
            } else {
                throw new LoginException(getMensagem(CHAVE_CREDENCIAIS_OMITIDAS));
            }
        } finally {
            if (request != null) {
                if (request.getSession(false) != null) {
                    request.getSession(false).invalidate();
                }

                request.logout();
            }
        }

        return resultado;
    }
}
