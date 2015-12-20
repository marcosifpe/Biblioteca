package webservice;

import biblioteca.Editora;
import interceptador.LoginInterceptador;
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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import service.EditoraService;

/**
 *
 * @author MASC
 */
@Path("editora")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors({LoginInterceptador.class})
public class EditoraWebService extends JsonWebService<Editora> {

    @EJB
    private EditoraService editoraService;

    @GET
    @Path("get/nome")
    @Produces({APPLICATION_JSON})
    public Response getEditora(@QueryParam("nome") String nome,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Editora editora = editoraService.getEditora(nome);
        return super.getJsonResponse(editora);
    }

}
