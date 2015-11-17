/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author MASC
 */
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

    private String usuario;
    private String senha;

    public String login() {
        try {
            FacesContext facesContext = FacesContext.
                    getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            request.login(usuario, senha);
            facesContext.getExternalContext().getSession(true);

        } catch (ServletException ex) {
            JsfUtil.adicionarMessagem("Login inválido!");
            return "/publico/login_faces.xhtml";
        }

        return "/usuario/livros.xhtml";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
