/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static acesso.Papel.ADMINISTRADOR;
import static acesso.Papel.USUARIO;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.ejb.TransactionManagementType.CONTAINER;

import acesso.Grupo;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
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
@DeclareRoles({ADMINISTRADOR, USUARIO})
public class GrupoServico extends Servico<Grupo> {
    @TransactionAttribute(SUPPORTS)       
    @PermitAll    
    public Grupo getGrupo(String nomeGrupo) {
        return getEntidade(Grupo.GRUPO_POR_NOME, new Object[]{nomeGrupo});
    }
}
