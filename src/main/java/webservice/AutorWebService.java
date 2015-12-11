/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import interceptador.LoginInterceptador;
import interceptador.ExcecaoInterceptador;
import biblioteca.Autor;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import service.AutorService;
import excecao.ExcecaoNegocio;

/**
 *
 * @author MASC
 */
@Path("autor")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AutorWebService extends JsonWebService<Autor> {

    @EJB
    private AutorService autorService;

    @GET
    @Path("get")
    @Produces("application/json")
    @Interceptors({LoginInterceptador.class, ExcecaoInterceptador.class})
    public String getAutor(@QueryParam("cpf") String cpf, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = autorService.getAutor(cpf);
        return autor.toJson();
    }

    @POST
    @Path("salvar")
    @Produces("application/json")
    @Consumes("application/json")
    @Interceptors({LoginInterceptador.class, ExcecaoInterceptador.class})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String salvarAutor(String jsonAutor, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = new Autor();
        autor.setAtributos(jsonAutor);
        autorService.salvar(autor);
        return super.getRespostaSucesso();
    }

    @POST
    @Path("atualizar")
    @Produces("application/json")
    @Consumes("application/json")
    @Interceptors({LoginInterceptador.class, ExcecaoInterceptador.class})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String atualizarAutor(@QueryParam("cpf") String cpf, String jsonAutor, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = autorService.getAutor(cpf);
        autor.setAtributos(jsonAutor);
        autorService.atualizar(autor);
        return super.getRespostaSucesso();
    }

    @DELETE
    @Path("remover/{cfp}")
    @Produces("application/json")
    @Interceptors({LoginInterceptador.class, ExcecaoInterceptador.class})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String removerAutor(@PathParam("cfp") String cpf, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        autorService.remover(cpf);
        return super.getRespostaSucesso();
    }
}
