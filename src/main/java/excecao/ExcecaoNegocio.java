/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excecao;

import javax.ejb.ApplicationException;

/**
 *
 * @author MASC
 */
@ApplicationException(rollback = true)
public class ExcecaoNegocio extends Exception {
    private String chave;    
    public static final String OBJETO_INEXISTENTE = "excecao.ExcecaoNegocio.objetoInexistente";
    public static final String REMOVER_EDITORA = "excecao.ExcecaoNegocio.editoraServico.remover";
    public static final String REMOVER_AUTOR = "excecao.ExcecaoNegocio.autorServico.remover";
    public static final String OBJETO_EXISTENTE = "excecao.ExcecaoNegocio.objetoExistente";  
    public static final String ACESSO_NAO_AUTORIZADO = "excecao.ExcecaoNegocio.acesso.nao.autorizado";
    public static final String CREDENCIAIS_OMITIDAS = "excecao.ExcecaoNegocio.acesso.credenciais.omitidadas";
    public static final String LOGIN_INVALIDO = "excecao.ExcecaoNegocio.acesso.login.invalido";    
    
    public ExcecaoNegocio(String chave) {
        this.chave = chave;
    }  

    public String getChave() {
        return chave;
    }
    
    @Override
    public String getMessage() {
        MensagemExcecao mensagemExcecao = new MensagemExcecao(this);
        return mensagemExcecao.getMensagem();
    }
    
    public boolean isAutorizacao() {
        switch(chave) {
            case ACESSO_NAO_AUTORIZADO:
            case CREDENCIAIS_OMITIDAS:
            case LOGIN_INVALIDO:
                return true;
            default:
                return false;
        }
    }
}
