/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptador;

import excecao.ExcecaoNegocio;
import excecao.ExcecaoSistema;
import java.util.Set;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author MASC
 */
public class ExcecaoInterceptador extends JsonInterceptador {

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Object result = null;
        boolean found = false;
        try {
            result = context.proceed();
        } catch (Throwable throwable) {
            @SuppressWarnings("ThrowableResultIgnored")
            Throwable cause = throwable;
            while (cause != null) {
                if (cause instanceof EntityExistsException) {
                    found = true;
                    result = super.getJson(cause.getClass().getName());
                    break;
                } else if (cause instanceof NoResultException) {
                    found = true;                    
                    result = super.getJson(cause.getClass().getName());
                    break;                    
                } else if (cause instanceof ConstraintViolationException) {
                    found = true;                    
                    ConstraintViolationException violations = (ConstraintViolationException) cause;
                    StringBuilder builder = new StringBuilder();
                    Set<ConstraintViolation<?>> constraintViolations = violations.getConstraintViolations();

                    for (ConstraintViolation violation : constraintViolations) {
                        if (builder.length() != 0) {
                            builder.append("; ");
                        }

                        builder.append(violation.getPropertyPath());
                        builder.append(" ");
                        builder.append(violation.getMessage());
                    }

                    result = super.getJson(cause.getClass().getName(), builder.toString());
                    break;                  
                } else if (cause instanceof ExcecaoNegocio) {
                    found = true;                    
                    result = super.getJson(cause);
                } else if (cause instanceof ExcecaoSistema) {
                    found = true;
                    result = super.getJson(cause);
                }
                
                cause = cause.getCause();
            }

            if (!found)
                throw throwable;
        }

        return result;
    }
}
