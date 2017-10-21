/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import acesso.Usuario;
import excecao.ExcecaoNegocio;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import servico.UsuarioServico;

/**
 *
 * @author MASC
 */
@RequestScoped
@Named("usuarioBean")
public class UsuarioBean extends Bean<Usuario> implements Serializable {
    @Inject   
    private Recaptcha recaptcha;
    
    public UsuarioBean() {
        this.entidade = new Usuario();
    }

    @Inject
    private UsuarioServico usuarioServico;
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

        if (recaptcha.validar()) {
            usuarioServico.salvar(entidade);
            this.sucesso = true;
        } else {
            super.adicionarMessagem(FacesMessage.SEVERITY_WARN, "Captcha inválido!");
        }

        return this.sucesso;
    }
}
