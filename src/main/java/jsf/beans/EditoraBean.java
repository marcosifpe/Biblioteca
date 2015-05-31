package jsf.beans;

import biblioteca.Editora;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import service.EditoraService;

/**
 *
 * @author MASC
 */
@Named(value = "editoraBean")
@ManagedBean
@ViewScoped
public class EditoraBean {
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
        editoraService.salvar(editora);
        iniciarCampos();
        JsfUtil.adicionarMessagem("Cadastro da editora realizado com sucesso!");
    }

    public Editora getEditora() {
        return editora;
    }
}
