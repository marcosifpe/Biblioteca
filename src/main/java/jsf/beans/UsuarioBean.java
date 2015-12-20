/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import acesso.Usuario;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import service.UsuarioService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "usuarioBean")
@RequestScoped
public class UsuarioBean extends Bean<Usuario> implements Serializable {

    public UsuarioBean() {
        this.entidade = new Usuario();
    }

    @EJB
    private UsuarioService usuarioService;
    private boolean sucesso = true;

    @Override
    protected void iniciarCampos() {
        if (sucesso) {
            this.entidade = new Usuario();
        }
    }

    @Override
    protected boolean salvar(Usuario entidade) throws ExcecaoNegocio {
        this.sucesso = false;
        Recaptcha recaptcha = new Recaptcha(FacesContext.getCurrentInstance());
        if (recaptcha.validar()) {
            usuarioService.salvar(entidade);
            this.sucesso = true;
        } else {
            super.adicionarMessagem(FacesMessage.SEVERITY_WARN, "Captcha inválido!");
        }

        return this.sucesso;
    }
}
