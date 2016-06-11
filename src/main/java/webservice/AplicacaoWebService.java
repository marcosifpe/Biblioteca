package webservice;

import java.util.HashMap;
import webservice.excecao.MapeadorExcecao;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;
import webservice.conversor.GsonGenericListWriter;
import webservice.conversor.GsonReader;
import webservice.conversor.GsonWriter;

/**
 * Define os componentes da aplicação JAX-RS, como por exemplo, web services.
 * @author MASC
 */
public class AplicacaoWebService extends Application {

    private final Map<String, Object> appProperties;

    public AplicacaoWebService() {
        super();                
        appProperties = new HashMap<>();
        
        for (String key: super.getProperties().keySet()) {
            appProperties.put(key, super.getProperties().get(key));
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        //Web services
        classes.add(AutorWebService.class);
        classes.add(EditoraWebService.class);
        classes.add(LivroWebService.class);
        classes.add(MapeadorExcecao.class);
        //Conversor para o formato JSON
        classes.add(GsonWriter.class);
        classes.add(GsonReader.class);
        classes.add(GsonGenericListWriter.class);
        //Permite envio de arquivos em requisições
        return classes;
    }

    @Override
    public Map<String, Object> getProperties() {
        return appProperties;
    }
}
