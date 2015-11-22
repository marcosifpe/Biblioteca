package service;

import acesso.Papel;
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
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@DeclareRoles({Papel.ADMINISTRADOR, Papel.USUARIO})
public class LivroService extends Service<Livro> {
    @TransactionAttribute(TransactionAttributeType.REQUIRED)    
    @RolesAllowed({Papel.ADMINISTRADOR})    
    public void salvar(Livro livro) {
        checkExistence(Livro.LIVRO_POR_ISBN, livro.getIsbn());
        entityManager.persist(livro);
    }

    @PermitAll    
    public List<Livro> getLivros() {
        return getResultList(Livro.LIVROS);
    }
}
