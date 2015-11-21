package service;

import biblioteca.Autor;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AutorService extends Service<Autor> {
    @TransactionAttribute(TransactionAttributeType.REQUIRED)    
    @RolesAllowed({"administrador"})
    public void salvar(Autor autor) {
        checkExistence("AutorPorCpf", autor.getCpf());
        entityManager.persist(autor);
    }
    
    @PermitAll
    public List<Autor> getAutores() {
        return getResultList("Autores");
    }
}
