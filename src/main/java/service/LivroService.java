package service;

import static acesso.Papel.ADMINISTRADOR;
import static acesso.Papel.USUARIO;
import static biblioteca.Livro.LIVRO_POR_ISBN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.ejb.TransactionManagementType.CONTAINER;
import biblioteca.Livro;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
@DeclareRoles({ADMINISTRADOR, USUARIO})
public class LivroService extends Service<Livro> {
    @RolesAllowed({ADMINISTRADOR})
    public void salvar(Livro livro) {
        checkExistence(LIVRO_POR_ISBN, livro.getIsbn());
        entityManager.persist(livro);
    }

    @TransactionAttribute(SUPPORTS)
    @RolesAllowed({USUARIO})
    public List<Livro> getLivros() {
        return getResultList(Livro.LIVROS);
    }

    @TransactionAttribute(SUPPORTS)
    @PermitAll
    public Livro getLivro(String isbn) {
        return getSingleResult(LIVRO_POR_ISBN, new Object[]{isbn});
    }
    
    @TransactionAttribute(SUPPORTS)
    @PermitAll
    public Livro criar() {
        return new Livro();
    }    
}
