package service;

import biblioteca.Autor;
import dao.DaoGenerico;
import javax.ejb.EJB;
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
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@WebService
public class AutorWebService {
    @EJB
    private DaoGenerico daoGenerico;
    
    @WebMethod(operationName = "salvarAutor")
    @WebResult(name = "sucesso")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean criarAutor(@WebParam(name = "autor", mode = WebParam.Mode.IN) Autor autor) {
        daoGenerico.salvar(autor);
        return true;
    }
}
