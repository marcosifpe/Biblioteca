package jsf.beans;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;

/**
 *
 * @author MASC
 */
public class JsfUtil {

    public static void adicionarMessagem(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static boolean entidadeExistente(EJBException ex) {
        if (ex.getCause() instanceof EntityExistsException) {
            JsfUtil.adicionarMessagem("Objeto " + ex.getCause().getMessage() + " já cadastrado!");
            return true;
        }
        
        return false;
    }    
}
