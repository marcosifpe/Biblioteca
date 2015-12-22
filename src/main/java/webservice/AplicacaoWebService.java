package webservice;

import webservice.excecao.MapeadorExcecao;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import webservice.conversor.GsonGenericListWriter;
import webservice.conversor.GsonReader;
import webservice.conversor.GsonWriter;

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
        classes.add(GsonWriter.class);
        classes.add(GsonReader.class);
        classes.add(GsonGenericListWriter.class);
        return classes;
    }
}
