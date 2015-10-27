package interceptador;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author MASC
 */
public class ExcecaoInterceptador {
    private Properties properties;
    private static final Logger LOGGER = Logger.getLogger(ExcecaoInterceptador.class.getName());
    
    public ExcecaoInterceptador() {
        properties = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("Exception.properties");
        
        if (is != null) {
            try {
                properties.load(is);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        StringBuilder str, str2;
        try {
            return context.proceed();
        } catch (ConstraintViolationException ex) {
            str = new StringBuilder();
            str2 = new StringBuilder();
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            
            for (ConstraintViolation violation : constraintViolations) {
                if (str2.length() != 0) {
                    str2.append("; ");
                }

                str2.append(violation.getPropertyPath());
                str2.append(": ");
                str2.append(violation.getMessage());
            }
            
            str.append(String.format((String) properties.get("javax.validation.ConstraintViolationException"), str2.toString()));
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            return new String[]{Boolean.FALSE.toString(), str.toString()};
        } catch (EntityExistsException ex) {
            str = new StringBuilder();
            str.append(String.format((String) properties.get("javax.persistence.EntityExistsException"), ex.getMessage()));
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            return new String[]{Boolean.FALSE.toString(), str.toString()};
        } catch (Exception ex) {
            str = new StringBuilder();
            str.append(properties.get("java.lang.Exception"));
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return new String[]{Boolean.FALSE.toString(), str.toString()};            
        }
    }
}
