package webservice;

import biblioteca.ISBN;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

import biblioteca.Livro;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import servico.LivroServico;

/**
 *
 * @author MASC
 */
@Path("livro")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LivroWebService extends WebService<Livro> {

    @EJB
    private LivroServico livroService;

    @GET
    @Path("isbn/{isbn}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getLivro(@PathParam("isbn")
            @ISBN String isbn, @Context HttpHeaders httpHeaders) {
        Livro livro = livroService.getLivro(isbn);
        return super.getResposta(livro);
    }

    @GET
    @Path("pdf")
    @Produces({APPLICATION_OCTET_STREAM, APPLICATION_JSON, APPLICATION_XML})
    public Response getPdf(
            @QueryParam("isbn")
            @ISBN String isbn, @Context HttpHeaders httpHeaders) {
        Livro livro = livroService.getLivroComArquivo(isbn);
        return super.getPdf(livro.getArquivoDigital());
    }
}
