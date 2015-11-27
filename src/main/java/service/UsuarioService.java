/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import acesso.Grupo;
import acesso.Papel;
import acesso.Usuario;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UsuarioService extends Service<Usuario> {
    @EJB
    private GrupoService grupoService;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)    
    public void salvar(Usuario usuario) {
        checkExistence(Usuario.USUARIO_POR_LOGIN, usuario.getLogin());
        usuario.adicionarGrupo(grupoService.getGrupo(Grupo.USUARIO));
        entityManager.persist(usuario);
    }
}
