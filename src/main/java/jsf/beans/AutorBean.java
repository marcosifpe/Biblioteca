package jsf.beans;

import biblioteca.Autor;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.AutorService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "autorBean")
@ViewScoped
public class AutorBean extends Bean implements Serializable {
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
        try {
            autorService.salvar(autor);   
            super.adicionarMessagem("Cadastro do autor realizado com sucesso!");
        } catch (EJBException ex) {
            if (super.entidadeExistente(ex)) {
                return;
            }
            
            throw ex;
        } finally {
            iniciarCampos();            
        }
    }
    
    private void iniciarCampos() {
        this.autor = new Autor();
    }    
}
