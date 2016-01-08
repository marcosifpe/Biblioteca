package webservice;

import biblioteca.ArquivoDigital;
import biblioteca.ISBN;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

import biblioteca.Livro;
import excecao.ExcecaoNegocio;
import interceptador.LoginInterceptador;
import java.io.File;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
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

    @POST
    @Path("salvar")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Interceptors({LoginInterceptador.class})
    public Response salvar(
            @Valid Livro livro,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        livroService.salvar(livro);
        return super.getRespostaSucesso();
    }

    @POST
    @Path("atualizar/documento/{isbn}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes({MULTIPART_FORM_DATA})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Interceptors({LoginInterceptador.class})
    public Response atualizar(
            @PathParam("isbn") @ISBN String isbn,
            @FormDataParam("file") FormDataBodyPart body,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio, IOException {
        ContentDisposition contentDisposition = body.getContentDisposition();
        File file = body.getValueAs(File.class);
        Livro livro = livroService.getLivro(isbn);
        ArquivoDigital arquivoDigital = livro.criarArquivoDigital();
        arquivoDigital.setArquivo(FileUtils.readFileToByteArray(file));
        arquivoDigital.setNome(contentDisposition.getFileName());
        arquivoDigital.setExtensao(body.getMediaType().toString());
        livro.setArquivoDigital(arquivoDigital);
        return super.getRespostaSucesso();
    }
}
