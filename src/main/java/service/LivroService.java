package service;

import biblioteca.Livro;
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
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@DeclareRoles({"administrador", "usuario"})
public class LivroService extends Service {   
    @RolesAllowed({"administrador"})    
    public void salvar(Livro livro) {
        entityManager.persist(livro);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @PermitAll    
    public List<Livro> getLivros() {
        return (List<Livro>) getResultList("Livros");
    }
}
