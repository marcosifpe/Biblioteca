/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static acesso.Usuario.USUARIO_POR_LOGIN;
import static acesso.Grupo.USUARIO;

import acesso.Usuario;
import excecao.ExcecaoNegocio;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public class UsuarioServico extends Servico<Usuario> {
    @EJB
    private GrupoServico grupoService;
    @EJB
    private EmailServico emailService;

    public void salvar(Usuario usuario) throws ExcecaoNegocio {
        checarExistencia(USUARIO_POR_LOGIN, usuario.getLogin());
        usuario.adicionarGrupo(grupoService.getGrupo(USUARIO));
        entityManager.persist(usuario);
        emailService.enviarMensagem(usuario.getEmail());
    }
}
