/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.beans;

import acesso.Usuario;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.UsuarioService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends Bean {
    private Usuario usuario;
    @EJB
    private UsuarioService usuarioService;
    
    /**
     * Creates a new instance of AutorBean
     */
    public UsuarioBean() {
        iniciarCampos();
    }
    
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void salvar() {
        try {
            usuarioService.salvar(usuario);   
            super.adicionarMessagem("Cadastro do usuário realizado com sucesso!");
        } catch (EJBException ex) {
            if (super.entidadeExistente(ex)) {
                return;
            }
            
            throw ex;
        } finally {
            iniciarCampos();            
        }
    }
    
    private void iniciarCampos() {
        this.usuario = new Usuario();
    }      
}
