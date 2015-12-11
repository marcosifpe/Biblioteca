/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import biblioteca.Entidade;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;

/**
 *
 * @author MASC
 * @param <T> Tipo da classe.
 */
public abstract class Bean<T extends Entidade> {

    protected T entidade;
    
    @PostConstruct
    public void init() {
        iniciarCampos();
    }    

    protected abstract void iniciarCampos();

    protected abstract boolean salvar(T entidade);

    public T getEntidade() {
        return entidade;
    }

    public void setEntidade(T entidade) {
        this.entidade = entidade;
    }

    public void salvar() {
        try {
            boolean sucesso = salvar(entidade);
            if (sucesso) {
                adicionarMessagem(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!");
            }
        } catch (EJBException ex) {
            if (entidadeExistente(ex)) {
                return;
            }

            throw ex;
        } finally {
            iniciarCampos();
        }
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem) {
        FacesMessage message = new FacesMessage(severity, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    protected boolean entidadeExistente(EJBException ex) {
        if (ex.getCause() instanceof EntityExistsException) {
            adicionarMessagem(FacesMessage.SEVERITY_WARN, "Objeto " + ex.getCause().getMessage() + " já cadastrado!");
            return true;
        }

        return false;
    }
}
