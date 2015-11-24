package service;

import acesso.Papel;
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
@DeclareRoles({Papel.ADMINISTRADOR, Papel.USUARIO})
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AutorService extends Service<Autor> {
    @TransactionAttribute(TransactionAttributeType.REQUIRED)    
    @RolesAllowed({Papel.ADMINISTRADOR})
    public void salvar(Autor autor) {
        checkExistence(Autor.AUTOR_POR_CPF, autor.getCpf());
        entityManager.persist(autor);
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)   
    @PermitAll
    public List<Autor> getAutores() {
        return getResultList(Autor.AUTORES);
    }
}
