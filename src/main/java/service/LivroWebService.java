package service;

import biblioteca.Livro;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author MASC
 * Exemplo de Web Service.
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@WebService
public class LivroWebService extends Service<Livro> {
    @WebMethod(operationName = "getLivro")
    @WebResult(name = "livro")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Livro getLivro(@WebParam(name = "isbn", mode = WebParam.Mode.IN) String isbn) {
        return getSingleResult("LivroPorIsbn", new Object[]{isbn});
    }
}
