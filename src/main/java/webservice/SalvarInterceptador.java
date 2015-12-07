/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.util.Set;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author MASC
 */
public class SalvarInterceptador extends JsonInterceptador {

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Object result = null;
        boolean found = false;
        try {
            result = context.proceed();
        } catch (Exception ex) {
            @SuppressWarnings("ThrowableResultIgnored")
            Throwable ex1 = ex.getCause();
            while (ex1 != null) {
                if (ex1 instanceof EntityExistsException) {
                    found = true;
                    result = getResult("erro", "Objeto existente");
                    break;
                } else if (ex1 instanceof ConstraintViolationException) {
                    found = true;                    
                    ConstraintViolationException violations = (ConstraintViolationException) ex1;
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

                    str.append(String.format("Erro(s) de validação: %s", str2.toString()));
                    result = getResult("erro", str.toString());
                    break;
                }
                
                ex1 = ex1.getCause();
            }

            if (!found)
                throw ex;
        }

        return result;
    }
}
