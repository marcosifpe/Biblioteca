package service;

import biblioteca.Editora;
import dao.DaoGenerico;
import javax.ejb.EJB;
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
public class EditoraWebService {
    @EJB
    private DaoGenerico daoGenerico;
    
    /**
     * Operação de Web service
     * @param editora
     * @return 
     */
    @WebMethod(operationName = "criarEditora")
    public boolean criarEditora(@WebParam(name = "Editora") Editora editora) {
        daoGenerico.salvar(editora);
        return true;
    }

}
