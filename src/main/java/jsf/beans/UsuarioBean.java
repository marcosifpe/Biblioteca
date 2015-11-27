/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import acesso.Usuario;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.UsuarioService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends Bean<Usuario> {
    @EJB
    private UsuarioService usuarioService;
    
    @Override
    protected void iniciarCampos() {
        this.entidade = new Usuario();
    }      

    @Override
    protected void salvar(Usuario entidade) {
        usuarioService.salvar(entidade);
    }
}
