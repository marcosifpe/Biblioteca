package service;

import biblioteca.Editora;
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
@Stateless
@LocalBean
@DeclareRoles({"administrador", "usuario"})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EditoraService extends Service<Editora> {
    @TransactionAttribute(TransactionAttributeType.REQUIRED)    
    @RolesAllowed({"administrador"})
    public void salvar(Editora editora) {
        checkExistence("EditoraPorNome", editora.getNome());
        entityManager.persist(editora);
    }

    @PermitAll
    public List<Editora> getEditoras() {
        return getResultList("Editoras");
    }
}
