/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import biblioteca.Livro;
import com.google.gson.Gson;
import java.io.StringReader;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
@Interceptors({LoginInterceptador.class})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LivroWebService {

    @EJB
    private LivroService livroService;

    @POST
    @Path("isbn")
    @Produces("application/json")
    @Consumes("application/json")
    public String getLivro(String jsonIsbn, @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        String isbn = getIsbn(jsonIsbn);
        Livro livro = livroService.getLivro(isbn);
        Gson gson = new Gson();
        return gson.toJson(livro.getMap());
    }

    private String getIsbn(String jsonIsbn) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonIsbn));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        return jsonObject.getString("isbn");
    }
}
