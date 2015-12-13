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
    public Object interceptar(InvocationContext ic) throws Exception {
        Object resultado = null;
        boolean encontrado = false;
        
        try {        
            resultado = ic.proceed();
        } catch (Throwable throwable) {
            Throwable causa = throwable;
            while (causa != null) {
                if (causa instanceof EntityExistsException) {
                    encontrado = true;
                    resultado = super.getJsonErrorResponse(causa.getClass().getName());
                    break;
                } else if (causa instanceof NoResultException) {
                    encontrado = true;          
                    resultado = super.getJsonErrorResponse(causa.getClass().getName());
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

                    encontrado = true;                    
                    resultado = super.getJsonErrorResponse(causa.getClass().getName(), builder.toString());
                    break;                  
                } else if (causa instanceof ExcecaoNegocio) {
                    encontrado = true;            
                    resultado = super.getJsonErrorResponse(causa);
                } else if (causa instanceof ExcecaoSistema) {
                    encontrado = true;
                    resultado = super.getJsonErrorResponse(causa);
                }
                
                causa = causa.getCause();
            }

            if (!encontrado)
                throw throwable;
        }
        
        return resultado;
    }
}
