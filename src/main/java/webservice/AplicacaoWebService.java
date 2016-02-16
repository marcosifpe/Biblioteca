package webservice;

import java.util.HashMap;
import webservice.excecao.MapeadorExcecao;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ServerProperties;
import webservice.conversor.GsonGenericListWriter;
import webservice.conversor.GsonReader;
import webservice.conversor.GsonWriter;

/**
 * Define os componentes da aplicação JAX-RS, como por exemplo, web services.
 * @author MASC
 */
public class AplicacaoWebService extends Application {

    private Map<String, Object> appProperties;

    public AplicacaoWebService() {
        super();                
        appProperties = new HashMap<String, Object>();
        
        for (String key: super.getProperties().keySet()) {
            appProperties.put(key, super.getProperties().get(key));
        }
        
        //Propriedade permite envio de mensagens de erros de validação ao cliente.
        appProperties.put(ServerProperties.BV_SEND_ERROR_IN_RESPONSE,
                true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
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
        classes.add(MultiPartFeature.class);
        return classes;
    }

    @Override
    public Map<String, Object> getProperties() {
        return appProperties;
    }
}
