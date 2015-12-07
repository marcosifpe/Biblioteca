/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import biblioteca.Autor;
import com.google.gson.Gson;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
public class AutorWebService {
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
    public String salvarAutor(String jsonAutor, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        return null;
    }
}
