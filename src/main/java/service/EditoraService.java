package service;

import static acesso.Papel.ADMINISTRADOR;
import static biblioteca.Editora.EDITORA_POR_NOME;
import static biblioteca.Editora.EDITORAS;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

import biblioteca.Editora;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBAccessException;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
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
public class EditoraService extends Service<Editora> {

    @Resource
    private SessionContext sessionContext;

    public void salvar(Editora editora) {
        if (sessionContext.isCallerInRole(ADMINISTRADOR)) {
            checkExistence(EDITORA_POR_NOME, editora.getNome());
            entityManager.persist(editora);
        } else {
            throw new EJBAccessException();
        }
    }

    @TransactionAttribute(SUPPORTS)
    public List<Editora> getEditoras() {
        return getResultList(EDITORAS);
    }
    
    @TransactionAttribute(SUPPORTS)    
    public Editora getEditora(String nome) {
        return super.getSingleResult(EDITORA_POR_NOME, new Object[]{nome});
    }
    
    @TransactionAttribute(SUPPORTS)
    public Editora criar() {
        return new Editora();
    }
}
