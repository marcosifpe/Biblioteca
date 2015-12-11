/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
    @Produces("application/json")
    @Interceptors({ExcecaoInterceptador.class})
    public Response getLivro(@PathParam("isbn") String isbn) {
        Livro livro = livroService.getLivro(isbn);
        return Response.ok(livro.toJson(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("pdf")
    @Produces({"application/pdf", "application/json"})
    @Interceptors({ExcecaoInterceptador.class})
    public Response getPdf(@QueryParam("isbn") String isbn) {
        Livro livro = livroService.getLivro(isbn);
        ArquivoDigital arquivoDigital = livro.getArquivoDigital();

        if (arquivoDigital != null) {
            return Response.ok(arquivoDigital.getArquivo(), MediaType.APPLICATION_OCTET_STREAM).
                    header("content-disposition", "attachment; filename=" + arquivoDigital.getNome())
                    .build();
        } else {
            return Response.ok(getResposta(false, "Arquivo não encontrado"), MediaType.APPLICATION_JSON).build();
        }
    }
}
