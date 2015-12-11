/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import service.ExcecaoNegocio;
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
                    result = super.getJson(false, (String) properties.get(cause.getClass().getName()));
                    break;
                } else if (cause instanceof NoResultException) {
                    found = true;                    
                    result = super.getJson(false, (String) properties.get(cause.getClass().getName()));
                    break;                    
                } else if (cause instanceof ConstraintViolationException) {
                    found = true;                    
                    ConstraintViolationException violations = (ConstraintViolationException) cause;
                    StringBuilder str = new StringBuilder();
                    StringBuilder str2 = new StringBuilder();
                    Set<ConstraintViolation<?>> constraintViolations = violations.getConstraintViolations();

                    for (ConstraintViolation violation : constraintViolations) {
                        if (str2.length() != 0) {
                            str2.append("; ");
                        }

                        str2.append(violation.getPropertyPath());
                        str2.append(" ");
                        str2.append(violation.getMessage());
                    }

                    str.append(String.format((String) properties.get(cause.getClass().getName()), str2.toString()));
                    result = super.getJson(false, str.toString());
                    break;                  
                } else if (cause instanceof ExcecaoNegocio) {
                    found = true;                    
                    result = super.getJson(false, cause.getMessage());
                }
                
                cause = cause.getCause();
            }

            if (!found)
                throw throwable;
        }

        return result;
    }
}
