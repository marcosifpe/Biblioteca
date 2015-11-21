package service;

import biblioteca.Editora;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author MASC
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@WebService
public class EditoraWebService extends Service<Editora> {  
    /**
     * Operação de Web service
     * @param editora
     * @return 
     */
    @WebMethod(operationName = "criarEditora")
    public boolean criarEditora(@WebParam(name = "Editora") Editora editora) {
        checkExistence("EditoraPorNome", editora.getNome());        
        entityManager.persist(editora);
        return true;
    }

}
