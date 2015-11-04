package service;

import biblioteca.Autor;
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
@Stateless(name = "autorService")
@LocalBean
@DeclareRoles({"administrador", "usuario"})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AutorService {
    @EJB
    private DaoGenerico daoGenerico;

    @RolesAllowed({"administrador"})
    public void salvar(Autor autor) {
        daoGenerico.salvar(autor);
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll
    public List<Autor> getAutores() {
        return (List<Autor>) daoGenerico.get("Autores");
    }
}
