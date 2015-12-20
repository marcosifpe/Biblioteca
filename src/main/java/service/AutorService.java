package service;

import static acesso.Papel.ADMINISTRADOR;
import static acesso.Papel.USUARIO;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

import excecao.ExcecaoNegocio;
import biblioteca.Autor;
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
@DeclareRoles({ADMINISTRADOR, USUARIO})
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED) 
public class AutorService extends Service<Autor> {   
    @RolesAllowed({ADMINISTRADOR})
    public void salvar(Autor autor) {
        checkExistence(Autor.AUTOR_POR_CPF, autor.getCpf());
        entityManager.persist(autor);
    }
    
    @RolesAllowed({ADMINISTRADOR})
    public void atualizar(Autor autor) {
        entityManager.merge(autor);
        entityManager.flush();
    }    
    
    @RolesAllowed({ADMINISTRADOR})
    public void remover(Autor autor) throws ExcecaoNegocio {
        autor = entityManager.merge(autor);
        if (autor.isInativo())
            entityManager.remove(autor);
        else
            throw new ExcecaoNegocio(ExcecaoNegocio.AUTOR_SERVICE_REMOVER);
    }
    
    @RolesAllowed({ADMINISTRADOR})
    public void remover(String cpf) throws ExcecaoNegocio {        
        Autor autor = getAutor(cpf);
        remover(autor);
    }    
    
    @TransactionAttribute(SUPPORTS)   
    @PermitAll
    public List<Autor> getAutores() {
        return getResultList(Autor.AUTORES);
    }
    
    @TransactionAttribute(SUPPORTS)   
    @PermitAll
    public Autor getAutor(String cpf) {
        return super.getSingleResult(Autor.AUTOR_POR_CPF, new Object[] {cpf});
    }

    @TransactionAttribute(SUPPORTS)   
    @PermitAll
    public Autor criar() {
        return new Autor();
    }
}