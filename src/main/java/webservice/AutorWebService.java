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
import servico.AutorServico;
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
@Interceptors({LoginInterceptador.class})
public class AutorWebService extends JsonWebService<Autor> {

    @EJB
    private AutorServico autorServico;

    @GET
    @Path("get")
    @Produces(APPLICATION_JSON)
    public Response getAutor(@QueryParam("cpf") String cpf,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = autorServico.getAutor(cpf);
        return super.getJsonResponse(autor);
    }

    @GET
    @Path("get/autores")
    @Produces(APPLICATION_JSON)
    public Response getAutores(@Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        List<Autor> autores = autorServico.getAutores();
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response salvarAutor(Autor autor,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        autorServico.salvar(autor);
        return getJsonResponse(super.sucesso());
    }

    @PUT
    @Path("atualizar")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response atualizarAutor(@QueryParam("cpf") String cpf,
            Autor autorJson,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = autorServico.getAutor(cpf);
        autor.setAtributos(autorJson);
        autorServico.atualizar(autor);
        return getJsonResponse(super.sucesso());
    }

    @DELETE
    @Path("remover/{cfp}")
    @Produces(APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response removerAutor(@PathParam("cfp") String cpf,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        autorServico.remover(cpf);
        return getJsonResponse(super.sucesso());
    }
}
