/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author MASC
 */
@NotBlank(message = "{biblioteca.ISBN}")
@Size(max = 17, message = "{biblioteca.ISBN}")
@Pattern(regexp = "[0-9]{3}-[0-9]{2}-[0-9]{4}-[0-9]{3}-[0-9]{1}", message = "{biblioteca.ISBN}")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ISBN {

    String message() default "{biblioteca.ISBN}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
