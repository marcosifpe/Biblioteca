/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excecao;

import excecao.util.MensagemExcecao;
import javax.ejb.ApplicationException;

/**
 *
 * @author MASC
 */
@ApplicationException(rollback = true)
public class ExcecaoSistema extends RuntimeException {
    public ExcecaoSistema(Throwable causa) {
        super(causa);
    }
    
    @Override
    public String getMessage() {
        MensagemExcecao mensagemExcecao = new MensagemExcecao(this);
        return mensagemExcecao.getMensagem();
    }    
}
