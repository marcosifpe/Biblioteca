/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import interceptador.LoginInterceptador;
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
import servico.AutorServico;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.validation.Valid;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author MASC
 */
@Path("autor")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors({LoginInterceptador.class})
@ValidateOnExecution(type = ExecutableType.NON_GETTER_METHODS)
public class AutorWebService extends WebService<Autor> {

    @EJB
    private AutorServico autorServico;

    @GET
    @Path("get")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getAutor(@QueryParam("cpf") @CPF String cpf,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        Autor autor = autorServico.getAutor(cpf);
        return super.getResposta(autor);
    }

    @GET
    @Path("get/autores")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Response getAutores(@Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) {
        List<Autor> autores = autorServico.getAutores();
        return getRespostaLista(autores);
    }

    @Override
    protected GenericEntity<List<Autor>> getListaGenerica(List<Autor> entidades) {
        return new GenericEntity<List<Autor>>(entidades){};
    }    
    
    @POST
    @Path("salvar")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes(APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response salvarAutor(@Valid Autor autor,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        autor.setId(null);
        autorServico.salvar(autor);
        return super.getRespostaSucesso();
    }

    @PUT
    @Path("atualizar")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Consumes(APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response atualizarAutor(@Valid Autor autor,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {        
        autorServico.atualizar(autor);
        return super.getRespostaSucesso();
    }

    @DELETE
    @Path("remover/{cfp}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response removerAutor(@PathParam("cfp") @CPF String cpf,
            @Context HttpServletRequest request,
            @Context HttpHeaders httpHeaders) throws ExcecaoNegocio {
        autorServico.remover(cpf);
        return super.getRespostaSucesso();
    }
}
