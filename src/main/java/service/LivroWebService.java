package service;

import biblioteca.Autor;
import biblioteca.Livro;
import dao.DaoGenerico;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author MASC
 */
@Stateless
@WebService
public class LivroWebService {

    @EJB
    private DaoGenerico daoGenerico;

    /**
     * Operação de Web service
     *
     * @return Mensagem de saudação
     */
    @WebMethod(operationName = "hello")
    public String hello() {
        return "Alo, mundo";
    }

    @WebMethod(operationName = "getLivro")
    @WebResult(name = "livro")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Livro getLivro(@WebParam(name = "isbn", mode = WebParam.Mode.IN) String isbn) {
        return (Livro) daoGenerico.getEntidade("LivroPorIsbn", new Object[]{isbn});
    }
    
    @WebMethod(operationName = "salvarAutor")
    @WebResult(name = "sucesso")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean criarAutor(@WebParam(name = "autor", mode = WebParam.Mode.IN) Autor autor) {
        daoGenerico.salvar(autor);
        return true;
    }
}
