/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import biblioteca.Entidade;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;

/**
 *
 * @author MASC
 * @param <T>
 */
public abstract class Bean<T extends Entidade> {
    protected T entidade;
    
    public Bean() {
        iniciarCampos();
    }
    
    protected abstract void iniciarCampos();
    protected abstract void salvar(T entidade);
    

    public T getEntidade() {
        return entidade;
    }

    public void setEntidade(T entidade) {
        this.entidade = entidade;
    }

    public void salvar() {
        try {
            salvar(entidade);
            adicionarMessagem("Cadastro realizado com sucesso!");
        } catch (EJBException ex) {
            if (entidadeExistente(ex)) {
                return;
            }
            
            throw ex;
        } finally {
            iniciarCampos();            
        }
    }    
    
    protected void adicionarMessagem(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    protected boolean entidadeExistente(EJBException ex) {
        if (ex.getCause() instanceof EntityExistsException) {
            adicionarMessagem("Objeto " + ex.getCause().getMessage() + " já cadastrado!");
            return true;
        }

        return false;
    }
}
