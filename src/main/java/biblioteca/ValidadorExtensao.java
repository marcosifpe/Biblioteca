package biblioteca;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author MASC
 */
public class ValidadorExtensao implements ConstraintValidator<ExtensaoDocumento, String> {
    private List<String> extensoesValidas;
    
    @Override
    public void initialize(ExtensaoDocumento extensaoDocumento) {
        this.extensoesValidas = new ArrayList<>();
        this.extensoesValidas.add("application/pdf");
        this.extensoesValidas.add("application/mobi");
        this.extensoesValidas.add("application/epub");        
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext cvc) {
        return valor == null ? false : extensoesValidas.contains(valor);
    }    
}
