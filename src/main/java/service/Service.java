/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static javax.persistence.PersistenceContextType.TRANSACTION;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

import biblioteca.Entidade;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author MASC
 * @param <T> A classe do serviço
 */
public abstract class Service<T extends Entidade> {

    @PersistenceContext(name = "biblioteca", type = TRANSACTION)
    protected EntityManager entityManager;
    protected Class<T> clazz;
    
    @TransactionAttribute(SUPPORTS)        
    protected List<T> getResultList(String nomeQuery) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, clazz);
        return query.getResultList();
    }

    @TransactionAttribute(SUPPORTS)        
    protected List<T> getResultList(String nomeQuery, Object[] parametros) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, clazz);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getResultList();
    }

    @TransactionAttribute(SUPPORTS)    
    protected T getSingleResult(String nomeQuery, Object[] parametros) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, clazz);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getSingleResult();
    }

    @TransactionAttribute(SUPPORTS)        
    protected void checkExistence(String nomeQuery, Object parametro)
            throws EntityExistsException {
        T object;

        try {
            object = getSingleResult(nomeQuery, new Object[] {parametro});
            if (object != null) {
                throw new EntityExistsException(parametro.toString());
            }           
        } catch (NonUniqueResultException ex) {
            throw new EntityExistsException(parametro.toString(), ex);
        } catch (NoResultException ex) {
            
        }
    }
}
