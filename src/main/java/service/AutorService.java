package service;

import biblioteca.Autor;
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
@Stateless(name = "autorService")
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AutorService {
    @PersistenceContext(name = "biblioteca", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
    
    public void salvar(Autor autor) {
        entityManager.persist(autor);
    }
}
