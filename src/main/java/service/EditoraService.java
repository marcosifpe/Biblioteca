package service;

import biblioteca.Editora;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EditoraService {

    @PersistenceContext(name = "biblioteca", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public void salvar(Editora editora) {
        entityManager.persist(editora);
    }
}
