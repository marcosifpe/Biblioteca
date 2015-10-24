package interceptador;

import java.util.Set;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author MASC
 */
public class ValidatorInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        try {
            return context.proceed();
        } catch (ConstraintViolationException ex) {
            StringBuilder str = new StringBuilder();
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations) {
                if (str.length() != 0) {
                    str.append("; ");
                }

                str.append(violation.getPropertyPath());
                str.append(": ");
                str.append(violation.getMessage());
            }

            return new String[]{Boolean.FALSE.toString(), str.toString()};
        }
    }
}
