/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

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

    private boolean captchaIsValid(HttpServletRequest request, FacesContext facesContext) {
        String recaptcha = request
                .getParameter("g-recaptcha-response");
        String chavePrivada = facesContext.getExternalContext().getInitParameter("PRIVATE_CAPTCHA_KEY");
        String url = facesContext.getExternalContext().getInitParameter("CAPTCHA_URL");
        return Recaptcha.verificar(url, recaptcha, chavePrivada);
    }

    public String login() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            
            if (captchaIsValid(request, facesContext)) {
                request.login(usuario, senha);
                facesContext.getExternalContext().getSession(true);
            } else {
                setUsuario("");                
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
