package webservice;

import biblioteca.Editora;
import excecao.ExcecaoNegocio;
import interceptador.LoginInterceptador;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;
import org.hibernate.validator.constraints.NotBlank;
import servico.EditoraServico;

/**
 *
 * @author MASC
 */
@Path("editora")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors({LoginInterceptador.class})
public class EditoraWebService extends WebService<Editora> {

    @EJB
    private EditoraServico editoraService;

    @GET
    @Path("get/nome")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getEditora(
            @QueryParam("nome") @NotBlank String nome,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Editora editora = editoraService.getEditora(nome);
        return super.getResposta(editora);
    }
    
    @POST
    @Path("salvar")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)    
    public Response salvar(@Valid Editora editora,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        editoraService.salvar(editora);
        return super.getRespostaSucesso();
    }

    @DELETE
    @Path("remover")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response remover(@QueryParam("nome") @NotBlank String nome,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        editoraService.remover(nome);
        return super.getRespostaSucesso();
    }
    
}
