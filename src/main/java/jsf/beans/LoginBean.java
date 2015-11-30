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
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

    @NotBlank
    private String usuario;
    @NotBlank
    private String senha;
    private FacesContext facesContext;

    private boolean validarCaptcha() {
        Recaptcha recaptcha = new Recaptcha(facesContext);
        return recaptcha.validar();
    }

    public String login() {
        try {
            facesContext = FacesContext.getCurrentInstance();

            if (validarCaptcha()) {
                HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                request.login(usuario, senha);
                facesContext.getExternalContext().getSession(true);
            } else {
                setUsuario(null);
                adicionarMensagem("Captcha inválido!");
                return "failure";
            }

        } catch (ServletException ex) {
            setUsuario(null);
            adicionarMensagem("Senha ou usuário inválidos!");
            return "failure";
        }

        return "success";
    }

    private void adicionarMensagem(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null);
        facesContext.addMessage(null, message);
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
