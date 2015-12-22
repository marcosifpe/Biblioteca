/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import static javax.persistence.PersistenceContextType.TRANSACTION;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static excecao.ExcecaoNegocio.OBJETO_EXISTENTE;

import biblioteca.Entidade;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.TransactionAttribute;
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
public abstract class Servico<T extends Entidade> {

    @PersistenceContext(name = "biblioteca", type = TRANSACTION)
    protected EntityManager entityManager;
    protected Class<T> classe;

    @TransactionAttribute(SUPPORTS)
    protected List<T> getEntidades(String nomeQuery) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);
        return query.getResultList();
    }

    @TransactionAttribute(SUPPORTS)
    protected List<T> getEntidades(String nomeQuery, Object[] parametros) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getResultList();
    }

    @TransactionAttribute(SUPPORTS)
    protected T getEntidade(String nomeQuery, Object[] parametros) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getSingleResult();
    }

    @TransactionAttribute(SUPPORTS)
    protected void checarExistencia(String nomeQuery, Object parametro)
            throws ExcecaoNegocio {
        T entidade;

        try {
            entidade = getEntidade(nomeQuery, new Object[]{parametro});
            if (entidade != null) {
                throw new ExcecaoNegocio(OBJETO_EXISTENTE);
            }
        } catch (NonUniqueResultException ex) {
            throw new ExcecaoNegocio(OBJETO_EXISTENTE);
        } catch (NoResultException ex) {

        }
    }
    
    @TransactionAttribute(SUPPORTS)
    protected void checarNaoExistencia(String nomeQuery, Object parametro)
            throws ExcecaoNegocio {
        try {
            getEntidade(nomeQuery, new Object[]{parametro});
        } catch (NoResultException ex) {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_INEXISTENTE);
        }
    }
    
}
