package jsf.beans;

import biblioteca.Editora;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import service.EditoraService;

/**
 *
 * @author MASC
 */
@Named(value = "editoraBean")
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
