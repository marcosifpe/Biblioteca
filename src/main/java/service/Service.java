/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author MASC
 */
public abstract class Service {
    @PersistenceContext(name = "biblioteca", type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;
    
    protected List getResultList(String nomeQuery) {
        Query query = entityManager.createNamedQuery(nomeQuery);
        return query.getResultList();
    }
    
    protected List getResultList(String nomeQuery, Object[] parametros) {
        Query query = entityManager.createNamedQuery(nomeQuery);
        
        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }
        
        return query.getResultList();        
    }
    
}
