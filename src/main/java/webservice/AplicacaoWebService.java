package webservice;

import excecao.MapeadorExcecao;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author MASC
 */
public class AplicacaoWebService extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(AutorWebService.class);
        classes.add(EditoraWebService.class);
        classes.add(LivroWebService.class);
        classes.add(MapeadorExcecao.class);
        return classes;
    }
}
