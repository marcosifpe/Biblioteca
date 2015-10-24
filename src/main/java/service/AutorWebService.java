package service;

import biblioteca.Autor;
import interceptador.EntityExistsInterceptor;
import interceptador.ValidatorInterceptor;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

/**
 *
 * @author MASC
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors({ValidatorInterceptor.class, EntityExistsInterceptor.class})
@WebService
public class AutorWebService {

    @PersistenceContext(name = "biblioteca", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @WebMethod(operationName = "salvarAutor")
    @WebResult(name = "status")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String[] criarAutor(@WebParam(name = "autor", mode = WebParam.Mode.IN) Autor autor) {
        checarExistencia(autor);
        entityManager.persist(autor);
        return new String[] {Boolean.TRUE.toString()};
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void checarExistencia(Autor autor) throws EntityExistsException {
        TypedQuery<Autor> query = entityManager.createNamedQuery("AutorPorCpf", Autor.class);
        query.setParameter("cpf", autor.getCpf());
        if (query.getSingleResult() != null) {
            throw new EntityExistsException(autor.getCpf());
        }
    }
}
