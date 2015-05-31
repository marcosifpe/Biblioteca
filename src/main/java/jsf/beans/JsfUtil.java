/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author MASC
 */
public class JsfUtil {

    public static void adicionarMessagem(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
