package jsf.beans;

import biblioteca.Autor;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import service.AutorService;

/**
 *
 * @author MASC
 */
@Named(value = "autorBean")
@ManagedBean
@ViewScoped
public class AutorBean {
    private Autor autor;
    @EJB(name = "autorService")
    private AutorService autorService;
    /**
     * Creates a new instance of AutorBean
     */
    public AutorBean() {
        iniciarCampos();
    }
    
    public Autor getAutor() {
        return this.autor;
    }
    
    public void salvar() {
        autorService.salvar(autor);
        iniciarCampos();
        adicionarMessagem("Cadastro realizado com sucesso!");
    }
    
    private void iniciarCampos() {
        this.autor = new Autor();
    }
    
    public void adicionarMessagem(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
