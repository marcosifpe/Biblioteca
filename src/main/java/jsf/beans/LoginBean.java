/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author MASC
 */
@RequestScoped
@Named("loginBean")
public class LoginBean implements Serializable {

    @NotBlank
    private String usuario;
    @NotBlank
    private String senha;
    @Inject
    private FacesContext facesContext;
    @Inject   
    private Recaptcha recaptcha;

    public String login() {
        try {
            if (recaptcha.validar()) {
                HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                request.login(usuario, senha);
                facesContext.getExternalContext().getSession(true);
            } else {
                setUsuario(null);
                adicionarMensagem("Captcha inválido!");
                return "falha";
            }

        } catch (ServletException ex) {
            setUsuario(null);
            adicionarMensagem("Senha ou usuário inválidos!");
            return "falha";
        }

        return "sucesso";
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
