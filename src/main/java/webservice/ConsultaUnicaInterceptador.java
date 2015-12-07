/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import javax.ejb.EJBException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.NoResultException;

/**
 *
 * @author MASC
 */
public class ConsultaUnicaInterceptador extends JsonInterceptador {

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Object result;
        try {
            result = context.proceed();
        } catch (EJBException ex) {
            if (ex.getCause() instanceof NoResultException) {
                result = getResult("erro", "Objeto inexistente");
            } else {
                throw ex;
            }
        }

        return result;
    }

}
