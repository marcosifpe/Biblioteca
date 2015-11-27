package jsf.beans;

import biblioteca.Editora;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import service.EditoraService;

/**
 *
 * @author MASC
 */
@ManagedBean(name = "editoraBean")
@ViewScoped
public class EditoraBean extends Bean<Editora> implements Serializable {
    @EJB
    private EditoraService editoraService;

    @Override
    protected void iniciarCampos() {
        setEntidade(new Editora());
    }

    @Override
    protected void salvar(Editora entidade) {
        this.editoraService.salvar(entidade);
    }
}
