/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.ejb.ApplicationException;

/**
 *
 * @author MASC
 */
@ApplicationException(rollback = true)
public class ExcecaoNegocio extends Exception {

    public ExcecaoNegocio(String mensagem) {
        super(mensagem);
    }    
}
