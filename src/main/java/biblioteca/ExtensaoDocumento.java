package biblioteca;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author MASC
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorExtensao.class)
@Documented
public @interface ExtensaoDocumento {
    String message() default "{prova.ArquivoDigital.extensao}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
