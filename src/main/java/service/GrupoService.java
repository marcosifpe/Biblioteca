/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import acesso.Grupo;
import acesso.Papel;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
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
@DeclareRoles({Papel.ADMINISTRADOR, Papel.USUARIO})
public class GrupoService extends Service<Grupo> {
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)       
    @PermitAll    
    public Grupo getGrupo(String nomeGrupo) {
        return getSingleResult(Grupo.GRUPO_POR_NOME, new Object[]{nomeGrupo});
    }
}
