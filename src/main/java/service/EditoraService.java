package service;

import biblioteca.Editora;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EditoraService {

    @PersistenceContext(name = "biblioteca", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public void salvar(Editora editora) {
        entityManager.persist(editora);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Editora> getEditoras() {
        TypedQuery query = entityManager.createNamedQuery("Editoras", Editora.class);
        return query.getResultList();
    }
}
