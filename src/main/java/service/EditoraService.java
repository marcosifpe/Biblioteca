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
public class EditoraService extends Service<Editora> {

    @Resource
    private SessionContext sessionContext;

    @TransactionAttribute(REQUIRED)
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
}
