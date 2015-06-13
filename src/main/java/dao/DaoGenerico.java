package dao;

import biblioteca.Entidade;
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
import javax.persistence.Query;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DaoGenerico {
    @PersistenceContext(name = "biblioteca", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
    
    public void salvar(Entidade entidade) {
        entityManager.persist(entidade);
    }
    
    public List get(String nomeQuery) {
        Query query = entityManager.createNamedQuery(nomeQuery);
        return query.getResultList();
    }
    
    public List get(String nomeQuery, Object[] parametros) {
        Query query = entityManager.createNamedQuery(nomeQuery);
        
        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }
        
        return query.getResultList();        
    }
}
