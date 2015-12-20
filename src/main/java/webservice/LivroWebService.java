/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

import biblioteca.ArquivoDigital;
import interceptador.ExcecaoInterceptador;
import biblioteca.Livro;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import service.LivroService;

/**
 *
 * @author MASC
 */
@Path("livro")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LivroWebService extends JsonWebService<Livro> {

    @EJB
    private LivroService livroService;

    @GET
    @Path("isbn/{isbn}")
    @Produces(APPLICATION_JSON)
    public Response getLivro(@PathParam("isbn") String isbn, @Context HttpServletResponse response) {
        Livro livro = livroService.getLivro(isbn);
        return super.getJsonResponse(livro);
    }

    @GET
    @Path("pdf")
    @Produces({APPLICATION_OCTET_STREAM, APPLICATION_JSON})
    public Response getPdf(@QueryParam("isbn") String isbn, @Context HttpServletResponse response) {
        Livro livro = livroService.getLivro(isbn);
        ArquivoDigital arquivoDigital = livro.getArquivoDigital();
        if (arquivoDigital != null) {
            return super.getPdfResponse(arquivoDigital);
        } else {
            return super.getJsonResponse(getErrorMessage("livro.nao.encontrado"));
        }
    }
}
