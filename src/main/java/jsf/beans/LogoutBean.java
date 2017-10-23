package jsf.beans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MASC
 */
@RequestScoped
@Named("logoutBean")
public class LogoutBean implements Serializable {
    @Inject
    private FacesContext facesContext;

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }
    
    public String logout() throws ServletException {
        HttpSession session = (HttpSession)
                facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        getRequest().logout();
        return "sair";
    }
}
