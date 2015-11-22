package service;

import acesso.Papel;
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
@DeclareRoles({Papel.ADMINISTRADOR, Papel.USUARIO})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EditoraService extends Service<Editora> {
    @TransactionAttribute(TransactionAttributeType.REQUIRED)    
    @RolesAllowed({Papel.ADMINISTRADOR})
    public void salvar(Editora editora) {
        checkExistence(Editora.EDITORA_POR_NOME, editora.getNome());
        entityManager.persist(editora);
    }

    @PermitAll
    public List<Editora> getEditoras() {
        return getResultList(Editora.EDITORAS);
    }
}
