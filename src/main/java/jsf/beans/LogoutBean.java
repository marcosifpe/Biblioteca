package jsf.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "logoutBean")
@ViewScoped
public class LogoutBean implements Serializable {

    public String logout() throws ServletException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();        
        request.logout();
        return "sair";
    }
}
