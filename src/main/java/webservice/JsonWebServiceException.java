/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import javax.ejb.ApplicationException;

/**
 *
 * @author MASC
 */
@ApplicationException(rollback = true)
public class JsonWebServiceException extends RuntimeException {
    public JsonWebServiceException(Throwable cause) {
        super(cause);
    }
}
