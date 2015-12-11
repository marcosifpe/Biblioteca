/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import interceptador.LoginInterceptador;
import interceptador.ExcecaoInterceptador;
import biblioteca.Livro;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import service.LivroService;

/**
 *
 * @author MASC
 */
@Path("livro")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LivroWebService {

    @EJB
    private LivroService livroService;

    @GET
    @Path("isbn/{isbn}")
    @Produces("application/json")
    @Interceptors({LoginInterceptador.class, ExcecaoInterceptador.class})
    public String getLivro(@PathParam("isbn") String isbn, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Livro livro = livroService.getLivro(isbn);
        return livro.toJson();
    }
}
