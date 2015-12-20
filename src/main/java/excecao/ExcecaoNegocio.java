/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excecao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import javax.ejb.ApplicationException;

/**
 *
 * @author MASC
 */
@ApplicationException(rollback = true)
public class ExcecaoNegocio extends Exception {
    private static final Properties properties = new Properties();
    public static final String AUTOR_SERVICE_REMOVER = "excecao.ExcecaoNegocio.AutorService.remover";
    public static final String OBJETO_EXISTENTE = "excecao.ExcecaoNegocio.objetoExistente";    
    
    static {
        InputStream is = ExcecaoNegocio.class.getClassLoader().getResourceAsStream("Exception.properties");
        
        if (is != null) {
            try {
                properties.load(is);
                is.close();
            } catch (IOException ex) {
                Logger.getGlobal().severe(ex.getMessage());
            }
        }              
    }

    public ExcecaoNegocio(String chaveMensagem) {
        super(properties.getProperty(chaveMensagem));
    }    
}
