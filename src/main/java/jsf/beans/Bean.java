/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;

/**
 *
 * @author MASC
 */
public abstract class Bean {

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
