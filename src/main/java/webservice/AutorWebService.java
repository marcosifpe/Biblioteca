/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import interceptador.LoginInterceptador;
import biblioteca.Autor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

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
    @Produces(APPLICATION_JSON)
    @Interceptors({LoginInterceptador.class})
    public Response getAutor(@QueryParam("cpf") String cpf,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = autorService.getAutor(cpf);
        return super.getJsonResponse(autor);
    }

    @GET
    @Path("get/autores")
    @Produces(APPLICATION_JSON)
    @Interceptors({LoginInterceptador.class})
    public Response getAutores(@Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        List<Autor> autores = autorService.getAutores();
        return getAutores(autores);
    }

    private Response getAutores(List<Autor> autores) {
        GsonBuilder builder = new GsonBuilder();
        builder = builder.excludeFieldsWithoutExposeAnnotation();
        builder = builder.setDateFormat("dd/MM/yyyy hh:mm:ss");
        Gson gson = builder.create();
        Map autoresMap = new HashMap<String, List<Autor>>();
        autoresMap.put("autores", autores);
        return getJsonResponse(gson.toJson(autoresMap));
    }

    @POST
    @Path("salvar")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Interceptors({LoginInterceptador.class})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    /*
    * Está dando bug quando eu tento incluir e é levantada a exceção EntityExistsException (por quê?)
    */
    public Response salvarAutor(Autor jsonAutor,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        autorService.salvar(jsonAutor);
        return getJsonResponse(super.sucesso());
    }

    @PUT
    @Path("atualizar")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Interceptors({LoginInterceptador.class})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response atualizarAutor(@QueryParam("cpf") String cpf,
            Autor jsonAutor,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = autorService.getAutor(cpf);
        autor.setPrimeiroNome(jsonAutor.getPrimeiroNome());
        autor.setUltimoNome(jsonAutor.getUltimoNome());
        autorService.atualizar(autor);
        return getJsonResponse(super.sucesso());
    }

    @DELETE
    @Path("remover/{cfp}")
    @Produces(APPLICATION_JSON)
    @Interceptors({LoginInterceptador.class})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response removerAutor(@PathParam("cfp") String cpf,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        autorService.remover(cpf);
        return getJsonResponse(super.sucesso());
    }
}
