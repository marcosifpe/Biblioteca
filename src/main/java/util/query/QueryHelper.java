/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.query;

import biblioteca.Entidade;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class QueryHelper {
    @PersistenceContext(name = "biblioteca", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
    
    public static List<Entidade> find(EntityManager entityManager, String queryName, Object[] paramaters) {
        Query query = entityManager.createNamedQuery(queryName);       
        addParameters(paramaters, query);
        return query.getResultList();
    }
    
    public static void addParameters(Object[] paramaters, Query query) {
        int i = 1;
        for (Object paramater : paramaters) {
            query.setParameter(i++, paramater);
        }
    }
    
    @SuppressWarnings("element-type-mismatch")
    public static void addParameters(Map<String, Object> parametersMap, Query query) {
        Set keysSet = parametersMap.keySet();
        
        for (Object key : keysSet) {
            query.setParameter((String) key, parametersMap.get(key));
        }
    }
}
