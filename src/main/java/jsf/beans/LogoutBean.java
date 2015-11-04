/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "logoutBean")
@ViewScoped
public class LogoutBean {

    public String logout() throws ServletException {
        ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).logout();
        return "/index";
    }
}
