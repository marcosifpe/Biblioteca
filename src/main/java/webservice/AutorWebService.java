/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import biblioteca.Autor;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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

/**
 *
 * @author MASC
 */
@Path("autor")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AutorWebService extends JsonWebService<Autor>{
    @EJB
    private AutorService autorService;

    @GET
    @Path("get")
    @Produces("application/json")    
    @Interceptors({LoginInterceptador.class, ConsultaUnicaInterceptador.class})
    public String getAutor(@QueryParam("cpf") String cpf, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = autorService.getAutor(cpf);
        return new Gson().toJson(autor.getMap());
    }
    
    @POST
    @Path("salvar")
    @Produces("application/json")
    @Consumes("application/json")    
    @Interceptors({LoginInterceptador.class, SalvarInterceptador.class})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String salvarAutor(String jsonAutor, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws IOException {
        Autor autor = super.get(jsonAutor, Autor.class);
        autorService.salvar(autor);
        return super.getResposta(true, "Sucesso");
    }
    
    @DELETE
    @Path("remover/{cfp}")
    @Produces("application/json")   
    @Interceptors({LoginInterceptador.class})
    public String removerAutor(@PathParam("cfp") String cpf, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        autorService.remover(cpf);
        return super.getResposta(true, "Sucesso");
    } 
}
