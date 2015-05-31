package jsf.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author MASC
 */
public class JsfUtil {

    public static void adicionarMessagem(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
