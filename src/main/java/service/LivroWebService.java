package service;

import biblioteca.Livro;
import dao.DaoGenerico;
import java.util.List;
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
        List<Livro> livros = daoGenerico.get("LivroPorIsbn", new Object[]{isbn});

        if (livros.isEmpty()) {
            return null;
        } else {
            return livros.get(0);
        }
    }
}
