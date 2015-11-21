package jsf.beans;

import biblioteca.Editora;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.EditoraService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "editoraBean")
@ViewScoped
public class EditoraBean implements Serializable {
    private Editora editora;
    @EJB
    private EditoraService editoraService;

    /**
     * Creates a new instance of EditoraBean
     */
    public EditoraBean() {
        iniciarCampos();
    }

    private void iniciarCampos() {
        this.editora = new Editora();
    }

    public void salvar() {
        try {
            editoraService.salvar(editora);
            JsfUtil.adicionarMessagem("Cadastro da editora realizado com sucesso!");
        } catch (EJBException ex) {
            if (JsfUtil.entidadeExistente(ex)) {
                return;
            }
            
            throw ex;
        } finally {
            iniciarCampos();            
        }        
    }

    public Editora getEditora() {
        return editora;
    }
}
