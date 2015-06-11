package jsf.beans;

import biblioteca.Autor;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.AutorService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "autorBean")
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
        JsfUtil.adicionarMessagem("Cadastro do autor realizado com sucesso!");
    }
    
    private void iniciarCampos() {
        this.autor = new Autor();
    }    
}
