/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

    private String usuario;
    private String senha;

    private boolean captchaIsValid(HttpServletRequest request) {
         String gRecaptchaResponse = request
                .getParameter("g-recaptcha-response");
        System.out.println(gRecaptchaResponse);
        try {
            return VerifyRecaptcha.verify(gRecaptchaResponse);
        } catch (IOException ex) {
            return false;
        }
    }

    public String login() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            if (captchaIsValid(request)) {
                request.login(usuario, senha);
                facesContext.getExternalContext().getSession(true);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Captcha inválido!", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "failure";
            }

        } catch (ServletException ex) {
            setUsuario("");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Login inválido!", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "failure";
        }

        return "success";
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
