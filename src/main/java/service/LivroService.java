package service;

import biblioteca.Livro;
import dao.DaoGenerico;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@DeclareRoles({"administrador", "usuario"})
public class LivroService {
    @EJB
    private DaoGenerico daoGenerico;
    
    @RolesAllowed({"administrador"})    
    public void salvar(Livro livro) {
        daoGenerico.salvar(livro);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll    
    public List<Livro> getLivros() {
        return (List<Livro>) daoGenerico.get("Livros");
    }
}
