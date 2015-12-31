package webservice.conversor;

import biblioteca.Entidade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import static java.nio.charset.StandardCharsets.UTF_8;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author MASC
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GsonWriter implements MessageBodyWriter<Entidade> {

    @Override
    public boolean isWriteable(Class type, Type genericType,
            Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(Entidade t, Class type, Type genericType,
            Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Entidade t, Class type, Type genericType,
            Annotation[] annotations, MediaType mediaType,
            MultivaluedMap httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().
                setDateFormat("dd/MM/yyyy HH:mm:ss").create();
        entityStream.write(gson.toJson(t).getBytes(UTF_8.name()));
    }
}
